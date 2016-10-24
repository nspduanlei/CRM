package com.apec.crm.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.config.Constants;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.NoScrollListView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duanlei on 16/9/20.
 */

public class CustomDetailActivity extends BaseActivity {

    @BindView(R.id.lv_contact_list)
    NoScrollListView mLVContactList;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_custom_detail, R.string.custom_detail_title);
    }

    @Override
    protected void initUi(Bundle savedInstanceState) {

//        ArrayList<Contact> contacts = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            contacts.add(new Contact("老板娘", "李小姐", "15669698989"));
//        }

        mLVContactList.setAdapter(new CommonAdapter<Contact>(this, new ArrayList<>(),
                R.layout.item_contact) {
            @Override
            public void convert(MyViewHolder holder, Contact contact) {
                holder.setText(R.id.tv_id, contact.getContactPost())
                        .setText(R.id.tv_nick, contact.getContactName());
            }
        });

        mLVContactList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(CustomDetailActivity.this, ContactActivity.class);
            intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_EDIT);
            startActivity(intent);
        });

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }

    /**
     * 编辑更多资料
     *
     * @param view
     */
    @OnClick(R.id.fl_more_data)
    void onMoreDataClicked(View view) {
        Intent intent = new Intent(this, CustomMoreDataActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
    }

    /**
     * 编辑基本资料
     *
     * @param view
     */
    @OnClick(R.id.fl_base_data)
    void onBaseDataClicked(View view) {
        Intent intent = new Intent(this, CustomEditActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_MORE_DATA);
    }

    /**
     * 添加联系人
     *
     * @param view
     */
    @OnClick(R.id.tv_add_contact)
    void onAddContactClicked(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_ADD_CONTACT);
    }
}
