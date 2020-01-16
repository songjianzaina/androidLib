package com.insworks.lib_base.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by songjian on 2017/1/6.
 */
public class ToastUtil {
    private static Context mContext;
    private static final String TAG = "ToastUtil";
    private static Toast toast;

    private ToastUtil() {

    }

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 短吐司
     */
    public static void showToast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 长吐司
     */
    public static void showLongToast(String msg) {
        toast(msg, Toast.LENGTH_LONG);
    }

    private static void toast(String msg, int lengthLong) {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 ToastUtil.init() 初始化！");
        }
        if (toast != null) {
            toast.cancel();//关闭吐司显示
        }
        toast = Toast.makeText(mContext, msg, lengthLong);
        toast.show();//重新显示吐司
    }
}
