package com.apec.crm.mvp.presenters.core;


import com.apec.crm.mvp.views.core.View;

public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView(View v);

    void onCreate();
}
