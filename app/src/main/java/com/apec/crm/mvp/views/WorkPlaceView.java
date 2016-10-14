package com.apec.crm.mvp.views;

import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.mvp.views.core.View;

/**
 * Created by duanlei on 16/9/27.
 */

public interface WorkPlaceView extends View {

    void onMyCountSuccess(MyCount data);
}
