package org.shixuan.inventory.dto;

import java.util.List;

/**
 * 分页查询结果封装类
 */
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
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public List<T> getList() {
        return list;
    }
    
    public void setList(List<T> list) {
        this.list = list;
    }
    
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getPages() {
        return pages;
    }
    
    public void setPages(int pages) {
        this.pages = pages;
    }
} 