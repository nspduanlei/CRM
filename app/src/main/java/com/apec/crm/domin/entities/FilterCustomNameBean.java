package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/26.
 */

public class FilterCustomNameBean implements Parcelable {
    
    private String areaName; //行政区域
    private String openSeaName; //片区编号
    private String className; //品类id
    private String customerTypeName; //客户类型
    private String customerLevelName; //客户分类
    private String customerStateName; //客户状态
    private String userName; //用户编号

    public FilterCustomNameBean() {}

    protected FilterCustomNameBean(Parcel in) {
        areaName = in.readString();
        openSeaName = in.readString();
        className = in.readString();
        customerTypeName = in.readString();
        customerLevelName = in.readString();
        customerStateName = in.readString();
        userName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaName);
        dest.writeString(openSeaName);
        dest.writeString(className);
        dest.writeString(customerTypeName);
        dest.writeString(customerLevelName);
        dest.writeString(customerStateName);
        dest.writeString(userName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterCustomNameBean> CREATOR = new Creator<FilterCustomNameBean>() {
        @Override
        public FilterCustomNameBean createFromParcel(Parcel in) {
            return new FilterCustomNameBean(in);
        }

        @Override
        public FilterCustomNameBean[] newArray(int size) {
            return new FilterCustomNameBean[size];
        }
    };

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOpenSeaName() {
        return openSeaName;
    }

    public void setOpenSeaName(String openSeaName) {
        this.openSeaName = openSeaName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getCustomerLevelName() {
        return customerLevelName;
    }

    public void setCustomerLevelName(String customerLevelName) {
        this.customerLevelName = customerLevelName;
    }

    public String getCustomerStateName() {
        return customerStateName;
    }

    public void setCustomerStateName(String customerStateName) {
        this.customerStateName = customerStateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
