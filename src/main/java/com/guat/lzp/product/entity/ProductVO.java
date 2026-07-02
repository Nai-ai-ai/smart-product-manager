package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品视图对象，包含分类名称，用于列表展示
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String photoUrl;
    private Double price;
    private String descp;
    private Date releaseDate;
    private Long catId;
    /** 分类名称，来自关联查询 */
    private String categoryName;
}
