package com.insworks.lib_base.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by songjian on 2016/11/15.
 */
public class BroadcastUtil {
    private static final String TAG = "BroadcastUtil";
    private static Context mContext;

    private BroadcastUtil() {

    }

    public static void init(Context context) {
        mContext = context;
    }

    public static void sendBroadcast(String key, Object object, String action) {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 BroadcastUtil.init() 初始化！");
        }
        Intent intent = new Intent();
        intent.setAction(action);

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
            return;
        }

        mContext.sendBroadcast(intent);
    }

}
