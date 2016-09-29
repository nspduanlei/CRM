package com.apec.crm.injector.components;


import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.injector.modules.AppModule;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

/**
 * Created by duanlei on 2016/5/9.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    MyApplication app();

    GoodsRepository goodsRepository();

    @Named("ui_thread")
    Scheduler uiThread();

    @Named("executor_thread")
    Scheduler executorThread();

    @Named("gson")
    Gson gson();
}
