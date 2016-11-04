package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/11/4.
 * 版本信息
 */

public class Version {
    private int versionCode;
    private String remake;
    private String url;
    private boolean flag;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
