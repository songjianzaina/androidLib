package com.chhd.customkeyboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.chhd.customkeyboard.builder.InsertBuilder;
import com.chhd.customkeyboard.builder.PopupBuilder;
import com.chhd.customkeyboard.keyboard.ABCKeyboardView;
import com.chhd.customkeyboard.keyboard.BaseKeyboardView;
import com.chhd.customkeyboard.keyboard.NumberKeyboardView;

/**
 * CustomKeyboard
 *
 * @author : 陈伟强 (2018/7/6)
 */
@SuppressLint("ClickableViewAccessibility")
public class CustomKeyboard {

    private final String TAG = this.getClass().getSimpleName();

    private CustomKeyboard() {
    }

    public static PopupBuilder popup() {
        return new PopupBuilder();
    }

    public static InsertBuilder insert() {
        return new InsertBuilder();
    }
}
