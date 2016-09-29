package com.apec.crm.views.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.utils.L;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/23.
 */

public class MapSearchActivity extends BaseActivity implements Inputtips.InputtipsListener {

    String mCityCode;

    @BindView(R.id.et_content)
    EditText mEtContent;

    InputtipsQuery mInputQuery;
    Inputtips mInputtips;

    @BindView(R.id.lv_search)
    ListView mLvSearch;

    @BindView(R.id.iv_clear_text)
    ImageView mIvClearText;

    CommonAdapter<Tip> mTipCommonAdapter;
    ArrayList<Tip> mTips = new ArrayList<>();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_search, -1, MODE_NONE);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {
        mCityCode = getIntent().getStringExtra("cityCode");
        L.e("test001", "cityCode---->" + mCityCode);

        mInputQuery = new InputtipsQuery("", mCityCode);
        mInputtips = new Inputtips(this, mInputQuery);
        mInputtips.setInputtipsListener(this);

        mEtContent.addTextChangedListener(new EditChangeListener());

        mTipCommonAdapter = new CommonAdapter<Tip>(this, mTips, R.layout.item_map_search) {
            @Override
            public void convert(MyViewHolder holder, Tip tip) {
                holder.setText(R.id.tv_title, tip.getName())
                        .setText(R.id.tv_address, tip.getAddress());
            }
        };

        mLvSearch.setAdapter(mTipCommonAdapter);

        mLvSearch.setOnItemClickListener((adapterView, view, i, l) -> {
            Tip tip = mTips.get(i);
            if (tip.getPoint() == null) {
                mEtContent.setText(tip.getName());
            } else {
                setResult(Constants.RESULT_CODE_MAP_SELECT_SUC,
                        getIntent().putExtra("address", tip));
                MapSearchActivity.this.finish();
            }
        });
    }

    class EditChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                mIvClearText.setVisibility(View.GONE);

            } else {
                mIvClearText.setVisibility(View.VISIBLE);

                mInputQuery = new InputtipsQuery(s.toString(), mCityCode);
                mInputtips.setQuery(mInputQuery);
                mInputtips.requestInputtipsAsyn();
            }
        }
    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        mTips.clear();
        mTips.addAll(list);
        mTipCommonAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.iv_clear_text)
    void onClearTextClicked(View view) {
        mEtContent.setText("");
    }

    @OnClick(R.id.tv_exit_search)
    void onExitSearchClicked(View view) {
        this.finish();
    }
}
