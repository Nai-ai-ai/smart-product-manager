package com.guat.lzp.product.service.impl;

import com.guat.lzp.product.entity.PageResult;
import com.guat.lzp.product.entity.Product;
import com.guat.lzp.product.entity.ProductVO;
import com.guat.lzp.product.mapper.ProductMapper;
import com.guat.lzp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 商品列表查询 - 使用RedisTemplate手动控制缓存
     */
    @Override
    public PageResult<ProductVO> findByPage(Integer pageNum, Integer pageSize, String name, Long categoryId, 
                                            Integer status, Double minPrice, Double maxPrice) {
        // 构建缓存key（包含所有筛选条件）
        String cacheKey = "product:list:" + pageNum + ":" + pageSize 
            + ":name=" + (name != null ? name : "") 
            + ":cat=" + (categoryId != null ? categoryId : "")
            + ":minPrice=" + (minPrice != null ? minPrice : "")
            + ":maxPrice=" + (maxPrice != null ? maxPrice : "");
        
        // 1. 优先查Redis缓存
        long startTime = System.currentTimeMillis();
        @SuppressWarnings("unchecked")
        PageResult<ProductVO> cachedResult = (PageResult<ProductVO>) redisTemplate.opsForValue().get(cacheKey);
        
        if (cachedResult != null) {
            long endTime = System.currentTimeMillis();
            System.out.println(">>> [缓存命中] 从Redis获取商品列表，key=" + cacheKey + "，耗时=" + (endTime - startTime) + "ms");
            return cachedResult;
        }
        
        // 2. 缓存未命中，查询数据库
        System.out.println(">>> [缓存未命中] 执行SQL查询商品列表，key=" + cacheKey);
        int offset = (pageNum - 1) * pageSize;
        int total = productMapper.countByCondition(name, categoryId, status, minPrice, maxPrice);
        List<ProductVO> list = productMapper.findByPage(name, categoryId, status, minPrice, maxPrice, offset, pageSize);
        PageResult<ProductVO> result = new PageResult<>(total, list);
        
        // 3. 写入Redis缓存，设置过期时间30分钟
        redisTemplate.opsForValue().set(cacheKey, result, 30, TimeUnit.MINUTES);
        long endTime = System.currentTimeMillis();
        System.out.println(">>> [缓存写入] 将商品列表写入Redis，key=" + cacheKey + "，过期时间30分钟，耗时=" + (endTime - startTime) + "ms");
        
        return result;
    }

    /**
     * 商品详情查询 - 使用声明式缓存 @Cacheable
     */
    @Override
    @Cacheable(value = "product", key = "'detail:' + #id")
    public ProductVO findById(Long id) {
        System.out.println(">>> [缓存未命中] 执行SQL查询商品详情 id=" + id);
        return productMapper.findById(id);
    }

    /**
     * 新增商品 - 清理列表缓存
     */
    @Override
    @CacheEvict(value = "product", allEntries = true)
    public boolean save(Product product) {
        boolean result = productMapper.insert(product) > 0;
        if (result) {
            clearListCache();
            System.out.println(">>> [缓存已清除] 新增商品成功，已清理列表缓存");
        }
        return result;
    }

    /**
     * 更新商品 - 使用@CachePut同步更新缓存
     */
    @Override
    @CachePut(value = "product", key = "'detail:' + #product.id")
    public ProductVO update(Product product) {
        productMapper.update(product);
        clearListCache();
        System.out.println(">>> [缓存已更新] 更新商品成功，已同步更新详情缓存和清理列表缓存");
        return productMapper.findById(product.getId());
    }

    /**
     * 删除商品 - 清理列表缓存和详情缓存
     */
    @Override
    @CacheEvict(value = "product", key = "'detail:' + #id")
    public boolean deleteById(Long id) {
        boolean result = productMapper.deleteById(id) > 0;
        if (result) {
            clearListCache();
            System.out.println(">>> [缓存已清除] 删除商品成功，已清理列表缓存和详情缓存");
        }
        return result;
    }

    @Override
    public int countAll() {
        return productMapper.countAll();
    }
    
    /**
     * 清理Redis中的列表缓存
     */
    private void clearListCache() {
        try {
            java.util.Set<String> keys = redisTemplate.keys("product:list:*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                System.out.println(">>> [Redis] 已删除 " + keys.size() + " 个列表缓存key");
            }
        } catch (Exception e) {
            System.out.println(">>> [Redis] 清理缓存失败: " + e.getMessage());
        }
    }
}
