package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.mvp.views.core.View;

/**
 * Created by duanlei on 2016/10/25.
 */

public interface CustomDetailView extends View {

    void getCustomDetailSuccess(CustomDetail customDetail);

}
