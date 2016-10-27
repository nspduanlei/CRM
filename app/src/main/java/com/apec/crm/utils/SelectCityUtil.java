package com.apec.crm.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.apec.crm.R;
import com.apec.crm.domin.entities.Address;
import com.apec.crm.domin.entities.Area;
import com.apec.crm.domin.entities.func.ListResult;
import com.apec.crm.domin.useCase.sys.GetAreaUseCase;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by duanlei on 2016/3/16.
 * 省、市、区、 街道 多级选择
 */
public class SelectCityUtil implements OnClickListener {

    private DialogPlus dialog;
    private SelectArea mSelectArea;

    private static final int PROVINCE = 0;
    private static final int CITY = 1;
    private static final int AREA = 2;


    //true 初始化显示一个区域， false 初始化没有默认选择的区域
    private boolean isShowArea;

    private GetAreaUseCase mGetAreaUseCase;
    private Context mContext;
    private ProgressBar mProgressBar;


    private ArrayList<Area> mData;
    private BaseAdapter mAdapter;
    private Area curArea;
    private RadioButton rbProvince, rbCity, rbArea, rbPlease;

    private ArrayList<Area> provinceList, cityList, areaList;

    private int curIndex = PROVINCE;

    private int selProvince, selCity, selArea;


    @Inject
    public SelectCityUtil(Context context, GetAreaUseCase getAreaUseCase) {
        mContext = context;
        mGetAreaUseCase = getAreaUseCase;
    }

    /**
     * 编辑地址
     */
    public void init(SelectArea selectArea, Address address) {
        init(selectArea);

        this.selProvince = Integer.valueOf(address.getProviceId());
        this.selCity = Integer.valueOf(address.getCityId());
        this.selArea = Integer.valueOf(address.getAreaId());


        rbProvince.setVisibility(View.VISIBLE);
        rbProvince.setText(address.getProvinceName());

        rbCity.setVisibility(View.VISIBLE);
        rbCity.setText(address.getCityName());

        rbArea.setVisibility(View.VISIBLE);
        rbArea.setText(address.getAreaName());

        obtainAreaForShow(PROVINCE);

        isShowArea = true;
        rbPlease.setVisibility(View.GONE);
    }

    public void init(SelectArea selectArea) {
        mSelectArea = selectArea;
        View dialogView = ((Activity) mContext).getLayoutInflater()
                .inflate(R.layout.fragment_select_city, null);

        mProgressBar = (ProgressBar) dialogView.findViewById(R.id.pb_loading);

        initView(dialogView);
        ViewHolder viewHolder = new ViewHolder(dialogView);

        dialog = DialogPlus.newDialog(mContext)
                .setContentHolder(viewHolder)
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setOnClickListener(this)
                .create();

        if (!isShowArea) {
            obtainArea(PROVINCE);
        }
    }

    private void initView(View view) {
        rbProvince = (RadioButton) view.findViewById(R.id.rb_province);
        rbCity = (RadioButton) view.findViewById(R.id.rb_city);
        rbArea = (RadioButton) view.findViewById(R.id.rb_area);
        rbPlease = (RadioButton) view.findViewById(R.id.rb_please_select);

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<Area>(mContext, mData, R.layout.select_city_item) {
            @Override
            public void convert(MyViewHolder holder, Area area) {
                holder.setText(R.id.tv_area_name, area.getAreaName());
                switch (curIndex) {
                    case 1:
                        if (area.getId() == selCity) {
                            holder.setTextColor(R.id.tv_area_name, "#3CDD93");
                        } else {
                            holder.setTextColor(R.id.tv_area_name, "#8e8e93");
                        }
                        break;
                    case 2:
                        if (area.getId() == selArea) {
                            holder.setTextColor(R.id.tv_area_name, "#3CDD93");
                        } else {
                            holder.setTextColor(R.id.tv_area_name, "#8e8e93");
                        }
                        break;
                }
            }
        };

        ListView areas = (ListView) view.findViewById(R.id.lv_areas);
        areas.setAdapter(mAdapter);

        areas.setOnItemClickListener((parent, view1, position, id) -> {
            curArea = mData.get(position);
            switch (curIndex) {
                case PROVINCE: //省选择
                    if (rbProvince.getVisibility() == View.GONE) {
                        rbProvince.setVisibility(View.VISIBLE);
                    }
                    rbProvince.setText(curArea.getAreaName());
                    selProvince = curArea.getId();

                    //省选择完，切换城市
                    curIndex = CITY;

//                    if (rbCity.getVisibility() == View.GONE) {
//                        rbPlease.setChecked(true);
//                    } else {
//                        rbCity.setChecked(true);
//                    }

                    //清除掉与上一次选择关联的城市和区
                    selCity = 0;
                    selArea = 0;
                    rbCity.setVisibility(View.GONE);
                    rbArea.setVisibility(View.GONE);
                    rbPlease.setVisibility(View.VISIBLE);
                    rbPlease.setChecked(true);

                    //获取下一级数据
                    obtainArea(curArea.getId());
                    break;

                case CITY: //城市选择
                    if (rbCity.getVisibility() == View.GONE) {
                        rbCity.setVisibility(View.VISIBLE);
                    }
                    rbCity.setText(curArea.getAreaName());
                    selCity = curArea.getId();

                    //城市选择完，切换到选择地区
                    curIndex = AREA;

//                    if (rbArea.getVisibility() == View.GONE) {
//                        rbPlease.setChecked(true);
//                    } else {
//                        rbArea.setChecked(true);
//                    }

                    //清除掉与上一次选择关联的区或街道
                    selArea = 0;
                    rbArea.setVisibility(View.GONE);
                    rbPlease.setVisibility(View.VISIBLE);
                    rbPlease.setChecked(true);

                    //获取下一级数据
                    obtainArea(curArea.getId());
                    break;
                case AREA: //地区选择
                    if (rbArea.getVisibility() == View.GONE) {
                        rbArea.setVisibility(View.VISIBLE);
                    }
                    rbArea.setText(curArea.getAreaName());
                    selArea = curArea.getId();

                    rbArea.setChecked(true);
                    rbPlease.setVisibility(View.GONE);

                    //街道选择完成关闭弹窗
                    dismiss();
                    break;
            }
        });
    }

