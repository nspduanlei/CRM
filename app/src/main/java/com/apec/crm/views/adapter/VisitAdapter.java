package com.apec.crm.views.adapter;

import android.content.Context;
import com.apec.crm.R;
import com.apec.crm.domin.entities.VisitRecord;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by duanlei on 16/9/19.
 */
public class VisitAdapter {

    public static CommonAdapter<VisitRecord> getAdapter(ArrayList<VisitRecord> visitRecords,
                                                        Context context) {
        return new CommonAdapter<VisitRecord>(context, visitRecords,
                R.layout.item_visit_record) {
            @Override
            public void convert(MyViewHolder holder, VisitRecord visitRecord) {
//                holder.setText(R.id.tv_custom_name, visitRecord.getCustomName())
//                        .setText(R.id.tv_contact_name, visitRecord.getContactName())
//                        .setText(R.id.tv_visit_time, visitRecord.getTime())
//                        .setText(R.id.tv_address, visitRecord.getAddress())
//                        .setText(R.id.tv_visit_des, visitRecord.getVisitDescribe());
            }
        };
    }

}
