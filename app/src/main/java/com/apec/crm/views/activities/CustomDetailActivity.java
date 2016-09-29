package com.apec.crm.views.activities;

import android.os.Bundle;

import com.apec.crm.R;
import com.apec.crm.app.MyApplication;
import com.apec.crm.domin.entities.Contact;
import com.apec.crm.views.activities.core.BaseActivity;
import com.apec.crm.views.widget.NoScrollListView;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

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

        ArrayList<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            contacts.add(new Contact("老板娘", "李小姐", "15669698989"));
        }

        mLVContactList.setAdapter(new CommonAdapter<Contact>(this, contacts, R.layout.item_contact) {
            @Override
            public void convert(MyViewHolder holder, Contact contact) {
                holder.setText(R.id.tv_id, contact.getIdentity())
                        .setText(R.id.tv_nick, contact.getNickName());
            }
        });

    }

    @Override
    protected void initDependencyInjector(MyApplication application) {

    }

    @Override
    protected void initPresenter() {

    }
}
