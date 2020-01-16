package com.insworks.megviface;

import android.app.Activity;
import android.widget.Toast;

import com.inswork.lib_cloudbase.event.BusEvent;
import com.inswork.lib_cloudbase.event.EventCode;
import com.inswork.lib_cloudbase.utils.NetworkUtil;
import com.insworks.lib_datas.utils.ActivityManager;
import com.insworks.megviface.bean.FaceResultBean;
import com.insworks.megviface.bean.FaceSignBean;
import com.insworks.megviface.faceid.HttpRequestCallBack;
import com.insworks.megviface.faceid.HttpRequestManager;
import com.insworks.megviface.net.UserApi;
import com.insworks.lib_loading.LoadingUtil;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.interceptor.CloudCallBack;
import com.megvii.meglive_sdk.sdk.listener.FaceIdDetectListener;
import com.megvii.meglive_sdk.sdk.listener.FaceIdInitListener;
import com.megvii.meglive_sdk.sdk.manager.FaceIdManager;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.insworks.megviface.faceid.GenerateSign.appSign;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_face
 * @ClassName: FeceUtil
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 15:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 15:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 活体认证工具类
 */
public class FeceUtil {

    private static FeceUtil mFeceUtil;
    private static Activity activity;
    private static FaceResultListener mListener;
    private String name;
    private String idCardNumber;
    private static String bizToken;
    protected static String mSign;
    private FaceSignBean faceSignBean;

