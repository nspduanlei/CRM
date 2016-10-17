package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/10/17.
 */

public class Address {

    private String provinceName;
    private String cityName;
    private String areaName;
    private int provinceId;
    private int selCityId;
    private int selAreaId;

    //定位地址
    private String longitude; //经度
    private String latitude; //纬度
    //街道
    private String rode;


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getRode() {
        return rode;
    }

    public void setRode(String rode) {
        this.rode = rode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getSelCityId() {
        return selCityId;
    }

    public void setSelCityId(int selCityId) {
        this.selCityId = selCityId;
    }

    public int getSelAreaId() {
        return selAreaId;
    }

    public void setSelAreaId(int selAreaId) {
        this.selAreaId = selAreaId;
    }
}
