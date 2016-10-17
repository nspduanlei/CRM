package com.apec.crm.views.fragments.core;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @BindView(R.id.tv_empty)
    TextView mEmptyDes;

    @BindView(R.id.ll_empty)
    LinearLayout mEmptyView;

    @BindView(R.id.tv_text_list)
    TextView mListCount;

    private CommonRecyclerAdapter mListAdapter;

    //默认允许下拉刷新
    private boolean mIsRefresh = true;

    private boolean isLoading;

//    private int visibleThreshold = 20;
//    private int lastVisibleItem, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        MyApplication myApplication = (MyApplication) getActivity().getApplication();
        initDependencyInjector(myApplication);
        initPresenter();
        return view;
    }

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
        mListAdapter.clear();
        mListAdapter.addAll(result);
        mSwipeRefreshLayout.setRefreshing(false);
        isLoading = false;
    }

    public void onLoadMoreComplete(List result) {
        mListAdapter.removeLoading();
        mListAdapter.addAll(result);
        isLoading = false;
    }

    public void onDataEnd() {
        mListAdapter.removeLoading();
        isLoading = false;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                //加载更多
                onListEndReached();
                isLoading = true;
            }
        }

//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//            totalItemCount = layoutManager.getItemCount();
//            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//
//            L.e("totalItemCount：" + totalItemCount + "， lastVisibleItem：" + lastVisibleItem);
//
//            if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                onListEndReached();
//                isLoading = true;
//            }
//        }
    };

    private void onListEndReached() {
        Log.i(LOG_TAG, "loadMore");
        mListAdapter.insertedLoading();
        loadOtherPage();
    }

    protected void setListCount(String s) {
        mListCount.setText(s);
    }

    protected void setEmptyText(String msg) {
        mEmptyDes.setText(msg);
    }

    protected void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    protected void hideEmpty() {
        mEmptyView.setVisibility(View.GONE);
    }
}
