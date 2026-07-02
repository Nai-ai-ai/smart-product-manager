package com.guat.lzp.product.service;

import com.guat.lzp.product.entity.PageResult;
import com.guat.lzp.product.entity.Product;
import com.guat.lzp.product.entity.ProductVO;

/**
 * 商品服务接口
 */
public interface ProductService {

    /** 多条件分页查询商品 */
    PageResult<ProductVO> findByPage(Integer pageNum, Integer pageSize, String name, Long categoryId, Integer status);

    /** 根据ID查询商品 */
    ProductVO findById(Long id);

    /** 新增商品 */
    boolean save(Product product);

    /** 更新商品 */
    Product update(Product product);

    /** 根据ID删除商品 */
    boolean deleteById(Long id);

    /** 统计商品数量 */
    int countAll();
}
