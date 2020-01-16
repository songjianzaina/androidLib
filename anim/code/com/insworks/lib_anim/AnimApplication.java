package com.insworks.lib_anim;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class AnimApplication extends Application {

    private static AnimApplication instance;

    /**
     * 初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static AnimApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_anim");
    }


}
