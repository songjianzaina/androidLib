package com.insworks.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.utils.ResourceUtils;

/**
 * Created by Huangdroid on 16/9/13.
 */

public class CommonButton extends Button {
    public CommonButton(Context context) {
        this(context, null, 0);
    }

    public CommonButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonButtonAttrs, defStyleAttr, 0);

        Drawable drawable = a.getDrawable(R.styleable.CommonButtonAttrs_button_click_selector);
        if (drawable != null) {
            setBackgroundDrawable(drawable);
        } else {
            int radius = a.getDimensionPixelSize(R.styleable.CommonButtonAttrs_button_radius, 0);
            int enableColor = a.getColor(R.styleable.CommonButtonAttrs_button_enable_solid_color, -1);
            int unEnableColor = a.getColor(R.styleable.CommonButtonAttrs_button_unenable_solid_color, -1);
            int strokeColor = a.getColor(R.styleable.CommonButtonAttrs_button_stroke_color, -1);
            int pressColor = a.getColor(R.styleable.CommonButtonAttrs_button_press_color, -1);
            int strokeWidth = a.getDimensionPixelSize(R.styleable.CommonButtonAttrs_button_stroke_width, 0);

            if (pressColor == -1) {
                pressColor = ResourceUtils.adjustAlpha(enableColor, 0.6f);
            }

            setBackgroundDrawable(createSelector(enableColor, unEnableColor, pressColor, strokeColor, strokeWidth, radius));
        }
        a.recycle();
    }

    private StateListDrawable createSelector(int enableColor, int unEnableColor, int pressColor, int strokeColor, int strokeWidth, int radius) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{-android.R.attr.state_enabled},
                ResourceUtils.createShape(unEnableColor, strokeColor, strokeWidth, radius));
        drawable.addState(new int[]{android.R.attr.state_selected},
                ResourceUtils.createShape(pressColor, strokeColor, strokeWidth, radius));
        drawable.addState(new int[]{android.R.attr.state_pressed},
                ResourceUtils.createShape(pressColor, strokeColor, strokeWidth, radius));
        drawable.addState(new int[]{android.R.attr.state_focused},
                ResourceUtils.createShape(pressColor, strokeColor, strokeWidth, radius));
        drawable.addState(new int[]{android.R.attr.state_enabled},
                ResourceUtils.createShape(enableColor, strokeColor, strokeWidth, radius));
        return drawable;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            if (drawableLeft != null || drawableRight != null) {
                float textWidth = getPaint().measureText(getText().toString());
                int drawablePadding = getCompoundDrawablePadding();
                int drawableWidth = 0;
                if (drawableLeft != null)
                    drawableWidth = drawableLeft.getIntrinsicWidth();
                else if (drawableRight != null) {
                    drawableWidth = drawableRight.getIntrinsicWidth();
                }
                float bodyWidth = textWidth + drawableWidth + drawablePadding + getPaddingLeft() + getPaddingRight();
                canvas.translate((getWidth() - bodyWidth) / 2, 0);
            }
        }
        super.onDraw(canvas);
    }
}
