package com.apec.crm.support.rest;


import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface CrmApi {

    /**
     * 登录
     */
    @POST("login-service/doLogin.apec")
    Observable<Result<User>> login(@Body RequestBody body);

    /**
     * 获取我本月的统计数据
     */
    @POST("sysparam-service/getMyCount.apec")
    Observable<Result<MyCount>> getMyCount();

    /**
     * 获取拜访记录
     */
    @POST("VISIT-RECORD-SERVICE/getVisitRecordsByUser.apec")
    Observable<Result<ListPage<VisitRecord>>> getVisitRecord(@Body RequestBody body);

    /**
     * 获取客户列表
     */
    @POST("CUSTOMER-SERVICE/searchCustomer.apec")
    Observable<Result<ListPage<Custom>>> getCustomList(@Body RequestBody body);

}
