package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.utils.ScreenUtils;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.VisitRecordFragment;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/19.
 */
public class VisitCustomActivity extends BaseActivity {

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_visit_custom, R.string.visit_custom_title);

        setBtnImage(R.drawable.nav_add_drawable, v -> {
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
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }


    @OnClick(R.id.btn_switch)
    void onSwitchClicked(View v) {
        if (mCalendarView.state().calendarMode == CalendarMode.WEEKS) {
            mCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
        } else {
            mCalendarView.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
        }
    }
}
