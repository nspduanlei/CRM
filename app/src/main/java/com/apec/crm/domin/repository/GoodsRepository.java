package com.apec.crm.domin.repository;


import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.domin.entities.func.ListPage;
import com.apec.crm.domin.entities.func.Result;

import rx.Observable;

/**
 * Created by duanlei on 2016/5/10.
 */
public interface GoodsRepository {

    Observable<Result<User>> login(String userName, String password);

    Observable<Result<ListPage<VisitRecord>>> getVisitRecord(String jsonString);
}