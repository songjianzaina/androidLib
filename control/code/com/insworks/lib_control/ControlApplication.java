package com.insworks.lib_control;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class ControlApplication extends Application {

    private static ControlApplication instance;

    /**
     * 初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static ControlApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_control");
    }


}
