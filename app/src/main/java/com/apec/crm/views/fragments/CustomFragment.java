package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.Custom;
import com.apec.crm.views.activities.FilterCustomActivity;
import com.apec.crm.views.activities.SearchCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/12.
 */
public class CustomFragment extends BaseFragment {

    @BindView(R.id.lv_custom_list)
    ListView mCustomList;


    @Override
    protected void initUI(View view) {

        ArrayList<Custom> data = new ArrayList<>();
        data.add(new Custom(1, "蛋糕店", "2016-6-20", "深圳市富强路发发发发嘎嘎嘎嘎322663号"));
        data.add(new Custom(1, "蛋糕店", "2016-6-20", "深圳市富强路发发发发嘎嘎嘎嘎322663号"));
        data.add(new Custom(1, "蛋糕店", "2016-6-20", "深圳市富强路发发发发嘎嘎嘎嘎322663号"));
        data.add(new Custom(1, "蛋糕店", "2016-6-20", "深圳市富强路发发发发嘎嘎嘎嘎322663号"));
        data.add(new Custom(1, "蛋糕店", "2016-6-20", "深圳市富强路发发发发嘎嘎嘎嘎322663号"));

        mCustomList.setAdapter(new CommonAdapter<Custom>(getActivity(), data, R.layout.item_custom_list) {
            @Override
            public void convert(MyViewHolder holder, Custom custom) {
                holder.setTextRound(R.id.tv_head, custom.getName().substring(0, 2))
                        .setText(R.id.tv_custom_name, custom.getName())
                        .setText(R.id.tv_address, custom.getAddress())
                        .setText(R.id.tv_time, custom.getTime());
            }
        });
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void initDependencyInjector(MyApplication myApplication) {

    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.tv_search)
    void onSearchClicked(View view) {
        Intent intent = new Intent(getActivity(), SearchCustomActivity.class);
        startActivity(intent);

    }

    @OnClick(R.id.tv_filter)
    void onFilterClicked(View view) {
        Intent intent = new Intent(getActivity(), FilterCustomActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_flash)
    void onFlashClicked(View view) {
        FlashDialogFragment flashDialogFragment = new FlashDialogFragment();
        flashDialogFragment.show(getActivity().getFragmentManager(), "FlashDialogFragment");
    }
}
