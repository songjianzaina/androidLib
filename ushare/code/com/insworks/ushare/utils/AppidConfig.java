package com.insworks.ushare.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_share.bilib.helper
 * @ClassName: AppidConfig
 * @Author: Song Jian
 * @CreateDate: 2019/8/23 13:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/23 13:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 从清单文件中获取分享id和密钥 由于云中付复制为多个版本 所以密钥放在清单文件中进行路由中转
 */
public class AppidConfig {


    /**
     * 获取清单文件中appid
     *
     * @param context
     * @param
     * @return
     */
    public static int readQQAPPID(Context context) {
        try {
            ApplicationInfo appInfo = getApplicationInfo(context);
            return appInfo.metaData.getInt("QQ_APPID", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取清单文件中QQAPPKEY 长串的
     *
     * @param context
     * @param
     * @return
     */
    public static String readQQAppKey(Context context) {
        try {
            ApplicationInfo appInfo = getApplicationInfo(context);
            return appInfo.metaData.getString("QQ_APP_KEY", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取清单文件中QQAPPKEY 长串的
     *
     * @param context
     * @param
     * @return
     */
    public static String readWetchatAppKey(Context context) {
        try {
            ApplicationInfo appInfo = getApplicationInfo(context);
            return appInfo.metaData.getString("WECHAT_APP_KEY", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取清单文件中的WECHATAPPID
     *
     * @param context
     * @return
     */
    public static String readWECHATAPPID(Context context) {
        try {
            ApplicationInfo appInfo = getApplicationInfo(context);
            return appInfo.metaData.getString("WECHAT_APPID", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static ApplicationInfo getApplicationInfo(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getApplicationInfo(
                context.getPackageName(), PackageManager.GET_META_DATA);
    }

    /**
     * 获取友盟唯一key
     * @param context
     * @return
     */
    public static String readUmengKey(Activity context) {
        try {
            ApplicationInfo appInfo = getApplicationInfo(context);
            return appInfo.metaData.getString("UMENG_SHARE_KEY", "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
