package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/17.
 */

public class Address implements Parcelable {

    private String areaId;
    private String areaName;
    private String cityId;
    private String cityName;
    private String latitude; //经度
    private String longitude; //纬度
    private String proviceId;
    private String provinceName;
    private String rodeName;

    private String locationDes; //定位描述

    public Address() {}

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProviceId() {
        return proviceId;
    }

    public void setProviceId(String proviceId) {
        this.proviceId = proviceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRodeName() {
        return rodeName;
    }

    public void setRodeName(String rodeName) {
        this.rodeName = rodeName;
    }

    public String getLocationDes() {
        return locationDes;
    }

    public void setLocationDes(String locationDes) {
        this.locationDes = locationDes;
    }

    protected Address(Parcel in) {
        areaId = in.readString();
        areaName = in.readString();
        cityId = in.readString();
        cityName = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        proviceId = in.readString();
        provinceName = in.readString();
        rodeName = in.readString();
        locationDes = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaId);
        dest.writeString(areaName);
        dest.writeString(cityId);
        dest.writeString(cityName);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(proviceId);
        dest.writeString(provinceName);
        dest.writeString(rodeName);
        dest.writeString(locationDes);
    }
}
