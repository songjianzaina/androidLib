package com.chhd.customkeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.chhd.customkeyboard.keyboard.ABCKeyboardView;
import com.chhd.customkeyboard.keyboard.BaseKeyboardView;
import com.chhd.customkeyboard.keyboard.NumberKeyboardView;
import com.inswork.lib_cloudbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * CustomKeyboardLayout
 *
 * @author : 陈伟强 (2018/7/6)
 */
public class CustomKeyboardView extends LinearLayout implements BaseKeyboardView.OnKeyClickListener {

    private final String TAG = this.getClass().getSimpleName();

    private CustomKeyboardView instance;

    private FrameLayout flContainer;
    private NumberKeyboardView numberKeyboardView;
    private ABCKeyboardView abcKeyboardView;

    private List<EditText> etList = new ArrayList<>();
    private EditText currentEt;

    private boolean isCircle = true;
    private OnKeyClickListener onKeyClickListener;
    private OnFocusChangeListener onFocusChangeListener;

    public void setVibrate(boolean isVibrate) {
        numberKeyboardView.setVibrate(isVibrate);
        abcKeyboardView.setVibrate(isVibrate);
    }

    public void setCircle(boolean circle) {
        isCircle = circle;
    }

    public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    public CustomKeyboardView(Context context) {
        this(context, null);
    }

    public CustomKeyboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        instance = this;

        LayoutInflater.from(getContext()).inflate(R.layout.layout_popup, this, true);

        setId(R.id.custom_keyboard_view);

        flContainer = findViewById(R.id.fl_container);
        numberKeyboardView = findViewById(R.id.number_keyboard_view);
        abcKeyboardView = findViewById(R.id.abc_keyboard_view);

        numberKeyboardView.setOnKeyClickListener(this);
        abcKeyboardView.setOnKeyClickListener(this);
    }

    public void setContainerView(View containerView) {
        flContainer.addView(containerView);
        post(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }

    private void initData() {
        etList.clear();
        findEditText(etList, instance);
        if (etList.isEmpty()) {
            throw new IllegalArgumentException("当前布局不含有任何EditText类型控件");
        }
        for (EditText et : etList) {
            if (isValid(et)) {
                attachTo(et);
                onKeyClickListener.onTabClick(instance, currentEt);
                onFocusChangeListener.onFocusChange(instance, currentEt);
                break;
            }
        }
        for (final EditText et : etList) {
            et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        etList.clear();
                        findEditText(etList, instance);
                        onFocusChangeListener.onFocusChange(instance, (EditText) v);
                        attachTo((EditText) v);
                    }
                }
            });
        }
    }

    public void attachTo(final EditText et) {
        currentEt = et;
        et.setSelection(et.getText().length());
        et.requestFocus();
        if (KeyboardUtils.isNumber(et)) {
            attachToNumber(et);
        } else {
            attachToABC(et);
        }
    }

    private void attachToNumber(final EditText et) {
        numberKeyboardView.setVisibility(VISIBLE);
        abcKeyboardView.setVisibility(GONE);
        int lastIndexOf = etList.lastIndexOf(et);
        boolean isLast = lastIndexOf == etList.size() - 1;
        isLast = !isCircle && isLast;
        isLast = etList.size() == 1 || isLast;
        numberKeyboardView.setLast(isLast);
        post(new Runnable() {
            @Override
            public void run() {
                numberKeyboardView.attachTo(et);
            }
        });
    }

    private void attachToABC(final EditText et) {
        numberKeyboardView.setVisibility(GONE);
        abcKeyboardView.setVisibility(VISIBLE);
        int lastIndexOf = etList.lastIndexOf(et);
        boolean isLast = lastIndexOf == etList.size() - 1;
        isLast = !isCircle && isLast;
        isLast = etList.size() == 1 || isLast;
        abcKeyboardView.setLast(isLast);
        post(new Runnable() {
            @Override
            public void run() {
                abcKeyboardView.attachTo(et);
            }
        });
    }

    private void findEditText(List<EditText> list, View parent) {
        if (parent == null) {
            return;
        }
        if (parent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = viewGroup.getChildAt(i);
                findEditText(list, child);
            }
        }
        if (parent instanceof EditText) {
            EditText et = (EditText) parent;
            KeyboardUtils.disableSoftKeyboard(et);
            if (isValid(et)) {
                list.add(et);
            }
        }
    }

    private boolean isValid(EditText et) {
        return et.isEnabled() && et.getVisibility() == VISIBLE;
    }

    @Override
    public void onTabClick() {
        int lastIndexOf = etList.lastIndexOf(currentEt);
        int len = etList.size() - 1;
        int start = lastIndexOf + 1;
        start = start > len ? 0 : start;
        for (int i = start; i <= len; i++) {
            EditText last = currentEt;
            EditText et = etList.get(i);
            currentEt = et;
            attachTo(currentEt);
            onKeyClickListener.onTabClick(this, currentEt);
            return;
        }
    }

    @Override
    public void onOkClick() {
        if (onKeyClickListener != null) {
            onKeyClickListener.onOkClick(instance);
        }
    }

    public interface OnKeyClickListener {

        void onTabClick(CustomKeyboardView root, EditText current);

        void onOkClick(CustomKeyboardView root);
    }

    public interface OnFocusChangeListener {

        void onFocusChange(CustomKeyboardView root, EditText current);
    }
}
