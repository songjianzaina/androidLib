package com.insworks.ushare;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class UshareApplication extends Application {

    private static UshareApplication instance;

    /**
     * 模块初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static UshareApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_ushare");
    }


}
