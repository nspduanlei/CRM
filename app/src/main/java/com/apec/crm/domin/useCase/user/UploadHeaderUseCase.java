package com.apec.crm.domin.useCase.user;

import com.apec.crm.domin.entities.func.Result;
import com.apec.crm.domin.repository.GoodsRepository;
import com.apec.crm.domin.useCase.UseCase;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by duanlei on 16/9/27.
 * 上传头像
 */

public class UploadHeaderUseCase extends UseCase<Result<String>> {
    private final GoodsRepository mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    MultipartBody.Part mImage;

    @Inject
    public UploadHeaderUseCase(GoodsRepository repository,
                               @Named("ui_thread") Scheduler uiThread,
                               @Named("executor_thread") Scheduler executorThread) {
        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
    }

    public void setData(File file) {
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        mImage = MultipartBody.Part.createFormData("image", file.getName(),
                        requestBody);
    }

    @Override
    public Observable<Result<String>> buildObservable() {
        return mRepository.uploadHeader(mImage)
                .observeOn(mUiThread)
                .subscribeOn(mExecutorThread);
    }
}
