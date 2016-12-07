package com.apec.crm.views.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.TabEntity;
import com.apec.crm.support.eventBus.RxBus;
import com.apec.crm.utils.MyUtils;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.fragments.CustomFragment;
import com.apec.crm.views.fragments.ProfileFragment;
import com.apec.crm.views.fragments.WorkPlaceFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import java.util.ArrayList;
import butterknife.BindView;
import rx.Subscription;


public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_main)
    CommonTabLayout mTlMain;

    private String[] mTitles = {"客户", "工作台", "我"};
    private int[] mIconUnSelectIds = {
            R.mipmap.tabbar_custom, R.mipmap.tabbar_work_place,
            R.mipmap.tabbar_profile};
    private int[] mIconSelectIds = {
            R.mipmap.tabbar_custom_selected, R.mipmap.tabbar_work_place_selected,
            R.mipmap.tabbar_profile_selected};

    CustomFragment mCustomFragment;
    WorkPlaceFragment mWorkPlaceFragment;
    ProfileFragment mProfileFragment;

    Subscription mRxSubscription;
    public static final int ACTION_UPDATE_CUSTOMS = 1;
    public static final int ACTION_UPDATE_USER = 2;
    public static final int ACTION_MY_COUNT = 3;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main, -1, MODE_NONE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        //int test = 1/0;

        initTabBar();
        initSubscription();
    }

    private void initSubscription() {
        mRxSubscription = RxBus.getDefault().toObservable(Integer.class)
                .subscribe(this::updateData, this::manageError);
    }

    private void manageError(Throwable throwable) {

    }

    private void updateData(Integer integer) {
        switch (integer) {
            case ACTION_UPDATE_CUSTOMS:
                mCustomFragment.updateCustomList();
                break;
            case ACTION_UPDATE_USER:
                mProfileFragment.updateUser();
                break;
            case ACTION_MY_COUNT:
                mWorkPlaceFragment.updateData();
                break;
        }
    }

    private void initTabBar() {
        mCustomFragment = CustomFragment.newInstance(CustomFragment.TYPE_PRIVATE);
        mWorkPlaceFragment = new WorkPlaceFragment();
        mProfileFragment = new ProfileFragment();

        mFragments.add(mCustomFragment);
        mFragments.add(mWorkPlaceFragment);
        mFragments.add(mProfileFragment);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
        }

        mTlMain.setTabData(mTabEntities, this, R.id.fl_content, mFragments);

//        mTlMain.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//
//                switch (position) {
//                    case 0:
//                        setUpTitle(R.string.custom_title);
//                        setBtnImage(R.drawable.nav_add_drawable, MainActivity.this);
//                        break;
//                    case 1:
//                        setUpTitle(R.string.work_place_title);
//                        hideBtnImage();
//                        break;
//                    case 2:
//                        setUpTitle(R.string.profile_title);
//                        hideBtnImage();
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabReselect(int position) {
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
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUtils.cancelSubscribe(mRxSubscription);
    }
}
