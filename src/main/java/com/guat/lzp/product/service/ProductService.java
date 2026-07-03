package com.guat.lzp.product.service;

import com.guat.lzp.product.entity.PageResult;
import com.guat.lzp.product.entity.Product;
import com.guat.lzp.product.entity.ProductVO;

/**
 * 商品业务接口
 */
public interface ProductService {

    /**
     * 分页查询商品
     * @param pageNum    页码（从1开始）
     * @param pageSize   每页条数
     * @param name       商品名称（模糊查询，可为null）
     * @param categoryId 分类ID（精确匹配，可为null）
     * @param status     状态（预留字段，可为null）
     * @param minPrice   最低价格（可为null）
     * @param maxPrice   最高价格（可为null）
     * @return 分页结果
     */
    PageResult<ProductVO> findByPage(Integer pageNum, Integer pageSize, String name, Long categoryId, 
                                     Integer status, Double minPrice, Double maxPrice);

    ProductVO findById(Long id);

    boolean save(Product product);

    ProductVO update(Product product);

    boolean deleteById(Long id);

    /** 统计全部商品数量 */
    int countAll();
}
