package com.guat.lzp.product.service;

import com.guat.lzp.product.entity.Category;
import java.util.List;

/**
 * 商品分类服务接口
 */
public interface CategoryService {

    /** 查询所有分类 */
    List<Category> findAll();

    /** 根据ID查询分类 */
    Category findById(Long id);

    /** 新增分类 */
    boolean save(Category category);

    /** 更新分类 */
    boolean update(Category category);

    /** 根据ID删除分类 */
    boolean deleteById(Long id);

    /** 统计分类数量 */
    int countAll();
}
