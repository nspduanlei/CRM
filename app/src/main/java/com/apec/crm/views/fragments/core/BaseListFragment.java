package com.apec.crm.views.fragments.core;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.widget.recyclerView.CommonRecyclerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by duanlei on 16/9/28.
 */

public abstract class BaseListFragment extends Fragment {

    private static final String LOG_TAG = BaseListFragment.class.getSimpleName();

    //下拉刷新
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(android.R.id.list)
    RecyclerView mRecyclerView;

    private CommonRecyclerAdapter mListAdapter;

    //底部提示
    private Snackbar mLoadingMoreSnack;

    //默认允许下拉刷新
    private boolean mIsRefresh = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        initUI(view);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        initDependencyInjector(myApplication);
        initPresenter();
        return view;
    }

    protected abstract void initUI(View view);
    protected abstract CommonRecyclerAdapter getAdapter();
    protected abstract void initDependencyInjector(MyApplication myApplication);
    protected abstract void initPresenter();
    protected abstract void loadFirstPage();
    protected abstract void loadOtherPage();

    /**
     * 设置是否启用下拉刷新
     * @param isRefresh
     */
    public void setIsRefresh(boolean isRefresh) {
        mIsRefresh = isRefresh;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mListAdapter = getAdapter();
        mRecyclerView.setAdapter(mListAdapter);


        mSwipeRefreshLayout.setRefreshing(true);
        initiateRefresh();

        if (mIsRefresh) {
            mSwipeRefreshLayout.setOnRefreshListener(() -> {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
            });
        }
    }

    private void initiateRefresh() {
        Log.i(LOG_TAG, "initiateRefresh");
        loadFirstPage();
    }

    public void onRefreshComplete(List result) {
        Log.i(LOG_TAG, "onRefreshComplete");
        mListAdapter.clear();
        mListAdapter.addAll(result);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void onLoadMoreComplete(List result) {
        mListAdapter.clear();
        mListAdapter.addAll(result);
        hideLoadingMore();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                //加载更多
                onListEndReached();
            }
        }
    };

    private void onListEndReached() {
        if (mLoadingMoreSnack != null && mLoadingMoreSnack.isShown()) {
            return;
        }
        Log.i(LOG_TAG, "loadMore");
        showLoadingMore();

        loadOtherPage();
    }

    public void showLoadingMore() {
        mLoadingMoreSnack = Snackbar.make(mRecyclerView,
                getString(R.string.message_loading_more_characters), Snackbar.LENGTH_INDEFINITE);
        mLoadingMoreSnack.show();
    }

    public void hideLoadingMore() {
        if (mLoadingMoreSnack != null)
            mLoadingMoreSnack.dismiss();
    }

}
