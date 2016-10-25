package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/25.
 */

public class MoreDataBean implements Parcelable{

    private String areaNo;
    private String classId;
    private String customerLevel;
    private String customerState;
    private String source;

    private String areaName;
    private String className;
    private String levelName;
    private String stateName;
    private String sourceName;

    private String isSpeedInto;
    private String businessHours;


    public MoreDataBean() {}

    protected MoreDataBean(Parcel in) {
        areaNo = in.readString();
        classId = in.readString();
        customerLevel = in.readString();
        customerState = in.readString();
        source = in.readString();
        areaName = in.readString();
        className = in.readString();
        levelName = in.readString();
        stateName = in.readString();
        sourceName = in.readString();
        isSpeedInto = in.readString();
        businessHours = in.readString();
    }

    public static final Creator<MoreDataBean> CREATOR = new Creator<MoreDataBean>() {
        @Override
        public MoreDataBean createFromParcel(Parcel in) {
            return new MoreDataBean(in);
        }

        @Override
        public MoreDataBean[] newArray(int size) {
            return new MoreDataBean[size];
        }
    };

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getIsSpeedInto() {
        return isSpeedInto;
    }

    public void setIsSpeedInto(String isSpeedInto) {
        this.isSpeedInto = isSpeedInto;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(areaNo);
        dest.writeString(classId);
        dest.writeString(customerLevel);
        dest.writeString(customerState);
        dest.writeString(source);
        dest.writeString(areaName);
        dest.writeString(className);
        dest.writeString(levelName);
        dest.writeString(stateName);
        dest.writeString(sourceName);
        dest.writeString(isSpeedInto);
        dest.writeString(businessHours);
    }
}
