package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户实体类，匹配数据库 t_user 表结构
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    /** 账号状态：1可用，0禁用 */
    private Integer active;
}
