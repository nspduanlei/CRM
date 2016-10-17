package com.apec.crm.domin.repository;


import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.entities.func.Result;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {

    Observable<Result<User>> login(RequestBody jsonString);

    Observable<Result<ListPage<VisitRecord>>> getVisitRecord(RequestBody jsonString);

    Observable<Result<ListPage<Custom>>> getCustomerList(RequestBody requestBody);

    Observable<Result<MyCount>> getMyCount();

    Observable<ListResult<Area>> getArea(RequestBody requestBody);

    Observable<ListResult<SelectContent>> getCustomAttribute(RequestBody requestBody);

    Observable<Result> addCustom(RequestBody requestBody);
}