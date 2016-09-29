package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 16/9/20.
 * 联系人
 */

public class Contact {
    //身份
    private String identity;
    //称呼
    private String nickName;
    private String phoneNum;

    public Contact(String identity, String nickName, String phoneNum) {
        this.identity = identity;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
