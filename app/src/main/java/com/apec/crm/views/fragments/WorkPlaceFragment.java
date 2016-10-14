package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.domin.entities.MyCount;
import com.apec.crm.injector.components.DaggerUserComponent;
import com.apec.crm.injector.modules.ActivityModule;
import com.apec.crm.mvp.presenters.WorkPlacePresenter;
import com.apec.crm.mvp.views.WorkPlaceView;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.views.activities.VisitCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by duanlei on 16/9/12.
 */
public class WorkPlaceFragment extends BaseFragment implements WorkPlaceView {

    @BindView(R.id.gv_menu)
    GridView mGVMenu;

    @Inject
    WorkPlacePresenter mPresenter;

    @BindView(R.id.tv_month)
    TextView mTvMonth;
    @BindView(R.id.tv_num_custom)
    TextView mTvNumCustom;
    @BindView(R.id.tv_num_order)
    TextView mTvNumOrder;
    @BindView(R.id.tv_num_visit)
    TextView mTvNumVisit;

    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Override
    protected void initUI(View view) {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(R.drawable.bg_menu_item_1, "客户拜访"));
        menuEntities.add(new MenuEntity(R.drawable.bg_menu_item_2, "公海池"));
        menuEntities.add(new MenuEntity(R.drawable.bg_menu_item_3, "统计"));

        mGVMenu.setAdapter(new CommonAdapter<MenuEntity>(getActivity(), menuEntities,
                R.layout.item_work_menu) {
            @Override
            public void convert(MyViewHolder holder, MenuEntity menuEntity) {
                holder.setText(R.id.tv_name, menuEntity.getName())
                        .setImageResource(R.id.iv_bg, menuEntity.getResId());
            }
        });

        mGVMenu.setOnItemClickListener((adapterView, view1, i, l) -> {
            switch (i) {
                case 0: //客户拜访
                    Intent intent = new Intent(getActivity(), VisitCustomActivity.class);
                    startActivity(intent);
                    break;
                case 1: //公海池
                    break;
                case 2: //统计
                    break;
            }
        });
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_work_place;
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
        mPresenter.attachView(this);
        updateData();
    }

    public void updateData() {
        mPresenter.getMyMonthCount();
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
    public void onMyCountSuccess(MyCount data) {
        mTvMonth.setText(DateUtil.getDateFormatStr(data.getCurrentTime(),
                getString(R.string.date_format_work_place)));
        mTvNumCustom.setText(String.valueOf(data.getAddedCustom()));
        mTvNumOrder.setText(String.valueOf(data.getAddedOrder()));
        mTvNumVisit.setText(String.valueOf(data.getAddedVisit()));

    }

}
