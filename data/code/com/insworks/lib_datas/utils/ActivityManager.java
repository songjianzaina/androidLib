package com.insworks.lib_datas.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;
import java.util.Stack;

/**
 * Activity 管理类，便于在任意地方管理所有Activity实例
 * <p/>
 * Created by jiang on 2015/7/30.
 */
public class ActivityManager {
    private Stack<Activity> actStack;
    private static ActivityManager instance;

    private ActivityManager() {
        if (actStack == null) {
            actStack = new Stack<Activity>();
        }
    }

    public synchronized static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            actStack.add(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     *
     * @return 如果记录为空将返回null
     */
    public Activity getCurrentActivity() {
        if (!actStack.isEmpty()) {
            return actStack.lastElement();
        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (!actStack.isEmpty()) {
            Activity activity = actStack.lastElement();
            finishActivity(activity);
        }
    }

    /**
     * 从栈中移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            actStack.removeElement(activity);
        }
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            actStack.removeElement(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : actStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = actStack.size(); i < size; i++) {
            Activity activity = actStack.get(i);
            if (null != activity) {
                activity.finish();
            }
        }
        actStack.clear();
    }

    /**
     * 获取activity数量
     */
    public void getActivityCount() {
        actStack.size();
    }

    /**
     * 判断记录是否为空
     *
     * @return 如果为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return actStack.isEmpty();
    }


    /**
     * 获取栈顶Activity名称
     * @param am
     * @return
     */
    public static ComponentName getTopActivityName(android.app.ActivityManager am) {
        List<android.app.ActivityManager.RunningTaskInfo> taskList = null;
        try {
            taskList = am.getRunningTasks(1);
            if (taskList != null && taskList.size() > 0) {
                ComponentName cname = taskList.get(0).topActivity;
                return cname;
            }
        } catch (Exception e) {
            // should not be here, but in some system, the getRunningTasks will fail with crash...
        }
        return null;
    }

    /**
     * 判断当前Activity是否处在栈顶
     * @param className
     * @param context
     * @return
     */
    public static boolean isTopActivity(String className, Context context) {
        android.app.ActivityManager am = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName componentName = getTopActivityName(am);
        if (componentName != null) {
            return componentName.getClassName().contains(className);
        }

        return false;
    }

}
