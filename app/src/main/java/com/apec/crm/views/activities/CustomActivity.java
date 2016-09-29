package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.views.activities.core.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/19.
 */
public class CustomActivity extends BaseActivity implements BaseActivity.AddClickListener {

    @BindView(R.id.lv_visit)
    ListView mLVVisit;

    @BindView(R.id.lv_dialog_menu)
    ListView mLVDialogMenu;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom, R.string.custom_home);
        setBtnImage(R.drawable.nav_more_drawable, this);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

//        ArrayList<VisitRecord> visitRecords = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            visitRecords.add(new VisitRecord("宏发食品厂", "2016-9-19",
//                    "fasfaffafaaffsfaffasfsfasfasfafasfa", "李某某", null, "深圳市福田区发发发嘎嘎嘎嘎嘎嘎"));
//        }
//
//        mLVVisit.setAdapter(VisitAdapter.getAdapter(visitRecords, this));
//
//
//        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
//        menuEntities.add(new MenuEntity(1, "添加拜访"));
//        menuEntities.add(new MenuEntity(2, "退回公海"));
//        menuEntities.add(new MenuEntity(3, "废弃"));
//        mLVDialogMenu.setAdapter(new CommonAdapter<MenuEntity>(this, menuEntities,
//                R.layout.item_dialog_menu) {
//            @Override
//            public void convert(MyViewHolder holder, MenuEntity menuEntity) {
//                holder.setText(R.id.tv_menu_name, menuEntity.getName());
//            }
//        });
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onAddClicked() {
        if (mLVDialogMenu.getVisibility() == View.VISIBLE) {
            mLVDialogMenu.setVisibility(View.GONE);
        } else {
            mLVDialogMenu.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.rl_custom)
    void OnPlateCustomClicked(View view) {
        Intent intent = new Intent(this, CustomDetailActivity.class);
        startActivity(intent);
    }
}
