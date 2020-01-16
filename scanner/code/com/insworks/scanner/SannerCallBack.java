package com.insworks.scanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.insworks.lib_base.utils.BroadcastUtil;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_scanner
 * @ClassName: SannerCallBack
 * @Author: Song Jian
 * @CreateDate: 2019/5/23 10:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 10:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public abstract class SannerCallBack extends BaseScanner  {
    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
        bundle.putString(CodeUtils.RESULT_STRING, result);
        resultIntent.putExtras(bundle);
//        CloudScannerActivity.this.setResult(RESULT_OK, resultIntent);
//        CloudScannerActivity.this.finish();
        onSuccess(mBitmap,result);
        //扫描成功后通知关闭扫描界面
        BroadcastUtil.sendBroadcast("finish", "1", "action_finish");
    }

    protected abstract void onSuccess(Bitmap mBitmap, String result);

    @Override
    public void onAnalyzeFailed() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
        bundle.putString(CodeUtils.RESULT_STRING, "");
        resultIntent.putExtras(bundle);
//        CloudScannerActivity.this.setResult(RESULT_OK, resultIntent);
//        CloudScannerActivity.this.finish();
        onFailed();
    }

    protected abstract void onFailed();
}
