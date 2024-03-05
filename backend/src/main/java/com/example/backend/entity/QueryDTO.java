package com.example.backend.entity;

public class QueryDTO {
    //前端传后端（？
    private Integer pageNo;
    private Integer pageSize;
    private String keyword;

    public Integer getPageNo() {
        return pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
