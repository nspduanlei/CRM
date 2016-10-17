package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.mvp.views.core.View;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/27.
 */

public interface SelectListView extends View {

    void onGetListSuccess(ArrayList<SelectContent> data);
}
