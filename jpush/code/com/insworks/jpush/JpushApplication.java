package com.insworks.jpush;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class JpushApplication extends Application {

    private static JpushApplication instance;

    /**
     * 极光推送库初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static JpushApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_jpush");
    }


}
