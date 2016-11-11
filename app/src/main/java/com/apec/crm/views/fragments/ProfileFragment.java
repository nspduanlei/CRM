package com.apec.crm.views.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.User;
import com.apec.crm.domin.entities.Version;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.ProfilePresenter;
import com.apec.crm.mvp.views.ProfileView;
import com.apec.crm.support.downloadmanager.manager.UpdateManager;
import com.apec.crm.support.picasso.ImageLoad;
import com.apec.crm.utils.AppUtils;
import com.apec.crm.utils.FileUtils;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.activities.ModifyPasswordActivity;
import com.apec.crm.views.activities.UserInfoActivity;
import com.apec.crm.views.fragments.core.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by duanlei on 16/9/12.
 */
public class ProfileFragment extends BaseFragment implements ProfileView {

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_username)
    TextView mTvUsername;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.tv_cache)
    TextView mTvCache;
    @BindView(R.id.tv_version)
    TextView mTvVersion;
    @BindView(R.id.tv_find_version)
    TextView mTvFindVersion;

    private boolean mHasNewVersion;
    private Version mVersion;

    @Inject
    ProfilePresenter mProfilePresenter;

    User mUser;

    @Override
    protected void initUI(View view) {
        mTvVersion.setText(String.format("v%s", AppUtils.getVersionName(getContext())));
        mTvCache.setText(FileUtils.getAutoFileOrFilesSize(getActivity().getCacheDir()));
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {
        DaggerUserComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .appComponent(myApplication.getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        mProfilePresenter.attachView(this);
        mProfilePresenter.onCreate();
    }

    /**
     * 退出登录
     * @param view
     */
    @OnClick(R.id.btn_login_out)
    void onLoginOutClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("退出登录");
        builder.setMessage("确定退出登录？");
        // 更新
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();

            loginOut();
        });
        // 稍后更新
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void loginOut() {
        SPUtils.put(getActivity(), SPUtils.TOKEN, "");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 修改秘密
     * @param view
     */
    @OnClick(R.id.fl_modify_password)
    void onModifyPassword(View view) {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 用户详细资料
     * @param view
     */
    @OnClick({R.id.iv_head, R.id.tv_username, R.id.tv_position})
    void onUserInfo(View view) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        if (mUser != null) {
            intent.putExtra(UserInfoActivity.ARG_USER, mUser);
        }
        startActivity(intent);
    }

    /**
     * 清楚缓冲
     * @param view
     */
    @OnClick(R.id.fl_cache)
    void onClearCacheClicked(View view) {
        FileUtils.delete(getActivity().getCacheDir());
        mTvCache.setText("0B");
    }

    /**
     * 版本更新
     * @param view
     */
    @OnClick({R.id.fl_version, R.id.tv_find_version})
    void onVersionOnClicked(View view) {
        if (mHasNewVersion) {
            new UpdateManager(getContext()).checkUpdate(mVersion.getRemake());
        }
    }

    @Override
    public void getVersionSuccess(Version version) {
        int currentVer = AppUtils.getVersionCode(getContext());
        mVersion = version;

        if (version.getVersionCode() > currentVer) {
            mHasNewVersion = true;
            mTvFindVersion.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getUserInfoSuccess(User user) {
        mUser = user;
        SPUtils.setUserInfo(getContext(), user);
        mTvPosition.setText(String.format("%s-%s", user.getDepName(), user.getPositionName()));
        mTvUsername.setText(user.getRealName());
        ImageLoad.loadUrlRound(getContext(), user.getImg(), mIvHead);
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

    @Override
    public void onStop() {
        super.onStop();
        mProfilePresenter.onStop();
    }

    public void updateUser() {
        mProfilePresenter.getUserInfo();
    }
}
