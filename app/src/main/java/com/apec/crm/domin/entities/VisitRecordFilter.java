package com.apec.crm.domin.entities;

import com.apec.crm.domin.entities.func.BaseFilter;

/**
 * Created by duanlei on 16/9/29.
 * 访问记录过滤
 */
public class VisitRecordFilter extends BaseFilter {

    //2016-09-19
    private String startDate;
    private String endDate;

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
}