package com.insworks.scanner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_scanner
 * @ClassName: TextScannerActivity
 * @Author: Song Jian
 * @CreateDate: 2019/5/24 12:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/24 12:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class TextScannerActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_scanner_activity_scanner_test);

    }

    public void startScanner(View view) {
        //开启扫描界面
        EasyScanner.init(this).startCloudScanView(new SannerCallBack() {
            @Override
            protected void onSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(TextScannerActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onFailed() {
                Toast.makeText(TextScannerActivity.this, "扫描失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
