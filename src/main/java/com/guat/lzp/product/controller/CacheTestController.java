package com.guat.lzp.product.controller;

import com.guat.lzp.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 缓存测试控制器
 * 用于验证Redis缓存是否正常工作
 */
@RestController
@RequestMapping("/test/cache")
public class CacheTestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试缓存功能
     * 首次访问执行SQL，第二次访问命中缓存不执行SQL
     */
    @GetMapping("")
    public String testCache() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Redis缓存功能测试 =====\n\n");

        // 清除所有缓存
        sb.append("【步骤1】清除所有缓存...\n");
        Set<String> keys = redisTemplate.keys("product:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
        sb.append("✅ 缓存已清除\n\n");

        // 第一次查询
        sb.append("【步骤2】第一次查询商品列表（应执行SQL）...\n");
        long start1 = System.currentTimeMillis();
        productService.findByPage(1, 10, null, null, null, null, null);
        long time1 = System.currentTimeMillis() - start1;
        sb.append("⏱️ 耗时: ").append(time1).append("ms\n");
        sb.append("📝 控制台应显示: [缓存未命中] 执行SQL查询商品列表\n\n");

        // 第二次查询
        sb.append("【步骤3】第二次查询商品列表（应命中缓存）...\n");
        long start2 = System.currentTimeMillis();
        productService.findByPage(1, 10, null, null, null, null, null);
        long time2 = System.currentTimeMillis() - start2;
        sb.append("⏱️ 耗时: ").append(time2).append("ms\n");
        sb.append("📝 控制台应无SQL日志\n\n");

        // 验证缓存
        sb.append("【步骤4】验证缓存数据...\n");
        Set<String> cacheKeys = redisTemplate.keys("product:list:*");
        sb.append("📦 Redis中商品缓存数量: ").append(cacheKeys != null ? cacheKeys.size() : 0).append("\n\n");

        // 结论
        sb.append("===== 测试结论 =====\n");
        sb.append("第1次耗时: ").append(time1).append("ms（执行SQL）\n");
        sb.append("第2次耗时: ").append(time2).append("ms（命中缓存）\n");
        
        if (time2 < time1) {
            sb.append("✅ 缓存生效！第2次查询明显更快\n");
            if (time1 > 0) {
                sb.append("✅ 性能提升: ").append(String.format("%.1f", (double)time1/time2)).append("倍\n");
            }
        } else {
            sb.append("⚠️ 缓存可能未生效，请检查配置\n");
        }

        sb.append("\n========================\n");
        sb.append("请查看控制台日志对比SQL执行情况");

        return sb.toString();
    }

    /**
     * 查看缓存状态
     */
    @GetMapping("/status")
    public String cacheStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== 缓存状态 =====\n\n");

        Set<String> keys = redisTemplate.keys("product:list:*");
        if (keys == null || keys.isEmpty()) {
            sb.append("📦 当前无商品缓存\n");
        } else {
            sb.append("📦 商品缓存列表（共").append(keys.size()).append("个）：\n");
            for (String key : keys) {
                sb.append("  - ").append(key).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * 清除所有缓存
     */
    @GetMapping("/clear")
    public String clearCache() {
        Set<String> keys = redisTemplate.keys("product:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
            return "✅ 已清除 " + keys.size() + " 个缓存";
        }
        return "✅ 当前无缓存需要清除";
    }
}
