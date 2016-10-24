package com.apec.crm.domin.entities.func;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/10/18.
 */

public class BaseFilter {

    //筛选条件
    private String keywords;

    //asc 或者 desc
    private String sortType;

    //["createDate"]
    private ArrayList<String> orderType;

    //页码
    private String pageNumber;
    //每页显示条数
    private String pageSize;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public ArrayList<String> getOrderType() {
        return orderType;
    }

    public void setOrderType(ArrayList<String> orderType) {
        this.orderType = orderType;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
