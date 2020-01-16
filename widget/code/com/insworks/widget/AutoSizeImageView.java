package com.insworks.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;

/**
 * Created by Mr_LanGe on 16/8/4.
 */
public class AutoSizeImageView extends ImageView {

    private float downX;
    private float downY;
    private long downTime;
    private OnRegionClickListener mListener;

    public AutoSizeImageView(Context context) {
        this(context, null);
    }

    public AutoSizeImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public void setOnRegionClickListener(OnRegionClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int modeW = MeasureSpec.getMode(widthMeasureSpec);
            int modeH = MeasureSpec.getMode(heightMeasureSpec);
            int sizeW = MeasureSpec.getSize(widthMeasureSpec);
            int sizeH = MeasureSpec.getSize(heightMeasureSpec);
            if (modeW != MeasureSpec.EXACTLY) {
                sizeW = getResources().getDisplayMetrics().widthPixels;
            }
            sizeH = sizeW * intrinsicHeight / intrinsicWidth;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeW, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(sizeH, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || isClickable() || isLongClickable()) {
            super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = SystemClock.uptimeMillis();
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float upX = event.getX();
                float upY = event.getY();
                long upTime = SystemClock.uptimeMillis();
                float deltaX = upX - downX;
                float deltaY = upY - downY;
                int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if ((deltaX * deltaX + deltaY * deltaY) < touchSlop * touchSlop) {
                    if (upTime - downTime < 500) {
                        onClick();
                        if (mListener != null) {
                            mListener.onRegionClick(this, (downX + upX) / 2, (downY + upY) / 2);
                        }
                    } else {
                        onLongClick();
                    }
                }
                break;
        }
        return true;
    }

    private void onClick() {

    }

    private void onLongClick() {

    }

    public interface OnRegionClickListener {

        void onRegionClick(View view, float x, float y);

    }

}
