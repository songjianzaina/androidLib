package com.insworks.accessibility;

import android.app.Activity;
import android.os.Bundle;

import com.inswork.lib_cloudbase.R;
import com.insworks.accessibility.base.BaseAccessibilityService;


public class AccessibilityTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_accessibility_activity_accessibility_test);

        //初始化服务
        BaseAccessibilityService.getInstance().init(this);
    }


}
