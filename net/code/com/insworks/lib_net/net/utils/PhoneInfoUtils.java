package com.insworks.lib_net.net.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 工程里关于imei和meid获取不正确。避免修改后影响工程特单独写一份。
 * 或许二次开发使用本工具类
 */
public class PhoneInfoUtils {


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.DISPLAY;
        //return android.os.Build.VERSION.RELEASE;

    }


    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }


    /**
     * 获取SN
     *
     * @return
     */
    public static String getSn(Context ctx) {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");

        } catch (Exception ignored) {

        }

        return serial;
    }

    /**
     * 系统4.0的时候
     * 获取手机IMEI 或者MEID
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getImeiOrMeid(Context ctx) {
        TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (manager != null) {
            return manager.getDeviceId();
        }

        return null;
    }

    /**
     * 拿到imei或者meid后判断是有多少位数
     *
     * @param ctx
     * @return
     */
    public static int getNumber(Context ctx) {
        return getImeiOrMeid(ctx).trim().length();
    }


    /**
     *  5.0统一使用这个获取IMEI IMEI2 MEID
     *
     * @param ctx
     * @return
     */
    @SuppressLint("MissingPermission")
    @TargetApi(Build.VERSION_CODES.M)
    public static Map getImeiAndMeid(Context ctx) {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager mTelephonyManager = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        Class<?> clazz = null;
        Method method = null;//(int slotId)

        try {
            clazz = Class.forName("android.os.SystemProperties");
            method = clazz.getMethod("get", String.class, String.class);
            String gsm = (String) method.invoke(null, "ril.gsm.imei", "");
            String meid = (String) method.invoke(null, "ril.cdma.meid", "");
            map.put("meid", meid);
            if (!TextUtils.isEmpty(gsm)) {
                //the value of gsm like:xxxxxx,xxxxxx
                String imeiArray[] = gsm.split(",");
                if (imeiArray != null && imeiArray.length > 0) {
                    map.put("imei1", imeiArray[0]);

                    if (imeiArray.length > 1) {
                        map.put("imei2", imeiArray[1]);
                    } else {
                        map.put("imei2", mTelephonyManager.getDeviceId(1));
                    }
                } else {
                    map.put("imei1", mTelephonyManager.getDeviceId(0));
                    map.put("imei2", mTelephonyManager.getDeviceId(1));
                }
            } else {
                map.put("imei1", mTelephonyManager.getDeviceId(0));
                map.put("imei2", mTelephonyManager.getDeviceId(1));

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    public static Map getIMEIforO(Context context) {
        Map<String, String> map = new HashMap<String, String>();
        TelephonyManager tm = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        String imei1 = tm.getImei(0);
        String imei2 = tm.getImei(1);
        if(TextUtils.isEmpty(imei1)&&TextUtils.isEmpty(imei2)){

            map.put("imei1", tm.getMeid()); //如果CDMA制式手机返回MEID
        }else {
            map.put("imei1", imei1);

            map.put("imei2", imei2);
        }
        return map;
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int vercoe = 0;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            vercoe = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return vercoe;
    }


    /**
     * 调用之前请先做权限检查
     * @param ctx
     * @return
     */
    public static String getIMEI(Context ctx){
        String imei = "";
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){  //4.0以下 直接获取
            imei = getImeiOrMeid(ctx);
        }else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.O){ //5。0，6。0系统
            Map imeiMaps = getImeiAndMeid(ctx);
            imei = getTransform(imeiMaps);
        }else{
            Map imeiMaps = getIMEIforO(ctx);

            imei = getTransform(imeiMaps);
        }

        return imei;
    }


    private static String getTransform( Map imeiMaps){
        String imei = "";
        if(imeiMaps!=null){
            String imei1 = (String) imeiMaps.get("imei1");
            if(TextUtils.isEmpty(imei1)){
                return imei;
            }
            String imei2 = (String) imeiMaps.get("imei2");
            if(imei2!=null) {
                if (imei1.trim().length() == 15 && imei2.trim().length() == 15) {
                    //如果两个位数都是15。说明都是有效IMEI。根据从大到小排列
                    long i1 = Long.parseLong(imei1.trim());
                    long i2 = Long.parseLong(imei2.trim());
                    if(i1 > i2){
                        imei = imei2+";"+imei1;
                    }else{
                        imei = imei1+";"+imei2;
                    }

                }else{  //
                    if(imei1.trim().length() == 15){
                        //如果只有imei1是有效的
                        imei = imei1;
                    }else if (imei2.trim().length() == 15){
                        //如果只有imei2是有效的
                        imei = imei2;
                    }else{
                        //如果都无效那么都为meid。只取一个就可以
                        imei = imei1;
                    }

                }
            }else{
                imei = imei1;
            }
        }
        return imei;
    }
}