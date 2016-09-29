package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.User;
import com.apec.crm.mvp.views.core.View;

/**
 * Created by duanlei on 16/9/27.
 */

public interface LoginView extends View {

    void onLoginSuccess(User user);
}
