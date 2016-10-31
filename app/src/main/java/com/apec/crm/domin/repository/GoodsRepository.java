package com.apec.crm.domin.repository;


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

import java.util.ArrayList;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {

    Observable<Result<User>> login(RequestBody jsonString);

    Observable<Result<ListPage<Custom>>> getCustomerList(RequestBody requestBody);

    Observable<Result<MyCount>> getMyCount();

    Observable<ListResult<Area>> getArea(RequestBody requestBody);

    Observable<ListResult<SelectContent>> getCustomAttribute(RequestBody requestBody);

    Observable<Result> addCustom(RequestBody requestBody);

    Observable<Result<User>> getUserInfo();

    Observable<ListResult<Contact>> getContact(RequestBody requestBody);

    Observable<ListResult<OpenSea>> getOpenSea(RequestBody requestBody);

    Observable<Result<CustomDetail>> getCustomDetail(RequestBody requestBody);

    Observable<Result> addContact(RequestBody requestBody);

    Observable<Result> delContact(RequestBody requestBody);

    Observable<Result> updateContact(RequestBody requestBody);

    Observable<Result> addVisit(RequestBody data, ArrayList<RequestBody> images);

    Observable<Result> updateCustom(RequestBody requestBody);

    Observable<Result<ListPage<VisitRecord>>> getVisits(RequestBody requestBody, int type);
}