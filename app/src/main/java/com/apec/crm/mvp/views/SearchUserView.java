package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.User;
import com.apec.crm.mvp.views.core.View;

import java.util.List;

/**
 * Created by duanlei on 2016/11/10.
 */

public interface SearchUserView extends View {
    void onSearchSuccess(List<User> users);
}
