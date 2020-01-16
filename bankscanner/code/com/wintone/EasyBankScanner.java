package com.wintone;

import android.app.Activity;
import android.content.Intent;

import com.wintone.smartvision_bankCard.ScanCamera;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @ProjectName: tftpay
 * @Package: com.wintone
 * @ClassName: EasyBankScanner
 * @Author: Song Jian
 * @CreateDate: 2019/6/6 10:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 10:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 银行卡扫描 入口类
 */
public class EasyBankScanner {

    private static EasyBankScanner easyBankScanner;
    private static Activity activity;
    private OnBankScannerResultListener listener;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyBankScanner.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static EasyBankScanner init(Activity activity) {
        EasyBankScanner.activity = activity;
        //初始化第三方库  该库后期可替换成其他
        if (easyBankScanner == null) {
            synchronized (EasyBankScanner.class) {
                if (easyBankScanner == null) {
                    easyBankScanner = new EasyBankScanner();
                }
            }
        }
        return easyBankScanner;
    }

    public void openScanner(OnBankScannerResultListener listener) {
        this.listener = listener;
        testInitialize();
        testPermission();

    }

    /**
     * 权限检测
     */
    private void testPermission() {
        AndPermission.with(activity)
                .permission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA})
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Intent intentTack = new Intent(activity, ScanCamera.class);
                        ScanCamera.setOnBankScannerResultListener(listener);
                        activity.startActivity(intentTack);
                    }
                })
                .start();
    }

}
