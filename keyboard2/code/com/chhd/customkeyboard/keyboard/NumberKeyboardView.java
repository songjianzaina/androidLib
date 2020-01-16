package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.Keyboard;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chhd.customkeyboard.KeyboardUtils;
import com.inswork.lib_cloudbase.R;

/**
 * NumberKeyboardView
 *
 * @author : 葱花滑蛋 (2018/7/5)
 */
public class NumberKeyboardView extends BaseKeyboardView {

    @Override
    public void attachTo(EditText editText) {
        super.attachTo(editText);
        handlerMinusKey();
        handlerDecimalKey();
        invalidateAllKeys();
    }

    private void handlerMinusKey() {
        int inputType = editText.getInputType();
        Keyboard.Key minus = getKey(45);
        if (minus == null) {
            return;
        }
        if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED_DECIMAL) {
            minus.label = "-";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED) {
            minus.label = "-";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_DECIMAL) {
            minus.label = "";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_DEFAULT) {
            minus.label = "";
        } else {
            minus.label = "-";
        }
    }

    private void handlerDecimalKey() {
        int inputType = editText.getInputType();
        Keyboard.Key decimal = getKey(46);
        if (decimal == null) {
            return;
        }
        if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED_DECIMAL) {
            decimal.label = ".";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_SIGNED) {
            decimal.label = "";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_DECIMAL) {
            decimal.label = ".";
        } else if (inputType == KeyboardUtils.NUMBER_TYPE_DEFAULT) {
            decimal.label = "";
        } else {
            decimal.label = ".";
        }
    }

    public NumberKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setKeyboard(new Keyboard(getContext(), R.xml.keyboard_number));
        setOnKeyboardActionListener(this);
    }

    private int screenValidHeight = KeyboardUtils.getScreenValidHeight(getContext());
    private double keyHeight = screenValidHeight * 0.075;
    private double verticalGap = screenValidHeight * 0.015;

    @Override
    public void onDraw(Canvas canvas) {
        handlerSpecKey();
        super.onDraw(canvas);

        for (Keyboard.Key item : getKeyboard().getKeys()) {
//            drawNotTouchKey(item, canvas);
            drawDoneKey(item, canvas);
        }
    }

    private void handlerSpecKey() {
        handlerDoneKey();
        handlerTabKey();
    }

    protected void handlerTabKey() {
        Keyboard.Key tabKey = getKey(KEYCODE_TAB);
        if (tabKey == null) {
            return;
        }
        if (isLast) {
            tabKey.height = (int) (keyHeight * 2 + verticalGap);
            tabKey.icon = getResources().getDrawable(R.drawable.ic_done_24dp);
            tabKey.label = null;
        } else {
            tabKey.height = (int) keyHeight;
            tabKey.icon = getResources().getDrawable(R.drawable.ic_baseline_keyboard_tab_24dp);
            tabKey.label = null;
        }
    }

    protected void handlerDoneKey() {
        Keyboard.Key doneKey = getKey(Keyboard.KEYCODE_DONE);
        if (doneKey == null) {
            return;
        }
        if (isLast) {
            doneKey.height = 0;
            doneKey.icon = null;
            doneKey.label = null;
        } else {
            doneKey.height = (int) keyHeight;
            doneKey.icon = getResources().getDrawable(R.drawable.ic_done_24dp);
            doneKey.label = null;
        }
    }
}
