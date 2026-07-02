package com.guat.lzp.product.mapper;

import com.guat.lzp.product.entity.Product;
import com.guat.lzp.product.entity.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 商品数据访问接口
 */
@Mapper
public interface ProductMapper {

    /** 多条件分页查询商品（含分类名称） */
    List<ProductVO> findByPage(@Param("name") String name,
                               @Param("catId") Long catId,
                               @Param("status") Integer status,
                               @Param("offset") int offset,
                               @Param("limit") int limit);

    /** 统计符合条件的商品总数 */
    int countByCondition(@Param("name") String name,
                         @Param("catId") Long catId,
                         @Param("status") Integer status);

    /** 根据ID查询商品 */
    ProductVO findById(@Param("id") Long id);

    /** 新增商品 */
    int insert(Product product);

    /** 更新商品 */
    int update(Product product);

    /** 根据ID删除商品 */
    int deleteById(@Param("id") Long id);

    /** 统计全部商品数量 */
    int countAll();
}
