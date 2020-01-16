package com.insworks.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.inswork.lib_cloudbase.R;


/**
 * 可自定义设置drawable宽高的TextView
 */
public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {
    private Drawable drawableLeft;
    private Drawable drawableRight;
    private Drawable drawableTop;
    private int leftWidth;
    private int rightWidth;
    private int topWidth;
    private int leftHeight;
    private int rightHeight;
    private int topHeight;
    private Context mContext;

    public DrawableTextView(Context context) {
        super(context);
        this.mContext = context;
        init(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        drawableLeft = typedArray.getDrawable(R.styleable.DrawableTextView_leftDrawable);
        drawableRight = typedArray.getDrawable(R.styleable.DrawableTextView_rightDrawable);
        drawableTop = typedArray.getDrawable(R.styleable.DrawableTextView_topDrawable);
        if (drawableLeft != null) {
            leftWidth = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_leftDrawableWidth, dip2px(context, 20));
            leftHeight = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_leftDrawableHeight, dip2px(context, 20));
        }
        if (drawableRight != null) {
            rightWidth = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_rightDrawableWidth, dip2px(context, 20));
            rightHeight = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_rightDrawableHeight, dip2px(context, 20));
        }
        if (drawableTop != null) {
            topWidth = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_topDrawableWidth, dip2px(context, 20));
            topHeight = typedArray.getDimensionPixelOffset(R.styleable.DrawableTextView_topDrawableHeight, dip2px(context, 20));
        }
    }


    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, leftWidth, leftHeight);
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, rightWidth, rightHeight);
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, topWidth, topHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, null);

    }

    /**
     * 设置左侧图片并重绘
     */
    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
        invalidate();
    }

    /**
     * 设置左侧图片并重绘
     */
    public void setDrawableLeft(int drawableLeftRes) {
        this.drawableLeft = mContext.getResources().getDrawable(drawableLeftRes);
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableRight(Drawable drawableRight) {
        this.drawableRight = drawableLeft;
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableRight(int drawableRightRes) {
        this.drawableRight = mContext.getResources().getDrawable(drawableRightRes);
        invalidate();
    }

    /**
     * 设置上部图片并重绘
     */
    public void setDrawable(Drawable drawableTop) {
        this.drawableTop = drawableTop;
        invalidate();
    }

    /**
     * 设置右侧图片并重绘
     */
    public void setDrawableTop(int drawableTopRes) {
        this.drawableTop = mContext.getResources().getDrawable(drawableTopRes);
        invalidate();
    }
}