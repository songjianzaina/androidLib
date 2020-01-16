package com.chhd.customkeyboard.test;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 葱花滑蛋 on 2019/1/17.
 */
public class ViewUtils {

    public static <T> T findView(View parent, Class<T> clazz) {
        return (T) forEachView(parent, clazz).get(0);
    }


    public static <T> List<T> findViews(View parent, Class<T> clazz) {
        return forEachView(parent, clazz);
    }

    private static <T> List<T> forEachView(View parent, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (parent == null) {
            return list;
        }
        if (!(parent instanceof ViewGroup)) {
            return list;
        }
        if (clazz.isInstance(parent)) {
            list.add((T) parent);
        }
        ViewGroup viewGroup = (ViewGroup) parent;
        int childCount = viewGroup.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup) {
                List<T> tempList = forEachView(child, clazz);
                list.addAll(tempList);
            }
        }
        return list;
    }
}
