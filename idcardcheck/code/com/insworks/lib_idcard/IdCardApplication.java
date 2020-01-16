package com.insworks.lib_idcard;

import android.app.Application;

import com.insworks.lib_log.LogUtil;

public class IdCardApplication extends Application {

    private static IdCardApplication instance;

    /**
     * 身份证识别初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static IdCardApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_idcard");
    }


}