    private void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 FeceUtil.init() 初始化！");
    }

    /**
     * 初始化活体识别库
     *
     * @param activity
     */
    public static FeceUtil init(Activity activity) {
        FeceUtil.activity = activity;
        //显示加载对话框
        LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).show();
        if (mFeceUtil == null) {
            synchronized (FeceUtil.class) {
                if (mFeceUtil == null) {
                    //设置 初始化监听
                    FaceIdManager.getInstance(activity).setFaceIdInitListener(mInitListener);
                    FaceIdManager.getInstance(activity).setFaceIdDetectListener(mDetectListener);
                    mFeceUtil = new FeceUtil();
                }
            }
        }

        return mFeceUtil;
    }


    /**
     * 开始检测
     *
     * @param
     */
    public FeceUtil setDetectInfo(String name, String idCardNumber) {
        this.name = name;
        this.idCardNumber = idCardNumber;
        testInitialize();
        return this;
    }


    /**
     * 设置活体检测监听器
     *
     * @param
     */
    public FeceUtil setFaceResultListener(FaceResultListener listener) {
        mListener = listener;
        return this;
    }


    /**
     * 设置bizToken
     *
     * @param
     */
    public FeceUtil setBizToken(String biztoken) {
        bizToken = bizToken;
        return this;
    }

    /**
     * 设置后台传过来的数据
     *
     * @param
     */
    public FeceUtil setFaceSignBean(FaceSignBean faceSignBean) {
        this.faceSignBean = faceSignBean;
        return this;
    }


    /**
     * 启动监听
     *
     * @param
     */
    public void start() {
        testListener();
        //权限检测
        testPermission();
    }

    private void testListener() {
        if (mListener == null) {
            throw new ExceptionInInitializerError("活体检测缺少回调监听 请先调用 FeceUtil.setFaceResultListener！");
        }
    }

    private void sendRequest() {
        //检查网络连接 没有网络的话没法初始化
        if (!NetworkUtil.isConnected(activity)) {
            Toast.makeText(activity, "网络连接异常", Toast.LENGTH_LONG).show();
            return;
        }
        final String apiKey = "OlyqVqLg_LUBAa-0P1bPZ86gbY2c1ozR";
        String secret = "r0nyDM37J4DEJcXOkZLGSh_W1DOYnE_z";
        long currtTime = System.currentTimeMillis() / 1000;
        long expireTime = (System.currentTimeMillis() + 60 * 60 * 100) / 1000;
        mSign = appSign(apiKey, secret, currtTime, expireTime).replaceAll("[\\s*\t\n\r]", "");
        String mSignVersion = "hmac_sha1";
        String livenessType = "meglive";
        int comparisonType = 1;



        getBizTokenFromNet(faceSignBean);

    }

    private void getBizTokenFromNet(FaceSignBean faceSignBean) {

        HttpRequestManager.getInstance().getBizToken(activity, HttpRequestManager.URL_GET_BIZTOKEN, faceSignBean.getSign(), "hmac_sha1", "meglive", 1, name, idCardNumber, faceSignBean.getUuid(), null, faceSignBean.getBizno(), new HttpRequestCallBack() {

            @Override
            public void onSuccess(String responseBody) {
                LogUtil.json("活体检测返回bizToken数据: ", responseBody);
                try {
                    JSONObject json = new JSONObject(responseBody);
                    bizToken = json.optString("biz_token");
                    //biz_token传送
                    BusEvent.send(EventCode.CARD_BIZTOKEN,bizToken);
                    FaceIdManager.getInstance(activity).setLanguage(activity, "zh");
                    FaceIdManager.getInstance(activity).setHost(activity, "https://openapi.faceid.com");
                    FaceIdManager.getInstance(activity).init(bizToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {
                LogUtil.d("获取BizToken失败: " + new String(responseBody));
                Toast.makeText(activity, "服务异常，请稍后再试", Toast.LENGTH_LONG).show();
            }

        });
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
                        sendRequest();
                    }
                })
                .start();
    }


    private static FaceIdInitListener mInitListener = new FaceIdInitListener() {
        @Override
        public void onSuccess() {
            LogUtil.d("初始化活体检测成功 准备活体检测");
//            LoadingUtil.init(activity).dismiss();
            FaceIdManager.getInstance(activity).startDetect();
        }

        @Override
        public void onFailed(int code, String msg) {
            LogUtil.d("初始化活体检测失败: " + msg);
        }
    };
    /**
     * 这种多层回调方式可以让宿主保持对本库 implementation 的引用方式 达到依赖隔离的目的
     */
    private static FaceIdDetectListener mDetectListener = new FaceIdDetectListener() {
        @Override
        public void onSuccess(final int code, final String msg) {
            LogUtil.d("活体检测联网成功:  code="+code+" =msg= " + msg);
            if (51000 == code) {
                mListener.onSuccess(code, msg);
            } else {
                mListener.onFailed(code,msg);
            }
//            //获取检测结果
//            getDectedResult2(code);

        }

        @Override
        public void onFailed(int code, String msg) {
            LogUtil.d("活体检测失败" + msg);
            mListener.onFailed(code, msg);
        }
    };

    private static void getDectedResult(final int code) {
        //显示加载对话框
        LoadingUtil.init(activity).show();
        UserApi.getFaceReuslt(mSign, "hmac_sha1", bizToken, new CloudCallBack<FaceResultBean>() {
            @Override
            public void onSuccess(FaceResultBean faceResultBean) {
                //获取 base64编码的活体照片
                String imageStr = faceResultBean.getImages().getImage_best();
                mListener.onSuccess(code, imageStr);
                //关闭加载对话框
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();

            }
        });
    }

    private static void getDectedResult2(final int code) {
        LoadingUtil.init(activity).show();
        //获取检测结果
        HttpRequestManager.getInstance().getResult(activity, HttpRequestManager.URL_GET_RESULT, mSign, "hmac_sha1", bizToken, new HttpRequestCallBack() {
            @Override
            public void onSuccess(String responseBody) {
                //   "images": {
                //        "image_best": "XXXX"   # base64编码的活体照片
                //    },
                JSONObject json = null;
                try {
                    json = new JSONObject(responseBody);

                    String imageJson = json.optString("images");
                    //获取 base64编码的活体照片
                    String imageStr = new JSONObject(imageJson).optString("image_best");
                    mListener.onSuccess(code, imageStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //关闭加载对话框
                LoadingUtil.init(activity).dismiss();
            }

            @Override
            public void onFailure(int statusCode, byte[] responseBody) {

            }
        });
    }
}
