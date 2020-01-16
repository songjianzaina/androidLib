package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chhd.customkeyboard.KeyboardUtils;
import com.inswork.lib_cloudbase.R;

import java.util.List;

/**
 * ABCKeyboardView
 *
 * @author : 陈伟强 (2018/7/11)
 */
public class ABCKeyboardView extends NumberKeyboardView {

    private boolean isShift = false;
    private boolean isABC = true;

    public ABCKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ABCKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setKeyboard(new Keyboard(getContext(), R.xml.keyboard_abc));
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        super.onKey(primaryCode, keyCodes);
        if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // 大写
            isShift = !isShift;
            toggleShift();
            invalidateAllKeys();
        } else if (primaryCode == KEYCODE_NUMBER) {
            isShift = false;
            isABC = false;
            post(new Runnable() {
                @Override
                public void run() {
                    setKeyboard(new Keyboard(getContext(), R.xml.keyboard_number_abc));
                }
            });
        } else if (primaryCode == KEYCODE_ABC) {
            isShift = false;
            isABC = true;
            post(new Runnable() {
                @Override
                public void run() {
                    setKeyboard(new Keyboard(getContext(), R.xml.keyboard_abc));
                }
            });
        }
    }

    private void toggleShift() {
        Keyboard.Key key = getKey(Keyboard.KEYCODE_SHIFT);
        int resId = isShift ?
                R.drawable.ic_twotone_font_download_24dp : R.drawable.ic_outline_font_download_24dp;
        key.icon = getResources().getDrawable(resId);
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key item : keyList) {
            if (item.label != null && isWord(item.label.toString())) {
                if (isShift) {
                    item.label = item.label.toString().toUpperCase();
                } else {
                    item.label = item.label.toString().toLowerCase();
                }
                item.codes[0] = item.label.charAt(0);
            }
        }
    }


    private boolean isWord(String str) {
        String word = "abcdefghijklmnopqrstuvwxyz";
        return word.contains(str.toLowerCase());
    }

    private int screenWidth = KeyboardUtils.getScreenWidth(getContext());
    private double keyWidth = screenWidth * 0.0868;
    private double doneKeyWidth = screenWidth * 0.1362;
    private double horizontalGap = screenWidth * 0.012;

    @Override
    protected void handlerDoneKey() {
        if (isABC) {
            Keyboard.Key doneKey = getKey(Keyboard.KEYCODE_DONE);
            if (doneKey == null) {
                return;
            }
            if (isLast) {
                doneKey.width = 0;
                doneKey.icon = null;
                doneKey.label = null;
            } else {
                doneKey.width = (int) doneKeyWidth;
                doneKey.icon = getResources().getDrawable(R.drawable.ic_done_24dp);
                doneKey.label = null;
            }
        } else {
            super.handlerDoneKey();
        }
    }

    @Override
    protected void handlerTabKey() {
        if (isABC) {
            Keyboard.Key tabKey = getKey(KEYCODE_TAB);
            if (tabKey == null) {
                return;
            }
            if (isLast) {
                tabKey.width = (int) (keyWidth + horizontalGap + doneKeyWidth);
                tabKey.icon = getResources().getDrawable(R.drawable.ic_done_24dp);
                tabKey.label = null;
            } else {
                tabKey.width = (int) keyWidth;
                tabKey.icon = getResources().getDrawable(R.drawable.ic_baseline_keyboard_tab_24dp);
                tabKey.label = null;
            }
        } else {
            super.handlerTabKey();
        }
    }
}
