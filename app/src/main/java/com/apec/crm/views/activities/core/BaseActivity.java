package com.apec.crm.views.activities.core;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 2016/5/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    protected TextView mToolbarTitle;
    protected Toolbar mToolbar;
    protected ImageView mToolbarBtn;
    protected TextView mMenuText;

    //有返回按钮
    public static final int MODE_BACK = 0;
    //不显示toolber
    public static final int MODE_NONE = 1;
    //不显示返回按钮
    public static final int MODE_HOME = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication myApplication = (MyApplication) getApplication();
        initDependencyInjector(myApplication);
        setUpContentView();
        initUi(savedInstanceState);
        initPresenter();
    }

    //设置布局，在里面调用setContentView方法
    protected abstract void setUpContentView();

    //初始化视图
    protected abstract void initUi(Bundle savedInstanceState);

    //初始化依赖，方便后面的对象注入
    protected abstract void initDependencyInjector(MyApplication application);

    //初始化Presenter
    protected abstract void initPresenter();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(layoutResID, -1, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId) {
        setContentView(layoutResID, titleResId, -1, MODE_BACK);
    }

    public void setContentView(int layoutResID, int titleResId, int mode) {
        setContentView(layoutResID, titleResId, -1, mode);
    }

    public void setContentView(int layoutResID, int titleResId, int menuId, int mode) {
        if (layoutResID == -1) {
            return;
        }
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setUpToolbar(titleResId, menuId, mode);
    }

    protected void setUpToolbar(int titleResId, int menuId, int mode) {
        if (mode != MODE_NONE) {

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
            mToolbarBtn = (ImageView) findViewById(R.id.toolbar_image);
            mMenuText = (TextView) findViewById(R.id.tv_menu);

            if (mode == MODE_BACK) {
                mToolbar.setNavigationIcon(R.drawable.arrow_back);
            }

            mToolbar.setNavigationOnClickListener(view -> onNavigationBtnClicked());

            setUpTitle(titleResId);
            setUpMenu(menuId);
        }
    }

    protected void setUpTitle(int titleResId) {
        if (titleResId > 0 && mToolbarTitle != null) {
            mToolbarTitle.setText(titleResId);
        }
    }

    /**
     * 设置右侧图片
     * @param resId
     * @param onClickListener
     */
    protected void setBtnImage(int resId, View.OnClickListener onClickListener) {
        mToolbarBtn.setVisibility(View.VISIBLE);
        mToolbarBtn.setImageResource(resId);
        mToolbarBtn.setOnClickListener(onClickListener);
    }

    protected void hideBtnImage() {
        mToolbarBtn.setVisibility(View.GONE);
    }

    protected void setUpMenu(int menuId) {
        if (mToolbar != null) {
            mToolbar.getMenu().clear();
            if (menuId > 0) {
                mToolbar.inflateMenu(menuId);
                mToolbar.setOnMenuItemClickListener(this);
            }
        }
    }

    protected void onNavigationBtnClicked() {
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    protected void setMenuText(String title, View.OnClickListener onClickListener) {
        mMenuText.setText(title);
        mMenuText.setOnClickListener(onClickListener);
    }
}
