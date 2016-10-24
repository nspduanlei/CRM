package com.apec.crm.views.widget.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.apec.crm.views.widget.RoundTextView;

import butterknife.ButterKnife;

/**
 * Created by duanlei on 16/9/21.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;


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

    public MyViewHolder(View view) {
        super(view);
        mConvertView = view;
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



}
