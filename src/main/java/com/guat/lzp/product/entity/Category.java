package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 商品分类实体类，匹配数据库 category 表结构
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String descp;
}
