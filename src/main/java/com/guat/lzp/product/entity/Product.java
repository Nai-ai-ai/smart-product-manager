package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品实体类，匹配数据库 product 表结构
 */
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String photoUrl;
    private Double price;
    private String descp;
    private Date releaseDate;
    private Long catId;
}
