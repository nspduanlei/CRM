package com.apec.crm.domin.useCase;

/**
 * Created by duanlei on 16/9/27.
 */

import rx.Observable;

public abstract class UseCase<T> {
    public abstract Observable<T> buildObservable();
    public Observable<T> execute() {
        return buildObservable();
    }
}
