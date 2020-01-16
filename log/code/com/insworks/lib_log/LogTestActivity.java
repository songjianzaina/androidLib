package com.insworks.lib_log;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.inswork.lib_cloudbase.R;

public class LogTestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_test);

        LogUtil.setLogTag("hahahahahhaahhaha");
        LogUtil.d("我是普通log");
        LogUtil.d("----=====----", "我是带自定义Tag的普通log");
        LogUtil.d("关闭log打印之前");
        LogUtil.setIsPrintLog(false);
        LogUtil.d("这条log不应该出现");


    }
}
