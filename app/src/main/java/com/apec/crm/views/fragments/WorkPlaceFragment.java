package com.apec.crm.views.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.views.activities.VisitCustomActivity;
import com.apec.crm.views.fragments.core.BaseFragment;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by duanlei on 16/9/12.
 */
public class WorkPlaceFragment extends BaseFragment {

    @BindView(R.id.gv_menu)
    GridView mGVMenu;

    @Override
    protected void initUI(View view) {

        ArrayList<MenuEntity> menuEntities = new ArrayList<>();
        menuEntities.add(new MenuEntity(1, "客户拜访"));
        menuEntities.add(new MenuEntity(2, "公海池"));
        menuEntities.add(new MenuEntity(3, "统计"));

        mGVMenu.setAdapter(new CommonAdapter<MenuEntity>(getActivity(), menuEntities,
                R.layout.item_work_menu) {
            @Override
            public void convert(MyViewHolder holder, MenuEntity menuEntity) {
                holder.setText(R.id.tv_name, menuEntity.getName());
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

    }

    @Override
    protected void initPresenter() {

    }
}
