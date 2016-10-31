package com.apec.crm.domin.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by duanlei on 16/9/19.
 * 拜访记录
 */
public class VisitRecord {

    private String contactName;
    private String contactNo;
    private String contactPhone;
    private long createDate;
    private String customerAddress;
    private String customerName;
    private String customerNo;
    private int id;
    private String locationJson;
    private String userName;
    private String visitNo;
    private String visitRemarks;

    private List<ImageItemBean> imageItems;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationJson() {
        return locationJson;
    }

    public void setLocationJson(String locationJson) {
        this.locationJson = locationJson;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getVisitRemarks() {
        return visitRemarks;
    }

    public void setVisitRemarks(String visitRemarks) {
        this.visitRemarks = visitRemarks;
    }

    public List<ImageItemBean> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<ImageItemBean> imageItems) {
        this.imageItems = imageItems;
    }


    public ArrayList<String> getOriginalUrlList() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0 ; i < imageItems.size(); i++) {
            arrayList.add(imageItems.get(i).getOriginalUrl());
        }
        return arrayList;
    }
}
