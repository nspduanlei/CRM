package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/25.
 * 片区
 */

public class OpenSea implements Parcelable {

    private String openSeaName;
    private String openSeaNo;

    public String getOpenSeaName() {
        return openSeaName;
    }

    public void setOpenSeaName(String openSeaName) {
        this.openSeaName = openSeaName;
    }

    public String getOpenSeaNo() {
        return openSeaNo;
    }

    public void setOpenSeaNo(String openSeaNo) {
        this.openSeaNo = openSeaNo;
    }

    protected OpenSea(Parcel in) {
        openSeaName = in.readString();
        openSeaNo = in.readString();
    }

    public static final Creator<OpenSea> CREATOR = new Creator<OpenSea>() {
        @Override
        public OpenSea createFromParcel(Parcel in) {
            return new OpenSea(in);
        }

        @Override
        public OpenSea[] newArray(int size) {
            return new OpenSea[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(openSeaName);
        dest.writeString(openSeaNo);
    }
}
