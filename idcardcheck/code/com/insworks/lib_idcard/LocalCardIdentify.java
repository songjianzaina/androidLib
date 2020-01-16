package com.insworks.lib_idcard;

import android.app.Activity;
import androidx.core.app.ActivityCompat;

import com.insworks.lib_idcard.utils.CheckPermissionUtil;

import io.github.dltech21.ocr.IDCardEnum;
import io.github.dltech21.ocr.OcrCameraActivity;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_idcard
 * @ClassName: LocalCardIdentify
 * @Author: Song Jian
 * @CreateDate: 2019/5/28 15:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/28 15:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 身份证识别工具类
 */
public class LocalCardIdentify {
    public static final int REQUEST_CODE = 1001;
    private static LocalCardIdentify localCardIdentify;
    private static Activity activity;


    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 LocalCardIdentify.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param context
     */
    public static LocalCardIdentify init(Activity context) {
        LocalCardIdentify.activity = context;
        //初始化第三方库  该库后期可替换成其他
        if (localCardIdentify == null) {
            synchronized (LocalCardIdentify.class) {
                if (localCardIdentify == null) {
                    localCardIdentify = new LocalCardIdentify();
                }
            }
        }
        return localCardIdentify;
    }

    /**
     * 开启识别身份证正面
     */
    public void startIDCardEnum() {
        testInitialize();
        if (isInitPermission()) {
            OcrCameraActivity.open(activity, IDCardEnum.FaceEmblem, REQUEST_CODE);
        }
    }

    /**
     * 开启识别身份证反面
     */
    public void startNationalEmblem() {
        testInitialize();
        if (isInitPermission()) {

        OcrCameraActivity.open(activity, IDCardEnum.NationalEmblem, REQUEST_CODE);
        }
    }

    /**
     * 初始化权限事件
     */
    private boolean isInitPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtil.checkPermission(activity);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
            return true;
        } else {
            //申请权限
            ActivityCompat.requestPermissions(activity, permissions, 100);
            return false;
        }
    }
}
