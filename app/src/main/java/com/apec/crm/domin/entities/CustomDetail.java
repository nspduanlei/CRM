package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by duanlei on 2016/10/17.
 */

public class CustomDetail implements Parcelable{

    private Address address;
    private String areaNo;
    private String businessHours;
    private String classId;
    private String customerLevel;
    private String customerName;
    private String customerState;
    private String customerType;
    private String id;
    private String isSpeedInto;
    private String source;

    private String className;
    private String areaName;
    private String sourceName;
    private String createDate;
    private String userName;

    private String customerLevelName; //客户分类名
    private String customerStateName; //客户状态名
    private String customerTypeName; //客户类型名

    private int type = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private List<Contact> contacts;

    public CustomDetail() {

    }

    protected CustomDetail(Parcel in) {
        address = in.readParcelable(Address.class.getClassLoader());
        areaNo = in.readString();
        businessHours = in.readString();
        classId = in.readString();
        customerLevel = in.readString();
        customerName = in.readString();
        customerState = in.readString();
        customerType = in.readString();
        id = in.readString();
        isSpeedInto = in.readString();
        source = in.readString();
        className = in.readString();
        areaName = in.readString();
        sourceName = in.readString();
        createDate = in.readString();
        userName = in.readString();
        customerLevelName = in.readString();
        customerStateName = in.readString();
        customerTypeName = in.readString();
        contacts = in.createTypedArrayList(Contact.CREATOR);
    }

    public static final Creator<CustomDetail> CREATOR = new Creator<CustomDetail>() {
        @Override
        public CustomDetail createFromParcel(Parcel in) {
            return new CustomDetail(in);
        }

        @Override
        public CustomDetail[] newArray(int size) {
            return new CustomDetail[size];
        }
    };

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

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsSpeedInto() {
        return isSpeedInto;
    }

    public void setIsSpeedInto(String isSpeedInto) {
        this.isSpeedInto = isSpeedInto;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(address, flags);
        dest.writeString(areaNo);
        dest.writeString(businessHours);
        dest.writeString(classId);
        dest.writeString(customerLevel);
        dest.writeString(customerName);
        dest.writeString(customerState);
        dest.writeString(customerType);
        dest.writeString(id);
        dest.writeString(isSpeedInto);
        dest.writeString(source);
        dest.writeString(className);
        dest.writeString(areaName);
        dest.writeString(sourceName);
        dest.writeString(createDate);
        dest.writeString(userName);
        dest.writeString(customerLevelName);
        dest.writeString(customerStateName);
        dest.writeString(customerTypeName);
        dest.writeTypedList(contacts);
    }
}
