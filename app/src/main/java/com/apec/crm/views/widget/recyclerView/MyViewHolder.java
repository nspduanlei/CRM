package com.apec.crm.views.widget.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apec.crm.views.widget.RoundTextView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 16/9/21.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;


    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);

        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public MyViewHolder(View view, Context context) {
        super(view);
        mConvertView = view;
        mContext = context;
        mViews = new SparseArray<>();
        ButterKnife.bind(this, view);
        view.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v);
            }
        });
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public MyViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MyViewHolder setTextRound(int viewId, int color) {
        RoundTextView tv = getView(viewId);
        tv.setColor(color);
        return this;
    }

    public MyViewHolder setImageUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        Picasso.with(mContext).load(url).into(iv);
        return this;
    }

    public MyViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public MyViewHolder setOnClickLister(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }




}
