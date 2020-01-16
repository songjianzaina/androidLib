package com.insworks.idcard_identify;

import android.app.Activity;
import android.widget.Toast;

import com.inswork.lib_cloudbase.event.BusEvent;
import com.inswork.lib_cloudbase.event.EventCode;
import com.inswork.lib_cloudbase.utils.NetworkUtil;
import com.insworks.idcard_identify.megvii.GenerateSign;
import com.insworks.lib_loading.LoadingUtil;
import com.insworks.lib_log.LogUtil;
import com.insworks.megviface.bean.FaceSignBean;
import com.megvii.faceidiol.sdk.manager.IDCardManager;
import com.megvii.faceidiol.sdk.manager.UserDetectConfig;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.idcard_identify
 * @ClassName: IDCardIdentifyUtil
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 10:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 10:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 旷视 身份证核验 入口类
 */
public class IDCardIdentifyUtil {

    public static final int PORTRAIT = 0;
    public static final int LANDSCAPE = 1;
    public static final int MODE_DOUBLE = 0;
    public static final int MODE_FRONT = 1;
    public static final int MODE_BACK = 2;
    private static IDCardIdentifyUtil iDCardIdentify;
    private static Activity activity;
    private FaceSignBean faceSignBean;

    private void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 IDCardIdentifyUtil.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static IDCardIdentifyUtil init(Activity activity) {
        IDCardIdentifyUtil.activity = activity;
        if (iDCardIdentify == null) {
            synchronized (IDCardIdentifyUtil.class) {
                if (iDCardIdentify == null) {
                    iDCardIdentify = new IDCardIdentifyUtil();
                }
            }
        }
        return iDCardIdentify;
    }

    /**
     * 设置后台传过来的数据
     *
     * @param
     */
    public IDCardIdentifyUtil setFaceSignBean(FaceSignBean faceSignBean) {
        this.faceSignBean = faceSignBean;
        return this;
    }


    /**
     * 开始检测
     *
     * @param
     */
    public IDCardIdentifyUtil startDetect(final int screenDirection, final int mode, final IdCardDetectResultListener listener) {
        testInitialize();
        //检查网络连接 没有网络的话没法初始化
        if (!NetworkUtil.isConnected(activity)) {
            Toast.makeText(activity, "网络连接异常", Toast.LENGTH_LONG).show();
            return this;
        }
        //检测权限
        testPermission(screenDirection, mode, listener);



        return this;
    }

    private void nextGo(final int screenDirection, final int mode, final IdCardDetectResultListener listener) {
        // 显示加载对话框
        LoadingUtil.init(activity).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String apiKey = "OlyqVqLg_LUBAa-0P1bPZ86gbY2c1ozR";
                String secret = "r0nyDM37J4DEJcXOkZLGSh_W1DOYnE_z";
                long currtTime = System.currentTimeMillis() / 1000;
                long expireTime = System.currentTimeMillis() / 1000 + 60 * 60 * 24;
                String sign = GenerateSign.appSign(apiKey, secret, currtTime, expireTime);
                UserDetectConfig config = new UserDetectConfig();
                //默认横屏模式
                config.setScreenDirection(LANDSCAPE);
                //双面模式
                config.setCaptureImage(MODE_DOUBLE);
                if (screenDirection == PORTRAIT ) {
                    //竖屏
                    config.setScreenDirection(PORTRAIT);
                }

               if (mode==MODE_FRONT) {
                    //人像面模式
                    config.setCaptureImage(MODE_FRONT);
                } else if(mode==MODE_BACK){
                    //国徽面模式
                    config.setCaptureImage(MODE_BACK);
                }
                IDCardManager.getInstance().init(activity, faceSignBean.getSign(), "hmac_sha1", config, new IDCardManager.InitCallBack() {
                    @Override
                    public void initSuccess(String bizToken) {
                        LogUtil.d("身份证核验初始化成功: "+bizToken);
                        BusEvent.send(EventCode.CARD_BIZTOKEN,bizToken);
                        IDCardManager.getInstance().setIdCardDetectListener(listener);
                        IDCardManager.getInstance().startDetect(activity, bizToken, "");
                        LoadingUtil.init(activity).dismiss();
                    }

                    @Override
                    public void initFailed(int resultCode, String resultMessage) {
                        LogUtil.d("身份证核验初始化失败: "+resultMessage);
                        Toast.makeText(activity, "身份证核验初始化失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    /**
     * 权限检测
     * @param screenDirection
     * @param mode
     * @param listener
     */
    private void testPermission(final int screenDirection, final int mode, final IdCardDetectResultListener listener) {
        AndPermission.with(activity)
                .permission(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA})
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        nextGo(screenDirection, mode, listener);
                    }
                })
                .start();
    }

}
