package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/17.
 */

public class SelectContent implements Parcelable {
    private String id;
    private String name;
    private String other;


    public SelectContent(String id, String name, String other) {
        this.id = id;
        this.name = name;
        this.other = other;
    }

    protected SelectContent(Parcel in) {
        id = in.readString();
        name = in.readString();
        other = in.readString();
    }

    public static final Creator<SelectContent> CREATOR = new Creator<SelectContent>() {
        @Override
        public SelectContent createFromParcel(Parcel in) {
            return new SelectContent(in);
        }

        @Override
        public SelectContent[] newArray(int size) {
            return new SelectContent[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(other);
    }
}
