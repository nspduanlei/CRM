package com.apec.crm.views.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.VisitRecordFilter;
import com.apec.crm.domin.entities.VisitsFilter;
import com.apec.crm.utils.DateUtil;
import com.apec.crm.utils.ScreenUtils;
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
public class VisitCustomActivity extends BaseActivity implements VisitRecordFragment.CountListener {

    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;

    @BindView(R.id.iv_arrow_date)
    ImageView mIvArrowDate;

    @BindView(R.id.tv_record_count)
    TextView mTvRecordCount;

    VisitRecordFragment mVisitRecordFragment;

    /**
     * 用户和客户筛选条件
     */
    private VisitsFilter mVisitsFilter;
    VisitRecordFilter mVisitRecordFilter = new VisitRecordFilter();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_visit_custom, R.string.visit_custom_title);

        //搜索
        setBtnImage(R.drawable.nav_search_drawable, v -> {
            Intent intent = new Intent(this, FilterVisitActivity.class);
            if (mVisitsFilter != null) {
                intent.putExtra(FilterVisitActivity.ARG_FILTER, mVisitsFilter);
            }
            startActivityForResult(intent, Constants.REQUEST_CODE_FILTER_VISIT);
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

        setCurrentDate();

        mCalendarView.setOnDateChangedListener((widget, date, selected) -> {
            //根据date获取 前一天和后一天的日期字符串
            String datePre = DateUtil.getPreDate(date.getDate());
            String dateNext = DateUtil.getNextDate(date.getDate());

            mVisitRecordFilter.setStartDate(datePre);
            mVisitRecordFilter.setEndDate(dateNext);

            mVisitRecordFragment.updateForFilter(mVisitRecordFilter);
        });

        initFragment();
    }

    private void setCurrentDate() {
        Date curDate = new Date();
        mCalendarView.setSelectedDate(curDate);
        mVisitRecordFilter.setStartDate(DateUtil.getPreDate(curDate));
        mVisitRecordFilter.setEndDate(DateUtil.getNextDate(curDate));
    }

    private void initFragment() {
        mVisitRecordFragment = VisitRecordFragment.newInstance(mVisitRecordFilter);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mVisitRecordFragment, "visitList")
                .commit();
        mVisitRecordFragment.setCountListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ADD_VISIT) {
            if (resultCode == Constants.RESULT_CODE_ADD_VISIT) {
                mVisitRecordFragment.updateForFilter(mVisitRecordFilter);
            }
        } else if (requestCode == Constants.REQUEST_CODE_FILTER_VISIT) {
            if (resultCode == Constants.RESULT_CODE_FILTER_VISIT) {
                VisitsFilter visitsFilter =
                        data.getParcelableExtra(FilterVisitActivity.RET_FILTER);
                mVisitsFilter = visitsFilter;

                mVisitRecordFilter.setCustomerNo(visitsFilter.getCustomNo());
                mVisitRecordFilter.setUserNo(visitsFilter.getUserNo());

                mVisitRecordFragment.updateForFilter(mVisitRecordFilter);
            }
       }
    }

    @Override
    public void handleCount(int count) {
        mTvRecordCount.setText(String.format("共%s条拜访记录", count));
    }
}
