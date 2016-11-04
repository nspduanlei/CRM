package com.apec.crm.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.apec.crm.R;
import com.apec.crm.domin.entities.MenuEntity;
import com.apec.crm.views.activities.AddCustomActivity;
import com.apec.crm.views.activities.AddVisitActivity;
import com.apec.crm.views.activities.ContactActivity;
import com.apec.crm.views.widget.listView.CommonAdapter;
import com.apec.crm.views.widget.listView.MyViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by duanlei on 16/9/13.
 */
public class FlashDialogFragment extends DialogFragment {

    @BindView(R.id.gv_menu)
    GridView mGvMenu;

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.MenuDialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_flash_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置全屏
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeffffff")));

        initUI(dialog);

        return dialog;
    }

    private void initUI(Dialog dialog) {
        ButterKnife.bind(this, dialog);

        ArrayList<MenuEntity> data = new ArrayList<>();
        data.add(new MenuEntity(R.drawable.ic_add_visit, "添加拜访"));
        data.add(new MenuEntity(R.drawable.ic_add_custom, "添加客户"));
        data.add(new MenuEntity(R.drawable.ic_add_contact, "添加联系人"));

        mGvMenu.setAdapter(new CommonAdapter<MenuEntity>(getActivity(), data, R.layout.item_quick_menu) {
            @Override
            public void convert(MyViewHolder holder, MenuEntity menuEntity) {
                holder.setText(R.id.tv_menu_name, menuEntity.getName())
                        .setImageResource(R.id.iv_menu_icon, menuEntity.getResId());
            }
        });

        mGvMenu.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = null;

            switch (i) {
                case 0: //添加拜访
                    intent = new Intent(getActivity(), AddVisitActivity.class);
                    break;

                case 1: //添加客户
                    intent = new Intent(getActivity(), AddCustomActivity.class);
                    break;

                case 2: //添加联系人
                    intent = new Intent(getActivity(), ContactActivity.class);
                    intent.putExtra(ContactActivity.ARG_TYPE, ContactActivity.TYPE_ADD_SAVE);
                    break;
            }
            startActivity(intent);
        });
    }

    @OnClick({R.id.fl_container, R.id.iv_quick_close})
    void onCloseClicked(View view) {
        dismiss();
    }
}
