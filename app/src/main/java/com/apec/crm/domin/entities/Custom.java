package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 16/9/13.
 * 客户
 */
public class Custom {
    private int id;
    private String name;
    private String time;
    private String address;

    public Custom(int id, String name, String time, String address) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.address = address;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
