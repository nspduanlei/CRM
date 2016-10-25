package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.apec.crm.domin.entities.func.BaseFilter;

/**
 * Created by duanlei on 2016/10/25.
 * 筛选客户列表
 */

public class FilterCustomBean extends BaseFilter implements Parcelable{

    private String areaId; //行政区域
    private String areaNo; //片区编号
    private String classId; //品类id
    private String customerType; //客户类型
    private String customerLevel; //客户分类
    private String customerState; //客户状态
    private String userNo; //用户编号

    public FilterCustomBean() {}

    protected FilterCustomBean(Parcel in) {
        areaId = in.readString();
        areaNo = in.readString();
        classId = in.readString();
        customerType = in.readString();
        customerLevel = in.readString();
        customerState = in.readString();
        userNo = in.readString();
    }

    public static final Creator<FilterCustomBean> CREATOR = new Creator<FilterCustomBean>() {
        @Override
        public FilterCustomBean createFromParcel(Parcel in) {
            return new FilterCustomBean(in);
        }

        @Override
        public FilterCustomBean[] newArray(int size) {
            return new FilterCustomBean[size];
        }
    };

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaId);
        dest.writeString(areaNo);
        dest.writeString(classId);
        dest.writeString(customerType);
        dest.writeString(customerLevel);
        dest.writeString(customerState);
        dest.writeString(userNo);
    }
}
