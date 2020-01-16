package com.insworks.lib_update;

import android.app.Application;

import com.insworks.lib_log.LogUtil;
import com.zhouyou.http.EasyHttp;

public class UpdateApplication extends Application {

    private static UpdateApplication instance;

    /**
     * 初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        init();
    }

    public static UpdateApplication getInstance() {
        return instance;
    }

    private void init() {
        //设置当前LogTag
        LogUtil.setLogTag("lib_update");
        EasyHttp.init(this);
    }


}
