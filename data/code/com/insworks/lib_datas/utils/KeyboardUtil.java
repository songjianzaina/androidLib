package com.insworks.lib_datas.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Huangdroid on 15/12/8.
 */
public class KeyboardUtil {
    public static void hideKeyBoard(Activity activity) {
        hideKeyBoard(activity, false);
    }

    public static void hideKeyBoard(Activity activity, boolean clearFocus) {
        View view = activity.getCurrentFocus();
        hideKeyBoard(activity, view, clearFocus);
    }

    public static void hideKeyBoard(Activity activity, View view, boolean clearFocus) {
        if (view != null) {
            if (clearFocus) {
                view.clearFocus();
            }
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 获取键盘是否已经打开
     */
    public static boolean isKeyboardShowing(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    public static void showKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.showSoftInput(view, 0);
//        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }




}
