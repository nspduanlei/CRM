package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 16/9/20.
 * 联系人
 */

public class Contact implements Parcelable {

    private String id;
    private String contactName;
    private String contactPhone;
    private String contactPost;

    private String customerNo;
    private String contactTel; //座机
    private String contactSex;
    private String contactAge;

    public Contact() {}

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactSex() {
        return contactSex;
    }

    public void setContactSex(String contactSex) {
        this.contactSex = contactSex;
    }

    public String getContactAge() {
        return contactAge;
    }

    public void setContactAge(String contactAge) {
        this.contactAge = contactAge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContactPost() {
        return contactPost;
    }

    public void setContactPost(String contactPost) {
        this.contactPost = contactPost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(contactName);
        dest.writeString(contactPhone);
        dest.writeString(contactPost);
        dest.writeString(customerNo);
        dest.writeString(contactTel);
        dest.writeString(contactSex);
        dest.writeString(contactAge);
    }

    protected Contact(Parcel in) {
        id = in.readString();
        contactName = in.readString();
        contactPhone = in.readString();
        contactPost = in.readString();
        customerNo = in.readString();
        contactTel = in.readString();
        contactSex = in.readString();
        contactAge = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
