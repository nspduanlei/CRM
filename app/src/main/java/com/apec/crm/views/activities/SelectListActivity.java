package com.apec.crm.views.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.SelectContent;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.SelectListPresenter;
import com.apec.crm.mvp.views.SelectListView;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/10/17.
 */

public class SelectListActivity extends BaseActivity implements SelectListView {

    public static final int CUSTOM_TYPE = 0; //客户类型
    private int mListType;

    @BindView(R.id.lv_content)
    ListView mLVContent;

    @Inject
    SelectListPresenter mSelectListPresenter;

    private CommonAdapter mAdapter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_select_list);

        mAdapter = new CommonAdapter<SelectContent>(this, new ArrayList<>(),
                R.layout.select_list_item) {
            @Override
            public void convert(MyViewHolder holder, SelectContent selectContent) {
                holder.setText(R.id.tv_content, selectContent.getName());
            }
        };
        mLVContent.setAdapter(mAdapter);

        mListType = getIntent().getIntExtra("listType", 0);
        switch (mListType) {
            case CUSTOM_TYPE:
                setUpTitle(R.string.custom_type);


                break;
            default:
                break;

        }
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerCustomComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mSelectListPresenter.attachView(this);
        mSelectListPresenter.onCreate();
    }

    @Override
    public void onGetListSuccess(ArrayList<SelectContent> data) {
        mAdapter.clear();
        mAdapter.addAll(data);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }
}
