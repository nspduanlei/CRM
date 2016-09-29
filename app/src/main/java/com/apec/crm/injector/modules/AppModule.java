package com.apec.crm.injector.modules;


import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.support.rest.RestDataSource;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by duanlei on 2016/5/9.
 */
@Module
public class AppModule {

    private final MyApplication mMyApplication;

    public AppModule(MyApplication myApplication) {
        this.mMyApplication = myApplication;
    }

    @Provides
    @Singleton
    MyApplication provideMyApplicationContext() {
        return mMyApplication;
    }

    @Provides
    @Singleton
    GoodsRepository provideGoodsRepository() {
        return new RestDataSource(mMyApplication);
    }

    @Provides @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.io();
    }

    @Provides @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides @Named("gson")
    Gson provideGson() {
        return new Gson();
    }
}
