package com.insworks.lib_datas.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.inswork.lib_cloudbase.R;


/**
 * 获取application节点下的medata工具类
 */
public class MetaDataUtil {


    public static String getMetaDataStr(Context context, String key) {
        String resultData = "";
        if (!TextUtils.isEmpty(key)) {
            Bundle appInfoBundle = getAppInfoBundle(context);
            if (appInfoBundle != null)
                resultData = appInfoBundle.getString(key);
        }
        return resultData;
    }

    public static int getMetaDataInt(Context context, String key) {
        int resultData = 0;
        if (!TextUtils.isEmpty(key)) {
            Bundle appInfoBundle = getAppInfoBundle(context);
            if (appInfoBundle != null)
                resultData = appInfoBundle.getInt(key);
        }
        return resultData;
    }

    /**
     * 获取清单文件中appid
     *
     * @param context
     * @param
     * @return
     */
    public static int readQQAPPID(Context context) {
        return getAppInfoBundle(context).getInt("QQ_APPID", 0);
    }


    /**
     * 获取清单文件中splash
     *
     * @param context
     * @param
     * @return
     */
    public static int readSplashPic(Context context) {
        return getAppInfoBundle(context).getInt("SPLASH", R.mipmap.ic_launcher);
    }

    /**
     * 获取清单文件中QQAPPKEY 长串的
     *
     * @param context
     * @param
     * @return
     */
    public static String readQQAppKey(Context context) {
        return getAppInfoBundle(context).getString("QQ_APP_KEY", "");
    }

    /**
     * 获取清单文件中前缀标识
     *
     * @param context
     * @param
     * @return
     */
    public static String readPerfix(Context context) {
        return getAppInfoBundle(context).getString("Prefix", "");
    }

    /**
     * 获取清单文件中Scheme标识
     *
     * @param context
     * @param
     * @return
     */
    public static String readAppScheme(Context context) {
        return getAppInfoBundle(context).getString("app_scheme", "");
    }

    /**
     * 获取清单文件中主机地址
     *
     * @param context
     * @param
     * @return
     */
    public static String readHost(Context context) {
        return getAppInfoBundle(context).getString("Host", "");
    }

    public static String readImageHost(Context context) {
        return getAppInfoBundle(context).getString("Image_Host", "");
    }

    /**
     * 获取清单文件中工程环境
     *
     * @param context
     * @param
     * @return
     */
    public static String readEnvironmentConfig(Context context) {
        return getAppInfoBundle(context).getString("environment", "online");
    }

    /**
     * 获取清单文件中QQAPPKEY 长串的
     *
     * @param context
     * @param
     * @return
     */
    public static String readWetchatAppKey(Context context) {
        return getAppInfoBundle(context).getString("WECHAT_APP_KEY", "");
    }


    /**
     * 获取清单文件中的WECHATAPPID
     *
     * @param context
     * @return
     */
    public static String readWECHATAPPID(Context context) {
        return getAppInfoBundle(context).getString("WECHAT_APPID", "");
    }


    /**
     * 获取友盟唯一key
     *
     * @param context
     * @return
     */
    public static String readUmengKey(Context context) {
        return getAppInfoBundle(context).getString("UMENG_SHARE_KEY", "");
    }

    public static int readLoginLogo(Context context) {
        return getAppInfoBundle(context).getInt("LOGIN_LOGO", R.mipmap.ic_launcher);
    }

    /**
     * 获取applogo
     *
     * @param context
     * @return
     */
    public static int readAppLogo(Context context) {
        return getAppInfoBundle(context).getInt("APP_LOGO", R.mipmap.ic_launcher);
    }

    private static Bundle getAppInfoBundle(Context context) {
        //注意：这里ApplicationInfo不能直接 CommonApplication.mApplication.getApplicationInfo()这样获取，否则会获取不到meta_data的
        ApplicationInfo applicationInfo = getAppInfo(context);
        if (applicationInfo != null) {
            return applicationInfo.metaData;
        }
        //避免空指针闪退
        return new Bundle();
    }

    private static ApplicationInfo getAppInfo(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        if (packageManager != null) {
            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return applicationInfo;
    }


}
