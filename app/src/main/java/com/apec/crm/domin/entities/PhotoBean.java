package com.apec.crm.domin.entities;

/**
 * Created by duanlei on 2016/10/28.
 */

public class PhotoBean {
    private String id;
    private String photoPath;
    private String url;

    public PhotoBean(String photoPath) {
        this.photoPath = photoPath;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
