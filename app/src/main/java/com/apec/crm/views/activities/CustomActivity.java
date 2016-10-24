package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.VisitRecordFragment;
import com.apec.crm.views.widget.RoundTextView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/19.
 */
public class CustomActivity extends BaseActivity {

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

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom, R.string.custom_home);

        setBtnImage(R.drawable.nav_more_drawable, v -> {
            if (mFlMenu.getVisibility() == View.VISIBLE) {
                mFlMenu.setVisibility(View.GONE);
            } else {
                mFlMenu.setVisibility(View.VISIBLE);
            }
        });

        VisitRecordFragment visitRecordFragment = new VisitRecordFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, visitRecordFragment, "visitList")
                .commit();
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCustom = getIntent().getParcelableExtra("custom");
        mRoundTextView.setColor(MyUtils.getColor(mCustom.getIcon()));

        MyUtils.setHeadText(mTvHead, mCustom.getCustomerName());

        mTvCustomName.setText(mCustom.getCustomerName());
        mTvAddress.setText(mCustom.getCustomerAddress());

        initMenu();
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

        });
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.rl_custom)
    void OnPlateCustomClicked(View view) {
        Intent intent = new Intent(this, CustomDetailActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_add_visit)
    void OnAddVisitClicked(View view) {
        Intent intent = new Intent(this, AddVisitActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fl_body)
    void OnBodyClicked(View view) {
        if (mFlMenu.getVisibility() == View.VISIBLE) {
            mFlMenu.setVisibility(View.GONE);
        }
    }
}