    /**
     * 获取地区数据
     *
     * @param id
     */
    private void obtainArea(int id) {
        mData.clear();
        mAdapter.notifyDataSetChanged();

        mProgressBar.setVisibility(View.VISIBLE);
        mGetAreaUseCase.setData(id);
        mGetAreaUseCase.execute().subscribe(this::onAreaReceived, this::managerError);
    }

    private void managerError(Throwable throwable) {
        mProgressBar.setVisibility(View.GONE);
    }

    private void onAreaReceived(ListResult<Area> response) {
        mProgressBar.setVisibility(View.GONE);
        if (response.isSucceed()) {
            //获取地区成功
            ArrayList<Area> areas = response.getData();

            if (areas.size() > 0) { //如果有地区数据
                switch (curIndex) {
                    case PROVINCE:
                        provinceList = areas;
                        break;
                    case CITY:
                        cityList = areas;
                        break;
                    case AREA:
                        areaList = areas;
                        break;
                }
                mData.clear();
                mData.addAll(areas);
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    private void obtainAreaForShow(int id) {
        mProgressBar.setVisibility(View.VISIBLE);
        mGetAreaUseCase.setData(id);
        mGetAreaUseCase.execute().subscribe(this::onAreaShowReceived, this::managerError);
    }

    private void onAreaShowReceived(ListResult<Area> response) {
        mProgressBar.setVisibility(View.GONE);
        if (response.isSucceed()) {
            //获取地区成功
            ArrayList<Area> areas = response.getData();

            if (areas.size() > 0) { //如果有地区数据
                switch (curIndex) {
                    case PROVINCE:
                        provinceList = areas;
                        curIndex = 1;
                        obtainAreaForShow(selProvince);
                        break;
                    case CITY:
                        cityList = areas;
                        curIndex = 2;
                        obtainAreaForShow(selCity);
                        break;
                    case AREA:
                        areaList = areas;
                        rbArea.performClick();
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(DialogPlus dialog, View view) {
        switch (view.getId()) {

            case R.id.rb_province://重新选择城市
                curIndex = PROVINCE;
                rbProvince.setChecked(true);

                mData.clear();
                mData.addAll(provinceList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_city: //重新选择城市
                curIndex = CITY;
                rbCity.setChecked(true);

                mData.clear();
                mData.addAll(cityList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_area: //重新选择地区
                curIndex = AREA;
                rbArea.setChecked(true);

                mData.clear();
                mData.addAll(areaList);
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.rb_please_select: //切换到当前的选择
                if (areaList == null) {
                    return;
                }

                rbPlease.setChecked(true);

                curIndex = 2;
                mData.clear();
                mData.addAll(areaList);
                mAdapter.notifyDataSetChanged();

                break;

            case R.id.btn_cancel:
                dialog.dismiss();
                break;
        }
    }

    private void dismiss() {
        dialog.dismiss();

        Address address = new Address();
        address.setProvinceName(rbProvince.getText().toString());
        address.setCityName(rbCity.getText().toString());
        address.setAreaName(rbArea.getText().toString());
        address.setProviceId(String.valueOf(selProvince));
        address.setCityId(String.valueOf(selCity));
        address.setAreaId(String.valueOf(selArea));

        mSelectArea.selectCityFinish(address);
    }

    public void showDialog() {
        dialog.show();
    }

    public interface SelectArea {
        void selectCityFinish(Address address);
    }
}
