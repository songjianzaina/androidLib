package com.insworks.lib_loading;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.insworks.lib_log.LogUtil;

import java.util.List;

public class LoadingApplication extends Application {

    private static LoadingApplication instance;
    private static Activity currentActivity;

    /**
     * 自定义加载对话框库 初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static LoadingApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_loading");
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d("YWK",activity+"onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d("YWK",activity+"onActivityStarted");
                currentActivity=activity;

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }



    public static Activity getCurrentActivity(){
        return currentActivity;
    }


    /**
     * 获得栈中最顶层的Activity的 包名
     *
     * @param
     * @return
     */
    public static String getTopActivityClassName() {
        android.app.ActivityManager manager = (android.app.ActivityManager) instance.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            //下面这句返回 ComponentInfo{com.insworks.lib_loading/com.insworks.lib_loading.LoadingTestActivity}
            //            return (runningTaskInfos.get(0).topActivity).toString();
            return (runningTaskInfos.get(0).topActivity).getClassName();
        } else {
            return null;
        }
    }

}
