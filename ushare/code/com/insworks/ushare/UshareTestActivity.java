package com.insworks.ushare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.UMShareAPI;

public class UshareTestActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);


    }

    /**
     * QQ与新浪不需要添加Activity，但需要在使用QQ分享或者授权的Activity中，添加：
     * 注意onActivityResult不可在fragment中实现，如果在fragment中调用登录或分享，需要在fragment依赖的Activity中实现
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
