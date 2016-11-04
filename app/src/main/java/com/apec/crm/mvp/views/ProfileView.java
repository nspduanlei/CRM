package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.Version;
import com.apec.crm.mvp.views.core.View;

/**
 * Created by duanlei on 2016/11/4.
 */
public interface ProfileView extends View {
    void getVersionSuccess(Version version);
    void getUserInfoSuccess(User user);
}
