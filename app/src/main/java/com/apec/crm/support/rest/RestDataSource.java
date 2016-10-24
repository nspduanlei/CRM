package com.apec.crm.support.rest;

import android.content.Context;

import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.support.rest.interceptors.CacheInterceptor;
import com.apec.crm.support.rest.interceptors.HeaderInterceptor;
import com.apec.crm.support.rest.interceptors.LoggingInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RestDataSource implements GoodsRepository {

    private static final long HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    public static String END_POINT = Constants.TEST_BASE_URL;
    private final CrmApi mCrmApi;

    @Inject
    public RestDataSource(Context context) {

        //开启响应数据缓存到文件系统功能
        final File cacheDir = new File(context.getCacheDir(), "httpResponseCache");

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)

                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)

                .addInterceptor(new LoggingInterceptor(context))
                .addInterceptor(new CacheInterceptor(context))

                //用json文件模拟数据
                //.addInterceptor(new MockInterceptor(context))

                .addNetworkInterceptor(new HeaderInterceptor(context))
                .addNetworkInterceptor(new StethoInterceptor()) //debug
                .cache(new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE))
                .build();


        Retrofit crmApiAdapter = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mCrmApi = crmApiAdapter.create(CrmApi.class);
    }


    @Override
    public Observable<Result<User>> login(RequestBody jsonString) {
        return mCrmApi.login(jsonString);
    }

    @Override
    public Observable<Result<ListPage<VisitRecord>>> getVisitRecord(RequestBody jsonString) {
        return mCrmApi.getVisitRecord(jsonString);
    }

    @Override
    public Observable<Result<ListPage<Custom>>> getCustomerList(RequestBody requestBody) {
        return mCrmApi.getCustomList(requestBody);
    }

    @Override
    public Observable<Result<MyCount>> getMyCount() {
        return mCrmApi.getMyCount();
    }

    @Override
    public Observable<ListResult<Area>> getArea(RequestBody requestBody) {
        return mCrmApi.getArea(requestBody);
    }

    @Override
    public Observable<ListResult<SelectContent>> getCustomAttribute(RequestBody requestBody) {
        return mCrmApi.getCustomAttr(requestBody);
    }

    @Override
    public Observable<Result> addCustom(RequestBody requestBody) {
        return mCrmApi.addCustom(requestBody);
    }

    @Override
    public Observable<Result<User>> getUserInfo() {
        return mCrmApi.getUserInfo();
    }

    @Override
    public Observable<ListResult<Contact>> getContact(RequestBody requestBody) {
        return mCrmApi.getContacts(requestBody);
    }
}
