package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/11/1.
 */

public class VisitsFilter implements Parcelable {

    private String customNo;
    private String userNo;
    private String customName;
    private String userName;

    public VisitsFilter() {}

    protected VisitsFilter(Parcel in) {
        customNo = in.readString();
        userNo = in.readString();
        customName = in.readString();
        userName = in.readString();
    }

    public static final Creator<VisitsFilter> CREATOR = new Creator<VisitsFilter>() {
        @Override
        public VisitsFilter createFromParcel(Parcel in) {
            return new VisitsFilter(in);
        }

        @Override
        public VisitsFilter[] newArray(int size) {
            return new VisitsFilter[size];
        }
    };

    public String getCustomNo() {
        return customNo;
    }

    public void setCustomNo(String customNo) {
        this.customNo = customNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(customNo);
        dest.writeString(userNo);
        dest.writeString(customName);
        dest.writeString(userName);
    }
}
