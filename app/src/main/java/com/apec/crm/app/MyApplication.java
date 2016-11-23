package com.apec.crm.app;

import android.app.Application;

import com.apec.crm.injector.components.AppComponent;
import com.apec.crm.injector.components.DaggerAppComponent;
import com.apec.crm.injector.modules.AppModule;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by duanlei on 16/9/9.
 */
public class MyApplication extends Application {

    private AppComponent mAppComponent;

    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initializeInjector();
        initializeDebug();
        LeakCanary.install(this);
    }

    private void initializeDebug() {
        Stetho.initializeWithDefaults(this);
    }

    private void initializeInjector() {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
