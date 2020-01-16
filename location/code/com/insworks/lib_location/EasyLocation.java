package com.insworks.lib_location;

import android.app.Activity;

import com.insworks.lib_location.utils.GetAddressUtil;
import com.insworks.lib_location.utils.LocationUtil;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_location
 * @ClassName: EasyLocation
 * @Author: Song Jian
 * @CreateDate: 2019/7/9 13:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/9 13:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 位置操作入口
 */
public class EasyLocation {


    private static Activity activity;
    private static EasyLocation easyLocation;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyLocation.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static EasyLocation init(Activity activity) {
        EasyLocation.activity = activity;
        if (easyLocation == null) {
            synchronized (EasyLocation.class) {
                if (easyLocation == null) {
                    easyLocation = new EasyLocation();
                }
            }
        }
        return easyLocation;
    }

    /**
     * 获取经纬度信息
     *
     * @param callback
     */
    public void getCurrentLocation(LocationUtil.LocationCallBack callback) {
        LocationUtil.getCurrentLocation(activity, callback);
    }

    /**
     * 根据经纬度获取当前地址
     *
     * @param
     */
    public String getCurrentAddress(double longitude, double latitude) {

        return new GetAddressUtil(activity).getAddress(longitude, latitude);
    }
}
