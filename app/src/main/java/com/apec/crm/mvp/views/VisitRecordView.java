package com.apec.crm.mvp.views;

import com.apec.crm.mvp.views.core.View;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/27.
 */

public interface VisitRecordView extends View {

    void onRefreshSuccess(ArrayList data);
    void onLoadMoreSuccess();

}
