package com.apec.crm.views.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apec.crm.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by duanlei on 2016/10/31.
 */

public class ImageSideAdapter extends PagerAdapter {

    private List<String> mPaths;
    private Context mContext;
    private OnImageClickListener mOnImageClickListener;

    public ImageSideAdapter(Context context, List<String> paths) {
        mContext = context.getApplicationContext();
        mPaths = paths;
    }

    public interface OnImageClickListener {
        void onImageClick();
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    @Override
    public int getCount() {
        return mPaths.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = new ImageView(mContext);

        iv.setOnClickListener(v -> {
            mOnImageClickListener.onImageClick();
        });

        Picasso.with(mContext)
                .load(mPaths.get(position))
                .config(Bitmap.Config.RGB_565)
                .resize(ScreenUtils.getScreenWidth(mContext), ScreenUtils.getScreenWidth(mContext))
                .centerInside()
                .into(iv);

        container.addView(iv, 0);

        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
