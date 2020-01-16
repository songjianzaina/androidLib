package com.chhd.customkeyboard.builder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.chhd.customkeyboard.CustomKeyboard;
import com.chhd.customkeyboard.KeyboardUtils;
import com.chhd.customkeyboard.keyboard.BaseKeyboardView;
import com.chhd.customkeyboard.keyboard.NumberKeyboardView;
import com.inswork.lib_cloudbase.R;


/**
 * InsertBuilder
 *
 * @author : 陈伟强 (2018/7/10)
 */
@SuppressLint("ClickableViewAccessibility")
public class InsertBuilder {

    private EditText editText;

    private boolean isVibrate = true;

    private OnKeyClickListener onKeyClickListener;

    public InsertBuilder() {
    }

    public InsertBuilder setVibrate(boolean isVibrate) {
        this.isVibrate = isVibrate;
        return this;
    }

    public InsertBuilder setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
        return this;
    }

    public void bind(final EditText editText) {
        this.editText = editText;
        KeyboardUtils.disableSoftKeyboard(editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (editText.isEnabled() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    Activity activity = (Activity) v.getContext();
                    if (isShow(activity)) {
                        hide(activity);
                    }
                    insert(editText);
                }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.isEnabled() && hasFocus) {
                    Activity activity = (Activity) v.getContext();
                    if (isShow(activity)) {
                        hide(activity);
                    }
                    insert(editText);
                }
            }
        });
    }

    private void insert(final EditText editText) {
        final Activity activity = (Activity) editText.getContext();
//        editText.requestFocus();
        BaseKeyboardView keyboardView;
        if (KeyboardUtils.isNumber(editText)) {
            keyboardView = (BaseKeyboardView) View.inflate(activity,
                    R.layout.layout_number_keyboard_view, null);

        } else {
            keyboardView = (BaseKeyboardView) View.inflate(activity,
                    R.layout.layout_abc_keyboard_view, null);
        }
        keyboardView.setVibrate(isVibrate);
        keyboardView.attachTo(editText);
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.addOnLayoutChangeListener(onLayoutChangeListener);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        keyboardView.setOnKeyClickListener(new NumberKeyboardView.OnKeyClickListener() {

            @Override
            public void onTabClick() {

            }

            @Override
            public void onOkClick() {
                if (onKeyClickListener != null) {
                    onKeyClickListener.onOkClick();
                }
                if (isShow(activity)) {
                    hide(activity);
                }
            }
        });
        rootView.addView(keyboardView, params);
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isShow(activity)) {
                    hide(activity);
                }
                return false;
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && isShow(activity)) {
                    hide(activity);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean isShow(Activity activity) {
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        String tag = activity.getResources().getString(R.string.tag_base_custom_view);
        BaseKeyboardView baseKeyboardView = rootView.findViewWithTag(tag);
        return baseKeyboardView != null;
    }

    private void hide(Activity activity) {
        ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        String tag = activity.getResources().getString(R.string.tag_base_custom_view);
        BaseKeyboardView baseKeyboardView = rootView.findViewWithTag(tag);
        if (baseKeyboardView != null) {
            rootView.removeView(baseKeyboardView);
            baseKeyboardView.setVisibility(View.GONE);
        }
    }

    private View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() {

        @Override
        public void onLayoutChange(View v,
                                   int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            ViewGroup rootView = (ViewGroup) v;
            Context context = rootView.getContext();
            String tag = context.getResources().getString(R.string.tag_base_custom_view);
            BaseKeyboardView baseKeyboardView = rootView.findViewWithTag(tag);
            int hasMoved = 0;
            Object heightTag = rootView.getTag(R.id.scroll_height);
            if (heightTag != null) {
                hasMoved = (int) heightTag;
            }
            if (baseKeyboardView == null) {
                rootView.removeOnLayoutChangeListener(this);
                if (hasMoved > 0) {
                    rootView.getChildAt(0).scrollBy(0, -1 * hasMoved);
                    rootView.setTag(R.id.scroll_height, 0);
                }
            } else {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);
                int[] etLocation = new int[2];
                editText.getLocationOnScreen(etLocation);
                int keyboardTop = etLocation[1] + editText.getHeight() + editText.getPaddingTop()
                        + editText.getPaddingBottom();
                int moveHeight = keyboardTop + baseKeyboardView.getHeight() - rect.bottom;
                if (moveHeight > 0) {
                    rootView.getChildAt(0).scrollBy(0, moveHeight);
                    rootView.setTag(R.id.scroll_height, hasMoved + moveHeight);
                }
            }
        }
    };

    public interface OnKeyClickListener {

        void onOkClick();
    }
}
