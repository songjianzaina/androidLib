package com.insworks.viewpager;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class ViewPagerApplication extends Application {

    private static ViewPagerApplication instance;

    /**
     * 始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static ViewPagerApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_viewpager");
    }


}
