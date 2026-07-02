package com.guat.lzp.product.config;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                System.out.println("[缓存] 获取失败，跳过: " + cache.getName());
            }
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                System.out.println("[缓存] 写入失败，跳过: " + cache.getName());
            }
            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                System.out.println("[缓存] 清除失败，跳过: " + cache.getName());
            }
            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                System.out.println("[缓存] 清空失败，跳过: " + cache.getName());
            }
        };
    }
}
