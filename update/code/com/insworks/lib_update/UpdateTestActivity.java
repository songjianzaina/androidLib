package com.insworks.lib_update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.base.UIActivity;
import com.inswork.lib_cloudbase.event.Event;


public class UpdateTestActivity extends UIActivity{

    public void update(View view) {
        //点击 立即更新
        EasyUpdate.init(this).updateApk(false,"是否升级到最新版本","1.修改UI显示问题\n2.修复app崩溃问题","https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk");
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected boolean isTitleBarBgTrans() {
        return false;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void receiveEvent(Event event) {

    }

    @Override
    protected void receiveStickyEvent(Event event) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.lib_update_activity_update_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData(Intent intent, Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onViewClick(View v) {

    }
}
