package com.apec.crm.views.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.User;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.UserInfoPresenter;
import com.apec.crm.mvp.views.UserInfoView;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.views.activities.core.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 2016/10/13.
 * 用户信息
 */

public class UserInfoActivity extends BaseActivity implements UserInfoView {

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.tv_phone_number)
    TextView mTvPhoneNumber;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_dep)
    TextView mTvDep;
    @BindView(R.id.tv_email)
    TextView mTvEmail;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Inject
    UserInfoPresenter mUserInfoPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_user_info, R.string.user_info);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        User user = SPUtils.getUserInfo(this);

        mTvUsername.setText(user.getUserName());
        mTvPosition.setText(user.getPositionName());
        mTvDep.setText(user.getDepName());
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {
        DaggerUserComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(application.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mUserInfoPresenter.attachView(this);
        mUserInfoPresenter.onCreate();
    }

    @Override
    public void getUserInfoSuccess(User user) {
        mTvPhoneNumber.setText(user.getPhoneNumber());
        mTvSex.setText(user.getSex());
        mTvEmail.setText(user.getEmail());
    }

    @Override
    public void showLoadingView() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onError(String errorCode, String errorMsg) {

    }
}
