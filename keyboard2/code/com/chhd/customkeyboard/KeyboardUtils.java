package com.chhd.customkeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * KeyboardUtils
 *
 * @author : 陈伟强 (2018/7/10)
 */
public class KeyboardUtils {

    public static final int NUMBER_TYPE_SIGNED_DECIMAL =
            InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED + InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int NUMBER_TYPE_SIGNED = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_SIGNED;
    public static final int NUMBER_TYPE_DECIMAL = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int NUMBER_TYPE_DEFAULT = InputType.TYPE_CLASS_NUMBER;

    private KeyboardUtils() {
    }

    public static void disableSoftKeyboard(EditText editText) {
        View parent = (View) editText.getParent();
        if (parent != null) {
            parent.setFocusableInTouchMode(true);
        }
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus =
                        cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
    }

    public static boolean isNumber(EditText editText) {
        int inputType = editText.getInputType();
        return inputType == NUMBER_TYPE_SIGNED_DECIMAL ||
                inputType == NUMBER_TYPE_SIGNED ||
                inputType == NUMBER_TYPE_DECIMAL ||
                inputType == NUMBER_TYPE_DEFAULT;
    }

    public static int getScreenValidHeight(Context context) {
        return getScreenHeight(context) - getNavBarHeight();
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        assert wm != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        assert wm != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    private static int getNavBarHeight() {
        Resources res = Resources.getSystem();
        int resourceId = res.getIdentifier("navigation_bar_height",
                "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
