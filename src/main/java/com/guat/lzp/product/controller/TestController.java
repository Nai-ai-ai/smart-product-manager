package com.guat.lzp.product.controller;

import com.guat.lzp.product.entity.PageResult;
import com.guat.lzp.product.entity.ProductVO;
import com.guat.lzp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis-cache")
    public String testCache() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Redis缓存测试 ===\n\n");
        
        // 1. 检查当前Redis中的缓存key
        Set<String> keys = redisTemplate.keys("product:list:*");
        sb.append("【当前缓存的Key】\n");
        if (keys == null || keys.isEmpty()) {
            sb.append("  无缓存数据\n");
        } else {
            for (String key : keys) {
                sb.append("  ").append(key).append("\n");
            }
        }
        
        // 2. 第一次查询（应该未命中）
        sb.append("\n【第一次查询】执行SQL查询商品列表\n");
        long start1 = System.currentTimeMillis();
        PageResult<ProductVO> result1 = productService.findByPage(1, 10, null, null, null, null, null);
        long time1 = System.currentTimeMillis() - start1;
        sb.append("  耗时: ").append(time1).append("ms\n");
        sb.append("  查询结果: ").append(result1.getList().size()).append("条记录\n");
        
        // 3. 第二次查询（应该命中缓存）
        sb.append("\n【第二次查询】从Redis缓存获取\n");
        long start2 = System.currentTimeMillis();
        PageResult<ProductVO> result2 = productService.findByPage(1, 10, null, null, null, null, null);
        long time2 = System.currentTimeMillis() - start2;
        sb.append("  耗时: ").append(time2).append("ms\n");
        sb.append("  查询结果: ").append(result2.getList().size()).append("条记录\n");
        
        // 4. 再次检查缓存key
        keys = redisTemplate.keys("product:list:*");
        sb.append("\n【缓存后的Key】\n");
        if (keys == null || keys.isEmpty()) {
            sb.append("  无缓存数据\n");
        } else {
            for (String key : keys) {
                sb.append("  ").append(key).append("\n");
            }
        }
        
        // 5. 性能对比
        sb.append("\n【性能对比】\n");
        sb.append("  第一次查询: ").append(time1).append("ms (数据库查询)\n");
        sb.append("  第二次查询: ").append(time2).append("ms (缓存命中)\n");
        if (time1 > 0) {
            double speedup = (double) time1 / time2;
            sb.append("  性能提升: ").append(String.format("%.1f", speedup)).append("倍\n");
        }
        
        return sb.toString();
    }
}
