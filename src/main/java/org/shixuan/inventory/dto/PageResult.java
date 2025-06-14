package org.shixuan.inventory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分页查询结果封装类
 */
@Setter
@Getter
public class PageResult<T> {
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 当前页数据
     */
    private List<T> list;
    
    /**
     * 当前页码
     */
    private int pageNum;
    
    /**
     * 每页记录数
     */
    private int pageSize;
    
    /**
     * 总页数
     */
    private int pages;
    
    public PageResult() {
    }
    
    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
    
    public PageResult(long total, List<T> list, int pageNum, int pageSize) {
        this.total = total;
        this.list = list;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
    }

}