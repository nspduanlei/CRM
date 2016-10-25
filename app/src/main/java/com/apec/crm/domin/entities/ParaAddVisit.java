package com.apec.crm.domin.entities;

import java.util.ArrayList;

/**
 * Created by duanlei on 2016/10/25.
 * 添加拜访的参数
 */

public class ParaAddVisit {

    private ArrayList<String> images;
    private String customerNo;
    private Address address;
    private String visitRemarks;
    private String contactNo;

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getVisitRemarks() {
        return visitRemarks;
    }

    public void setVisitRemarks(String visitRemarks) {
        this.visitRemarks = visitRemarks;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
