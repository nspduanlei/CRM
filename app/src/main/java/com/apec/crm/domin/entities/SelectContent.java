package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/10/17.
 */

public class SelectContent {
    private int id;
    private String name;

    public SelectContent(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
