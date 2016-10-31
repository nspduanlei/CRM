package com.apec.crm.domin.useCase.visit;

import com.apec.crm.domin.entities.AddVisitBean;
import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;
import com.apec.crm.utils.L;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 * 添加拜访
 */

public class AddVisitUseCase extends UseCase<Result> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final Gson mGson;

    RequestBody mData;
    ArrayList<RequestBody> mImages;

    @Inject
    public AddVisitUseCase(GoodsRepository repository,
                           @Named("ui_thread") Scheduler uiThread,
                           @Named("executor_thread") Scheduler executorThread,
                           @Named("gson") Gson gson) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mGson = gson;
    }

    public void setData(AddVisitBean addVisitBean, ArrayList<File> files) {
        mData = RequestBody.create(
                MediaType.parse("application/json"),
                mGson.toJson(addVisitBean));

        L.e(mGson.toJson(addVisitBean));

        mImages = new ArrayList<>();
        for (int i = 0 ; i < files.size(); i++) {
            RequestBody requestBody =
                    RequestBody.create(MediaType.parse("image/*"), files.get(i));
            mImages.add(requestBody);
        }
    }

    @Override
    public Observable<Result> buildObservable() {
        return mRepository.addVisit(mData, mImages)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
