package com.guat.lzp.product.service.impl;

import com.guat.lzp.product.entity.Category;
import com.guat.lzp.product.mapper.CategoryMapper;
import com.guat.lzp.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(value = "category", key = "'all'")
    public List<Category> findAll() {
        System.out.println("[缓存未命中] 从数据库查询分类列表");
        return categoryMapper.findAll();
    }

    @Override
    @Cacheable(value = "category", key = "#id")
    public Category findById(Long id) {
        System.out.println("[缓存未命中] 从数据库查询分类: " + id);
        return categoryMapper.findById(id);
    }

    @Override
    @CacheEvict(value = "category", allEntries = true)
    public boolean save(Category category) {
        return categoryMapper.insert(category) > 0;
    }

    @Override
    @CacheEvict(value = "category", allEntries = true)
    public boolean update(Category category) {
        return categoryMapper.update(category) > 0;
    }

    @Override
    @CacheEvict(value = "category", key = "#id")
    public boolean deleteById(Long id) {
        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    public int countAll() {
        return categoryMapper.countAll();
    }
}