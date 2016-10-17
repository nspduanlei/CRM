package com.apec.crm.domin.entities.func;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/29.
 */

public class ListResult<T> {
    private boolean succeed;
    private String errorCode;
    private String errorMsg;

    private ArrayList<T> data;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
