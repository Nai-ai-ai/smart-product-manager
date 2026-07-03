package com.guat.lzp.product.mapper;

import com.guat.lzp.product.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品分类数据访问接口
 */
@Mapper
public interface CategoryMapper {

    /** 查询所有分类 */
    List<Category> findAll();

    /** 根据ID查询分类 */
    Category findById(@Param("id") Long id);

    /** 新增分类 */
    int insert(Category category);

    /** 更新分类 */
    int update(Category category);

    /** 根据ID删除分类 */
    int deleteById(@Param("id") Long id);

    /** 统计分类总数 */
    int countAll();
    
    /** 统计分类下的商品数量 */
    int countProductsByCategoryId(@Param("id") Long id);
}
