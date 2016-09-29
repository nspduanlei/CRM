package com.apec.crm.support.rest;


import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface CrmApi {

    @FormUrlEncoded
    @POST("login-service/doLogin.apec")
    Observable<Result<User>> login(@Field("userName") String userName,
                           @Field("password") String password);

    /**
     * 获取拜访记录
     */
    @POST("VISIT-RECORD-SERVICE/getVisitRecordsByUser.apec")
    Observable<Result<ListPage<VisitRecord>>> getVisitRecord(@Body String body);



}
