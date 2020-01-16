package com.insworks.lib_update.utils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.inswork.lib_cloudbase.utils.SystemUtils;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.insworks.lib_update.utils
 * @ClassName: AppStore
 * @Author: Song Jian
 * @CreateDate: 2019/8/5 10:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/5 10:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 第三方应用市场跳转
 */
public class AppStore {

    /**
     * 跳转到腾讯应用宝下载软件
     */
    public static void goTencentStore(String packageName) {
        if (isAvilible("com.tencent.android.qqdownloader")) {// 市场存在
            startAppStore(packageName, "com.tencent.android.qqdownloader");
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + packageName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            SystemUtils.mContext.startActivity(it);
        }
    }

    /**
     * 启动到app详情界面
     */
    public static void startAppStore(String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg)) return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SystemUtils.mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断软件是否存在
     */
    public static boolean isAvilible(String packageName) {
        try {
            SystemUtils.mContext.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
