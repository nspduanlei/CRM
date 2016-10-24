package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 16/9/13.
 * 客户
 */
public class Custom implements Parcelable {

    private String id;
    private String customerName;
    private String time;
    private String customerAddress;
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    protected Custom(Parcel in) {
        id = in.readString();
        customerName = in.readString();
        time = in.readString();
        customerAddress = in.readString();
        icon = in.readString();
    }

    public static final Creator<Custom> CREATOR = new Creator<Custom>() {
        @Override
        public Custom createFromParcel(Parcel in) {
            return new Custom(in);
        }

        @Override
        public Custom[] newArray(int size) {
            return new Custom[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(customerName);
        dest.writeString(time);
        dest.writeString(customerAddress);
        dest.writeString(icon);
    }
}
