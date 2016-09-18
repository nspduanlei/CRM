package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 16/9/13.
 */
public class MenuEntity {
    private int resId;
    private String name;

    public MenuEntity(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
