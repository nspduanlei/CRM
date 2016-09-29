package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.apec.collapsecalendar.CollapseCalendarView;
import com.apec.collapsecalendar.manager.CalendarManager;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.utils.T;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.VisitRecordFragment;

import org.joda.time.LocalDate;

import butterknife.BindView;

/**
 * Created by duanlei on 16/9/19.
 */
public class VisitCustomActivity extends BaseActivity implements
        BaseActivity.AddClickListener {

    @BindView(R.id.calendar)
    CollapseCalendarView mCalendarView;

//    @BindView(R.id.lv_visit)
//    ListView mLVVisit;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_visit_custom, R.string.visit_custom_title);
        setBtnImage(R.drawable.nav_add_drawable, this);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        CalendarManager manager = new CalendarManager(LocalDate.now(),
                CalendarManager.State.WEEK, LocalDate.now().minusYears(1), LocalDate.now());

        mCalendarView.init(manager);
        mCalendarView.setListener(date ->
                T.showShort(VisitCustomActivity.this, date.getYear() + "-" + date.getMonthOfYear()
                    + "-" + date.getDayOfMonth()));

//        ArrayList<VisitRecord> visitRecords = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            visitRecords.add(new VisitRecord("宏发食品厂", "2016-9-19",
//                    "fasfaffafaaffsfaffasfsfasfasfafasfa", "李某某", null, "深圳市福田区发发发嘎嘎嘎嘎嘎嘎"));
//        }
//
//        mLVVisit.setAdapter(VisitAdapter.getAdapter(visitRecords, this));


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

    @Override
    public void onAddClicked() {
        Intent intent = new Intent(this, AddVisitActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_VISIT);
    }
}
