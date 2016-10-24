package com.apec.crm.views.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.utils.ScreenUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.VisitRecordFragment;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/19.
 */
public class VisitCustomActivity extends BaseActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @BindView(R.id.lv_dialog_menu)
    ListView mLVDialogMenu;

    @BindView(R.id.fl_body)
    FrameLayout mFlMenu;

    @BindView(R.id.iv_arrow_date)
    ImageView mIvArrowDate;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_visit_custom, R.string.visit_custom_title);

        //搜索
        setBtnImage(R.drawable.nav_search_drawable, v -> {
            if (mFlMenu.getVisibility() == View.VISIBLE) {
                mFlMenu.setVisibility(View.GONE);
            } else {
                mFlMenu.setVisibility(View.VISIBLE);
            }
        });

        //添加拜访
        setMenuText("添加拜访", v -> {
            Intent intent = new Intent(this, AddVisitActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE_ADD_VISIT);

        });
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

        mCalendarView.setTileWidth(ScreenUtils.getScreenWidth(this)/7);
        mCalendarView.state().edit().setMaximumDate(new Date()).commit();
        mCalendarView.setSelectedDate(new Date());

        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            T.showShort(this, date.toString());
        });

        VisitRecordFragment visitRecordFragment = new VisitRecordFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, visitRecordFragment, "visitRecord")
                .commit();

        initMenu();
    }

    private void initMenu() {
        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(1, "选择客户"));
        menuEntities.add(new MenuEntity(2, "选择人员"));
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

    @OnClick(R.id.fl_switch_date)
    void onSwitchClicked(View v) {
        if (mCalendarView.state().calendarMode == CalendarMode.WEEKS) {
            mCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();

            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvArrowDate, "rotation", 0f, 180f);
            animator.setDuration(1000);
            animator.start();

        } else {
            mCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();

            ObjectAnimator animator = ObjectAnimator.ofFloat(mIvArrowDate, "rotation", 180f, 360f);
            animator.setDuration(500);
            animator.start();
        }
    }

    @OnClick(R.id.fl_body)
    void OnBodyClicked(View view) {
        if (mFlMenu.getVisibility() == View.VISIBLE) {
            mFlMenu.setVisibility(View.GONE);
        }
    }
}
