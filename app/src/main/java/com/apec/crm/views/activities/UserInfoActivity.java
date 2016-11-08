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
import com.apec.crm.support.eventBus.RxBus;
import com.apec.crm.support.picasso.ImageLoad;
import com.apec.crm.utils.GalleryFinalUtils;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.views.activities.core.BaseActivity;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by duanlei on 2016/10/13.
 * 用户信息
 */

public class UserInfoActivity extends BaseActivity implements UserInfoView,
        GalleryFinal.OnHanlderResultCallback {

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

    GalleryFinalUtils mGalleryFinalUtils;

    User mUser;

    public static final String ARG_USER = "arg_user";

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_user_info, R.string.user_info);
        mUser = getIntent().getParcelableExtra(ARG_USER);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        if (mUser != null) {
            fillData();
        }
        mGalleryFinalUtils = new GalleryFinalUtils(this);
    }

    public void fillData() {
        mTvUsername.setText(mUser.getRealName());
        mTvPosition.setText(mUser.getPositionName());
        mTvDep.setText(mUser.getDepName());
        mTvPhoneNumber.setText(mUser.getPhoneNumber());
        mTvSex.setText(mUser.getSex());
        mTvEmail.setText(mUser.getEmail());
        ImageLoad.loadUrlRound(this, mUser.getImg(), mIvHead);
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
        if (mUser == null) {
            mUserInfoPresenter.getUserInfo();
        }
    }

    @Override
    public void getUserInfoSuccess(User user) {
        fillData();
    }

    @Override
    public void onUploadHeadSuccess(String imgUrl) {
        SPUtils.put(this, SPUtils.USER_IMG, imgUrl);
        RxBus.getDefault().post(MainActivity.ACTION_UPDATE_USER);
        ImageLoad.loadUrlRound(this, imgUrl, mIvHead);
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

    @OnClick(R.id.iv_head)
    void onHeadClicked(View view) {
        mGalleryFinalUtils.selectUserHead(this);
    }

    @Override
    public void onHanlderSuccess(int request, List<PhotoInfo> resultList) {
        if (request == GalleryFinalUtils.REQUEST_SELECT_IMAGE) {
            File imgFile = new File(resultList.get(0).getPhotoPath());
            mUserInfoPresenter.uploadUserHead(imgFile);
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        mUserInfoPresenter.onStop();
    }
}
