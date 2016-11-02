package com.apec.crm.support.rest;

import android.content.Context;

import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.OpenSea;
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
import java.util.ArrayList;
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

    @Override
    public Observable<ListResult<OpenSea>> getOpenSea(RequestBody requestBody) {
        return mCrmApi.getOpenSea(requestBody);
    }

    @Override
    public Observable<Result<CustomDetail>> getCustomDetail(RequestBody requestBody) {
        return mCrmApi.getCustomDetail(requestBody);
    }

    @Override
    public Observable<Result> addContact(RequestBody requestBody) {
        return mCrmApi.addContact(requestBody);
    }

    @Override
    public Observable<Result> delContact(RequestBody requestBody) {
        return mCrmApi.delContact(requestBody);
    }

    @Override
    public Observable<Result> updateContact(RequestBody requestBody) {
        return mCrmApi.updateContact(requestBody);
    }

    @Override
    public Observable<Result> addVisit(RequestBody data, ArrayList<RequestBody> images) {

        switch (images.size()) {
            case 1:
                return mCrmApi.addVisit(data, images.get(0));
            case 2:
                return mCrmApi.addVisit(data, images.get(0), images.get(1));
            case 3:
                return mCrmApi.addVisit(data, images.get(0), images.get(1), images.get(2));
            default:
                return mCrmApi.addVisit(data);
        }
    }

    @Override
    public Observable<Result<ListPage<VisitRecord>>> getVisits(RequestBody requestBody, int type) {
        if (type == 0) {
            return mCrmApi.getVisitByU(requestBody);
        } else {
            return mCrmApi.getVisitByC(requestBody);
        }
    }

    @Override
    public Observable<Result> modifyPassword(RequestBody requestBody) {
        return mCrmApi.modifyPassword(requestBody);
    }

    @Override
    public Observable<Result> returnPool(RequestBody requestBody) {
        return mCrmApi.returnPool(requestBody);
    }

    @Override
    public Observable<Result> deleteCustom(RequestBody requestBody) {
        return mCrmApi.deleteCustom(requestBody);
    }

    @Override
    public Observable<Result> updateCustom(RequestBody requestBody) {
        return mCrmApi.updateCustom(requestBody);
    }
}
