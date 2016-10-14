package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/10/14.
 * 我当月的统计数据
 */

public class MyCount {

    private long currentTime;
    private int addedOrder;
    private int addedCustom;
    private int addedVisit;

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public int getAddedOrder() {
        return addedOrder;
    }

    public void setAddedOrder(int addedOrder) {
        this.addedOrder = addedOrder;
    }

    public int getAddedCustom() {
        return addedCustom;
    }

    public void setAddedCustom(int addedCustom) {
        this.addedCustom = addedCustom;
    }

    public int getAddedVisit() {
        return addedVisit;
    }

    public void setAddedVisit(int addedVisit) {
        this.addedVisit = addedVisit;
    }
}
