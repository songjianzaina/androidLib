package com.insworks.lib_base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by songjian on 2017/1/6.
 */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static Context mContext;


    protected ActivityUtil() {

    }


    public static void init(Context context) {
        mContext = context;
    }

    //Activity跳转
    public static void startActivity(Class clazz) {
        testInitialize();
        mContext.startActivity(new Intent(mContext, clazz));
    }

    //Activity跳转 隐式跳转
    public static void startActivity(String action) {
        testInitialize();
        //隐式跳转
        Intent intent=new Intent();
        intent.setAction(action);
        mContext.startActivity(intent);
    }

    //Activity跳转 隐式跳转 携带参数
    public static void startActivity(String action,String key, Object object) {
        testInitialize();
        //隐式跳转
        Intent intent=new Intent();
        intent.setAction(action);
        if (setIntentData(key, object, intent)) return;

        mContext.startActivity(intent);
    }

    private static boolean setIntentData(String key, Object object, Intent intent) {
        if (object instanceof String) {
            intent.putExtra(key, (String) object);
        } else if (object instanceof String[]) {
            intent.putExtra(key, (String[]) object);
        } else if (object instanceof Integer) {
            intent.putExtra(key, (Integer) object);
        } else if (object instanceof Integer[]) {
            intent.putExtra(key, (Integer[]) object);
        } else if (object instanceof Serializable) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(key, (Serializable) object);
            intent.putExtras(bundle);
        } else if (object instanceof Float) {
            intent.putExtra(key, (Float) object);
        } else if (object instanceof Float[]) {
            intent.putExtra(key, (Float[]) object);
        } else if (object instanceof Boolean) {
            intent.putExtra(key, (Boolean) object);

        } else if (object instanceof Boolean[]) {
            intent.putExtra(key, (Boolean[]) object);

        } else if (object instanceof Byte) {
            intent.putExtra(key, (Byte) object);

        } else if (object instanceof Byte[]) {
            intent.putExtra(key, (Byte[]) object);

        } else if (object instanceof Character) {
            intent.putExtra(key, (Character) object);

        } else if (object instanceof Character[]) {
            intent.putExtra(key, (Character[]) object);

        } else if (object instanceof Short) {
            intent.putExtra(key, (Short) object);

        } else if (object instanceof Short[]) {
            intent.putExtra(key, (Short[]) object);

        } else if (object instanceof Double) {
            intent.putExtra(key, (Double) object);

        } else if (object instanceof Double[]) {
            intent.putExtra(key, (Double[]) object);

        } else if (object instanceof Long) {
            intent.putExtra(key, (Long) object);

        } else if (object instanceof Long[]) {
            intent.putExtra(key, (Long[]) object);

        } else if (object instanceof CharSequence) {
            intent.putExtra(key, (CharSequence) object);

        } else if (object instanceof CharSequence[]) {
            intent.putExtra(key, (CharSequence[]) object);

        } else if (object instanceof Parcelable) {
            intent.putExtra(key, (Parcelable) object);

        } else if (object instanceof Parcelable[]) {
            intent.putExtra(key, (Parcelable[]) object);

        } else {
            return true;
        }
        return false;
    }

    //Activity跳转 并且销毁当前Activity
    public static void startActivityFinish(Class clazz, Activity finishActivity) {
        testInitialize();
        mContext.startActivity(new Intent(mContext, clazz));
        if (finishActivity != null) {
            finishActivity.finish();
        }
    }
    protected static void testInitialize() {
        if (mContext == null)
            throw new ExceptionInInitializerError("请先在全局Application中调用 ActivityUtil.init() 初始化！");
    }
    //Activity跳转 并携带数据
    public static void startActivity(Class clazz, String key, Object object) {
        testInitialize();
        Intent intent = new Intent(mContext, clazz);
        if (setIntentData(key, object, intent)) return;

        mContext.startActivity(intent);
    }


}
