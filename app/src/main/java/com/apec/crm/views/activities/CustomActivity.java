package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.injector.components.DaggerCustomComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.CustomPresenter;
import com.apec.crm.mvp.views.CustomView;
import com.apec.crm.support.eventBus.RxBus;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.VisitRecordFragment;
import com.apec.crm.views.widget.RoundTextView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/19.
 */
public class CustomActivity extends BaseActivity implements CustomView {

    @BindView(R.id.lv_dialog_menu)
    ListView mLVDialogMenu;

    Custom mCustom;

    @BindView(R.id.tv_custom_name)
    TextView mTvCustomName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.roundTextView)
    RoundTextView mRoundTextView;
    @BindView(R.id.tv_head)
    TextView mTvHead;

    @BindView(R.id.fl_body)
    FrameLayout mFlMenu;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    VisitRecordFragment mVisitRecordFragment;
    public static final String ARG_CUSTOM = "arg_custom";
    private VisitRecordFilter mVisitRecordFilter = new VisitRecordFilter();
    @Inject
    CustomPresenter mCustomPresenter;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom, R.string.custom_home);

        setBtnImage(R.drawable.nav_more_drawable, v -> {
            showDialog();
        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCustom = getIntent().getParcelableExtra(ARG_CUSTOM);

        if (mCustom == null) {
            this.finish();
        }

        mVisitRecordFilter.setCustomerNo(mCustom.getId());

        mRoundTextView.setColor(MyUtils.getColor(mCustom.getIcon()));

        MyUtils.setHeadText(mTvHead, mCustom.getCustomerName());

        mTvCustomName.setText(mCustom.getCustomerName());
        mTvAddress.setText(mCustom.getCustomerAddress());

        initFragment();

        initMenu();
    }

    private void initFragment() {
        mVisitRecordFragment = VisitRecordFragment.newInstance(mVisitRecordFilter);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mVisitRecordFragment, "visitList")
                .commit();
    }

    private void initMenu() {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(1, "添加拜访"));
        menuEntities.add(new MenuEntity(2, "退回公海"));
        menuEntities.add(new MenuEntity(3, "废弃"));
        mLVDialogMenu.setAdapter(new CommonAdapter<MenuEntity>(this, menuEntities,
                R.layout.item_dialog_menu) {
            @Override
            public void convert(MyViewHolder holder, MenuEntity menuEntity) {
                holder.setText(R.id.tv_menu_name, menuEntity.getName());
            }
        });

        mLVDialogMenu.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    Intent intent = new Intent(this, AddVisitActivity.class);
                    intent.putExtra(AddVisitActivity.ARG_CUSTOM_ID, mCustom.getId());
                    intent.putExtra(AddVisitActivity.ARG_CUSTOM_NAME, mCustom.getCustomerName());
                    startActivityForResult(intent, Constants.REQUEST_CODE_ADD_VISIT);
                    break;
                case 1:
                    mCustomPresenter.returnPool(mCustom.getId());
                    break;
                case 2:
                    mCustomPresenter.deleteCustom(mCustom.getId());
                    break;
            }
            hideDialog();
        });
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
        mCustomPresenter.attachView(this);
        mCustomPresenter.onCreate();
    }

    /**
     * 跳转到客户详情
     *
     * @param view
     */
    @OnClick(R.id.rl_custom)
    void OnPlateCustomClicked(View view) {
        Intent intent = new Intent(this, CustomDetailActivity.class);
        intent.putExtra(CustomDetailActivity.ARG_CUSTOM_ID, mCustom.getId());
        startActivity(intent);
    }

    /**
     * 添加拜访
     * @param view
     */
    @OnClick(R.id.btn_add_visit)
    void OnAddVisitClicked(View view) {
        Intent intent = new Intent(this, AddVisitActivity.class);
        intent.putExtra(AddVisitActivity.ARG_CUSTOM_ID, mCustom.getId());
        intent.putExtra(AddVisitActivity.ARG_CUSTOM_NAME, mCustom.getCustomerName());
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_VISIT);
    }

    @OnClick(R.id.fl_body)
    void OnBodyClicked(View view) {
        hideDialog();
    }

    private void showDialog() {
        if (mFlMenu.getVisibility() == View.VISIBLE) {
            mFlMenu.setVisibility(View.GONE);
        } else {
            mFlMenu.setVisibility(View.VISIBLE);
        }
    }

    private void hideDialog() {
        if (mFlMenu.getVisibility() == View.VISIBLE) {
            mFlMenu.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_ADD_VISIT) {
            if (resultCode == Constants.RESULT_CODE_ADD_VISIT) {
                mVisitRecordFragment.updateForFilter(mVisitRecordFilter);
            }
        }
    }

    @Override
    public void onRetPoolSuccess() {
        T.showShort(this, "退回公海成功");

        RxBus.getDefault().post(MainActivity.ACTION_UPDATE_CUSTOMS);

        this.finish();
    }

    @Override
    public void onDelCustomSuccess() {
        T.showShort(this, "删除客户成功");

        RxBus.getDefault().post(MainActivity.ACTION_UPDATE_CUSTOMS);

        this.finish();
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

    @Override
    protected void onStop() {
        super.onStop();
        mCustomPresenter.onStop();
    }
}
