package com.insworks.lib_keyboard.one;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;

/**
 * 直接传入包含键盘和密码框的布局  适用于自定义
 */

public class InputKeyboardView extends RelativeLayout implements View.OnClickListener {

    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;
    TextView number5;
    TextView number6;
    TextView number7;
    TextView number8;
    TextView number9;
    TextView number0;
    ImageView delete;


    private OnInputKeyboardListener onKeyboardListener;

    public InputKeyboardView(Context context) {
        this(context, null);
    }

    public InputKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public InputKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_num_keyboard, this);
        initView();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按返回键 隐藏键盘
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShown()) {
                setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {

        number0 = findViewById(R.id.number0);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);
        delete = findViewById(R.id.delete);

        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        delete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (onKeyboardListener==null) {
            return;
        }
        if (v.getId() == R.id.delete) {
            onKeyboardListener.onDeleteKeyEvent();
        }
        if (onKeyboardListener == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.number1) {
            onKeyboardListener.onInsertKeyEvent("1");
        } else if (i == R.id.number2) {
            onKeyboardListener.onInsertKeyEvent("2");
        } else if (i == R.id.number3) {
            onKeyboardListener.onInsertKeyEvent("3");
        } else if (i == R.id.number4) {
            onKeyboardListener.onInsertKeyEvent("4");
        } else if (i == R.id.number5) {
            onKeyboardListener.onInsertKeyEvent("5");
        } else if (i == R.id.number6) {
            onKeyboardListener.onInsertKeyEvent("6");
        } else if (i == R.id.number7) {
            onKeyboardListener.onInsertKeyEvent("7");
        } else if (i == R.id.number8) {
            onKeyboardListener.onInsertKeyEvent("8");
        } else if (i == R.id.number9) {
            onKeyboardListener.onInsertKeyEvent("9");
        } else if (i == R.id.number0) {
            onKeyboardListener.onInsertKeyEvent("0");
        }

    }

    public interface OnInputKeyboardListener {
        void onInsertKeyEvent(String text);

        void onDeleteKeyEvent();
    }
    public void setIOnKeyboardListener(OnInputKeyboardListener onKeyboardListener) {

        this.onKeyboardListener = onKeyboardListener;
    }




}
