package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 2016/10/31.
 */

public class ImageItemBean implements Parcelable {

    private String originalUrl;
    private String thumbnailUrl;

    protected ImageItemBean(Parcel in) {
        originalUrl = in.readString();
        thumbnailUrl = in.readString();
    }

    public static final Creator<ImageItemBean> CREATOR = new Creator<ImageItemBean>() {
        @Override
        public ImageItemBean createFromParcel(Parcel in) {
            return new ImageItemBean(in);
        }

        @Override
        public ImageItemBean[] newArray(int size) {
            return new ImageItemBean[size];
        }
    };

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalUrl);
        dest.writeString(thumbnailUrl);
    }
}
