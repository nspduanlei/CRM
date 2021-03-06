package com.apec.crm.support.rest;


import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.CustomDetail;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.OpenSea;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.Version;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.entities.func.Result;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * 获取版本信息
     */
    @POST("sysparam-service/getNowVersionInfo.apec")
    Observable<Result<Version>> getVersion();

    /**
     * 获取用户信息
     */
    @POST("userAppInterface-service/getUserInfo.apec")
    Observable<Result<User>> getUserInfo();

    /**
     * 修改密码
     */
    @POST("userAppInterface-service/updateUserPassword.apec")
    Observable<Result> modifyPassword(@Body RequestBody body);

    /**
     * 获取用户下属
     */
    @POST("userAppInterface-service/getDepartmentList.apec")
    Observable<Result<ListPage<User>>> getUserList(@Body RequestBody body);

    @POST("userAppInterface-service/getDepartmentList.apec")
    Observable<Result<ListPage<User>>> getUserList();

    /**
     * 上传用户头像
     */
    @Multipart
    @POST("userAppInterface-service/updateUserImage/uploadPicture.apec")
    Observable<Result<String>> uploadUserHead(@Part MultipartBody.Part image);

    /**
     * 获取客户列表
     */
    @POST("CUSTOMER-SERVICE/searchCustomer.apec")
    Observable<Result<ListPage<Custom>>> getCustomList(@Body RequestBody body);

    /**
     * 获取公海客户列表
     */
    @POST("CUSTOMER-SERVICE/searchOpenSea.apec")
    Observable<Result<ListPage<Custom>>> getPublicCustomList(@Body RequestBody body);

    /**
     * 选择行政区域
     */
    @POST("CUSTOMER-SERVICE/getArear.apec")
    Observable<ListResult<Area>> getArea(@Body RequestBody body);

    /**
     * 添加客户
     */
    @POST("CUSTOMER-SERVICE/addCustomer.apec")
    Observable<Result> addCustom(@Body RequestBody body);

    /**
     * 获取供选择的客户属性
     */
    @POST("CUSTOMER-SERVICE/getAllSysparam.apec")
    Observable<ListResult<SelectContent>> getCustomAttr(@Body RequestBody body);

    /**
     * 获取客户联系人
     */
    @POST("CUSTOMER-SERVICE/searchContact.apec")
    Observable<ListResult<Contact>> getContacts(@Body RequestBody body);


    /**
     * 根据区域id获取片区
     */
    @POST("CUSTOMER-SERVICE/queryOpenSeaByRegionId.apec")
    Observable<ListResult<OpenSea>> getOpenSea(@Body RequestBody body);


    /**
     * 获取客户详情
     */
    @POST("CUSTOMER-SERVICE/findOneCustomer.apec")
    Observable<Result<CustomDetail>> getCustomDetail(@Body RequestBody body);

    /**
     * 更新客户
     */
    @POST("CUSTOMER-SERVICE/updateCustomer.apec")
    Observable<Result> updateCustom(@Body RequestBody body);


    /**
     * 添加客户联系人
     */
    @POST("CUSTOMER-SERVICE/addContact.apec")
    Observable<Result> addContact(@Body RequestBody body);


    /**
     * 拾取客户
     */
    @POST("CUSTOMER-SERVICE/getCustomer.apec")
    Observable<Result> pickCustom(@Body RequestBody body);


    /**
     * 删除客户联系人
     */
    @POST("CUSTOMER-SERVICE/deleteContact.apec")
    Observable<Result> delContact(@Body RequestBody body);

    /**
     * 更新客户联系人
     */
    @POST("CUSTOMER-SERVICE/updateContact.apec")
    Observable<Result> updateContact(@Body RequestBody body);

    /**
     * 退回公海
     */
    @POST("CUSTOMER-SERVICE/returnOpenCustomer.apec")
    Observable<Result> returnPool(@Body RequestBody body);

    /**
     * 删除客户
     */
    @POST("CUSTOMER-SERVICE/deleteCustomer.apec")
    Observable<Result> deleteCustom(@Body RequestBody body);

    /**
     * 添加拜访
     */
    @Multipart
    @POST("VISIT-RECORD-SERVICE/addVisitRecord/uploadPicture.apec")
    Observable<Result> addVisit(@Part("data") RequestBody data,
                                @Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("VISIT-RECORD-SERVICE/addVisitRecord/uploadPicture.apec")
    Observable<Result> addVisit(@Part("data") RequestBody data);

    /**
     * 获取拜访记录
     */
    @POST("VISIT-RECORD-SERVICE/getVisitRecordsByUser.apec")
    Observable<Result<ListPage<VisitRecord>>> getVisitByU(@Body RequestBody body);

    @POST("VISIT-RECORD-SERVICE/getVisitRecordsByCustomer.apec")
    Observable<Result<ListPage<VisitRecord>>> getVisitByC(@Body RequestBody body);

}
