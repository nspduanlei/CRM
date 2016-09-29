package com.apec.crm.mvp.presenters.core;

/**
 * Created by duanlei on 16/9/28.
 */

public abstract class ListPresenter {

    //总页数
    public static final int LIST_ITEM_COUNT = 20;

    //当前页
    public int mCurrentPage = 1;

    public void refresh() {
        mCurrentPage = 1;
        doRefresh();
    }

    public void loadMore() {
        mCurrentPage ++;
        doLoadMore();
    }

    protected abstract void doRefresh();
    protected abstract void doLoadMore();

}
