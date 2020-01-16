package com.insworks.accessibility.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_share
 * @ClassName: Packageutil
 * @Author: Song Jian
 * @CreateDate: 2019/5/14 13:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/14 13:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class Packageutil {
    //QQ
    public static final String QQ_FRIEND_PACKAGE_NAME = "com.tencent.mobileqq";
    //QQ空间
    public static final String QQ_QZONE_PACKAGE_NAME = "com.qzone";
    //支付宝
    public static final String ALIPAY = "com.eg.android.AlipayGphone";
    //联通应用
    public static final String UNICOM = "com.sinovatech.unicom.ui";

    /**
     * 检测手机是否安装了该应用
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstallPackage(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            try {
                context.getPackageManager().getApplicationInfo(packageName,
                        PackageManager.GET_UNINSTALLED_PACKAGES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }
        return false;
    }
}
