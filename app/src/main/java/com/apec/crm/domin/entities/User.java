package com.apec.crm.domin.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by duanlei on 16/9/27.
 */
public class User implements Parcelable {
    private String userNo;
    private String userName;
    private String password;

    private String token;

    //真实姓名
    private String realName;

    //部门名称
    private String depName;

    //职位名称
    private String positionName;

    private String positionLevel;

    //手机号码
    private String phoneNumber;

    //性别
    private String sex;

    //邮箱地址
    private String email;

    //用户头像
    private String img;

    public User() {}

    protected User(Parcel in) {
        userNo = in.readString();
        userName = in.readString();
        password = in.readString();
        token = in.readString();
        realName = in.readString();
        depName = in.readString();
        positionName = in.readString();
        positionLevel = in.readString();
        phoneNumber = in.readString();
        sex = in.readString();
        email = in.readString();
        img = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userNo);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeString(token);
        dest.writeString(realName);
        dest.writeString(depName);
        dest.writeString(positionName);
        dest.writeString(positionLevel);
        dest.writeString(phoneNumber);
        dest.writeString(sex);
        dest.writeString(email);
        dest.writeString(img);
    }
}
