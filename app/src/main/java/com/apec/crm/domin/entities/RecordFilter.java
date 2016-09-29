package com.apec.crm.domin.entities;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/29.
 */

public class RecordFilter {
    //筛选条件
    private String keywords;
    //2016-09-19
    private String startDate;
    private String endDate;

    //asc 或者 desc
    private String sortType;

    //["createDate"]
    private ArrayList<String> orderType;


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
