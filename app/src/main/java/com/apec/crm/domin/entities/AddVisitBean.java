package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/10/26.
 * 添加拜访
 */

public class AddVisitBean {
    /**
     * customerNo : dnkakdnkla
     * customerName : 张三的店
     * contactName : 王老板
     * contactPhone : 11333331112
     * customerAddress : 深圳市福田区
     * visitRemarks : 拜访内容：客户很喜欢xx白糖
     * locationJson : {"latitude":0,"longitude":0}
     */
    private String customerNo;
    private String customerName;
    private String contactName;
    private String contactPhone;
    private String customerAddress;
    private String visitRemarks;
    private String contactNo;
    /**
     * latitude : 0.0
     * longitude : 0.0
     */
    private LocationJsonBean locationJson;


    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getVisitRemarks() {
        return visitRemarks;
    }

    public void setVisitRemarks(String visitRemarks) {
        this.visitRemarks = visitRemarks;
    }

    public LocationJsonBean getLocationJson() {
        return locationJson;
    }

    public void setLocationJson(LocationJsonBean locationJson) {
        this.locationJson = locationJson;
    }

    public static class LocationJsonBean {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
