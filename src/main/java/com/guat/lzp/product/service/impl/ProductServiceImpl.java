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

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageResult<ProductVO> findByPage(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status) {
        int offset = (pageNum - 1) * pageSize;
        int total = productMapper.countByCondition(name, categoryId, status);
        List<ProductVO> list = productMapper.findByPage(name, categoryId, status, offset, pageSize);
        return new PageResult<>(total, list);
    }

    @Override
    public ProductVO findById(Long id) {
        return productMapper.findById(id);
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public boolean save(Product product) {
        boolean result = productMapper.insert(product) > 0;
        if (result) {
            try {
                redisTemplate.delete("product::list");
                System.out.println("[缓存已清除] 商品列表缓存已清除");
            } catch (Exception e) {
                System.out.println("[缓存清除失败] Redis不可用: " + e.getMessage());
            }
        }
        return result;
    }

    @Override
    @CachePut(value = "product", key = "#product.id")
    public Product update(Product product) {
        productMapper.update(product);
        try {
            redisTemplate.delete("product::list");
            System.out.println("[缓存已清除] 商品列表缓存已清除");
        } catch (Exception e) {
            System.out.println("[缓存清除失败] Redis不可用: " + e.getMessage());
        }
        return product;
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public boolean deleteById(Long id) {
        boolean result = productMapper.deleteById(id) > 0;
        if (result) {
            try {
                redisTemplate.delete("product::list");
                System.out.println("[缓存已清除] 商品列表缓存已清除");
            } catch (Exception e) {
                System.out.println("[缓存清除失败] Redis不可用: " + e.getMessage());
            }
        }
        return result;
    }

    @Override
    public int countAll() {
        return productMapper.countAll();
    }
}