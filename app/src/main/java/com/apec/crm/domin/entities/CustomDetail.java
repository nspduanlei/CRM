package com.apec.crm.domin.entities;

import java.util.List;

/**
 * Created by duanlei on 2016/10/17.
 */

public class CustomDetail {

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

    private List<Contact> contacts;



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


}
