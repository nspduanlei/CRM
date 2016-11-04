package com.apec.crm.views.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.apec.crm.R;
import com.apec.crm.views.adapter.ImageSideAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by duanlei on 16/9/13.
 */
public class ImageDialogFragment extends DialogFragment implements ImageSideAdapter.OnImageClickListener {

    @BindView(R.id.vp_photo)
    ViewPager mVpPhoto;

    @BindView(R.id.tv_index)
    TextView mTvIndex;

    ImageSideAdapter mPagerAdapter;

    List<String> mPaths;

    private static final String ARG_PATHS = "arg_paths";
    private static final String ARG_INDEX = "arg_index";

    private int mIndex;

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.MenuDialog);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_image_dialog);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置全屏
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ee000000")));

        initUI(dialog);
        return dialog;
    }

    public static ImageDialogFragment newInstance(ArrayList<String> paths, int currIndex) {
        ImageDialogFragment newFragment = new ImageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_PATHS, paths);
        bundle.putInt(ARG_INDEX, currIndex);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    private void initUI(Dialog dialog) {
        ButterKnife.bind(this, dialog);

        mPaths = getArguments().getStringArrayList(ARG_PATHS);
        mIndex = getArguments().getInt(ARG_INDEX);
        mTvIndex.setText(String.format("%d/%d", mIndex + 1, mPaths.size()));

        mPagerAdapter = new ImageSideAdapter(getActivity(), mPaths);
        mPagerAdapter.setOnImageClickListener(this);
        mVpPhoto.setAdapter(mPagerAdapter);
        mVpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvIndex.setText(String.format("%d/%d", position + 1, mPaths.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mVpPhoto.setCurrentItem(mIndex);
    }

    @Override
    public void onImageClick() {
        dismiss();
    }
}
