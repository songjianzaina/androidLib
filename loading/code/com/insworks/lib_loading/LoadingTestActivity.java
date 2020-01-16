package com.insworks.lib_loading;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.inswork.lib_cloudbase.R;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_loading
 * @ClassName: LoadingTestActivity
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 12:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 12:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: loading测试类
 */
public class LoadingTestActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_loading_activity_loding_test);
    }

    public void loading(View view) {
        LoadingUtil.init(this).show();
    }
}
