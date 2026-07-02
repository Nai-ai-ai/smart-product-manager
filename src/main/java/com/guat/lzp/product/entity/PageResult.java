package com.guat.lzp.product.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分页查询结果封装
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;
    /** 当前页数据 */
    private List<T> list;

    public PageResult() {}

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
