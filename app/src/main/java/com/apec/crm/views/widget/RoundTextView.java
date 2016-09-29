package com.apec.crm.views.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.apec.crm.R;
import com.apec.crm.utils.DensityUtils;

/**
 * Created by duanlei on 16/9/13.
 */
public class RoundTextView extends View {

    private int mColor = Color.RED;
    private String mText;
    private Paint mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintText = new Paint();

    private float mTextWidth;

    private String[] mRandoColor = {"#D32F2F", "#C2185B", "#7B1FA2", "#FF1744", "#F50057", "#D500F9"};

    public RoundTextView(Context context) {
        super(context);
        init();
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView);
        //mColor = a.getColor(R.styleable.RoundTextView_circle_color, Color.RED);
        mText = a.getString(R.styleable.RoundTextView_circle_text);

        if (mText == null) {
            mText = "";
        }

        a.recycle();

        init();
    }

    private void init() {
        //随机设置mColor颜色

        mColor = Color.parseColor(mRandoColor[(int) (Math.random() * 10) % mRandoColor.length]);

        mPaintCircle.setColor(mColor);
        mPaintText.setColor(Color.parseColor("#ffffff"));

        mTextWidth = DensityUtils.sp2px(getContext(), 15);

        mPaintText.setTextSize(mTextWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, radius, mPaintCircle);

        canvas.save();

        canvas.drawText(mText, width / 2 - mTextWidth, height / 2 + mTextWidth / 3, mPaintText);
    }

    public void setText(String text) {
        mText = text;
        invalidate();
    }
}
