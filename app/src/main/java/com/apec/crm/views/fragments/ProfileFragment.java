package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.User;
import com.apec.crm.utils.AppUtils;
import com.apec.crm.utils.FileUtils;
import com.apec.crm.utils.SPUtils;
import com.apec.crm.views.activities.LoginActivity;
import com.apec.crm.views.activities.ModifyPasswordActivity;
import com.apec.crm.views.activities.UserInfoActivity;
import com.apec.crm.views.fragments.core.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by duanlei on 16/9/12.
 */
public class ProfileFragment extends BaseFragment {

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

    @Override
    protected void initUI(View view) {
        User user = SPUtils.getUserInfo(getContext());

        mTvPosition.setText(String.format("%s-%s", user.getDepName(), user.getPositionName()));
        mTvUsername.setText(user.getUserName());

        mTvVersion.setText(String.format("v%s", AppUtils.getVersionName(getContext())));

        mTvCache.setText(FileUtils.getAutoFileOrFilesSize(getActivity().getCacheDir()));
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.btn_login_out)
    void onLoginOutClicked(View view) {
        SPUtils.put(getActivity(), SPUtils.TOKEN, "");

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.fl_modify_password)
    void onModifyPassword(View view) {
        Intent intent = new Intent(getActivity(), ModifyPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.iv_head, R.id.tv_username, R.id.tv_position})
    void onUserInfo(View view) {
        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fl_cache)
    void onClearCacheClicked(View view) {
        FileUtils.delete(getActivity().getCacheDir());
        mTvCache.setText("0B");
    }


}
