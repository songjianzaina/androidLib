package com.insworks.ocrbaidu;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.insworks.lib_log.LogUtil;

import java.io.File;

/**
 * @ProjectName: OCR_identify
 * @Package: com.ocr
 * @ClassName: EasyIdentify
 * @Author: Song Jian
 * @CreateDate: 2019/6/5 16:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 16:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 身份证 银行卡 行驶证  驾驶证等识别 入口类
 */
public class EasyIdentify {

    private static final int REQUEST_BANK = 102;
    public static final String ak = "oH6tqEsBX2PSW2OViQyd2yYA";//需要自己配置 绑定包名
    public static final String sk = "A36f7UrseglvtH9jGP5u7bU9uGxIjZ31";//需要自己配置
    private static EasyIdentify easyIdentify;
    private static Activity activity;
    private OnBankIdentifyListener bankListener;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyIdentify.init() 初始化！");
    }

    public EasyIdentify() {
        //初始化
        initAccessTokenWithAkSk();
    }

    /**
     * 初始化
     *
     * @param activity
     */
    public static EasyIdentify init(Activity activity) {
        EasyIdentify.activity = activity;
        if (easyIdentify == null) {
            synchronized (EasyIdentify.class) {
                if (easyIdentify == null) {
                    easyIdentify = new EasyIdentify();
                }
            }
        }
        return easyIdentify;
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(
                new OnResultListener<AccessToken>() {
                    @Override
                    public void onResult(AccessToken result) {
                        // 本地自动识别需要初始化
                        initLicense();
                        Log.d("MainActivity", "onResult: " + result.toString());
                        LogUtil.d("lib_ocr", "初始化认证成功");
                    }

                    @Override
                    public void onError(OCRError error) {
                        error.printStackTrace();
                        Log.e("MainActivity", "onError: " + error.getMessage());
                        LogUtil.d("lib_ocr", "初始化认证失败,请检查 key");
                    }
                }, activity,
                // 需要自己配置 https://console.bce.baidu.com
                ak,
                // 需要自己配置 https://console.bce.baidu.com
                sk);
    }

    private void initLicense() {
        CameraNativeHelper.init(activity, OCR.getInstance().getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        final String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        LogUtil.d("lib_ocr", "本地质量控制初始化错误，错误原因： " + msg);
                    }
                });
    }

    /**
     * 开启银行卡扫描
     */
    public void openBankOCR(OnBankIdentifyListener listener) {
        this.bankListener = listener;
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(activity.getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_BANK_CARD);
        activity.startActivityForResult(intent, REQUEST_BANK);

        CameraActivity.setOnBankIdentifyListener(new CameraActivity.OnCarmareResultListener() {
            @Override
            public void onResult(String contentType) {
                //获取相机结果
                String filePath = FileUtil.getSaveFile(activity.getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_BANK_CARD.equals(contentType)) {
                        //从本地文件中识别 银行卡
                        recCreditCard(filePath);
                    }
                }
            }
        });
    }

    /**
     * 解析银行卡
     *
     * @param filePath 图片路径
     */
    private void recCreditCard(String filePath) {
        // 银行卡识别参数设置
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));

        // 调用银行卡识别服务
        OCR.getInstance().recognizeBankCard(param, new OnResultListener<BankCardResult>() {
            @Override
            public void onResult(BankCardResult result) {
                if (result != null) {

                    String type;
                    if (result.getBankCardType() == BankCardResult.BankCardType.Credit) {
                        type = "信用卡";
                    } else if (result.getBankCardType() == BankCardResult.BankCardType.Debit) {
                        type = "借记卡";
                    } else {
                        type = "不能识别";
                    }
                    if (bankListener != null) {
                        bankListener.onSuccess(!TextUtils.isEmpty(result.getBankCardNumber()) ? result.getBankCardNumber() : "", type, !TextUtils.isEmpty(result.getBankName()) ? result.getBankName() : "");
                    }
                }
            }

            @Override
            public void onError(OCRError error) {
                Toast.makeText(activity, "识别出错", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "onError: " + error.getMessage());
            }
        });
    }
}
