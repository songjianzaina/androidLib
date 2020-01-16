package com.insworks.idcard_identify.megvii;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;
import com.insworks.idcard_identify.IDCardIdentifyUtil;
import com.insworks.idcard_identify.IdCardDetectResultListener;
import com.megvii.faceidiol.sdk.manager.IDCardDetectListener;
import com.megvii.faceidiol.sdk.manager.IDCardManager;
import com.megvii.faceidiol.sdk.manager.IDCardResult;
import com.megvii.faceidiol.sdk.manager.UserDetectConfig;

import static android.os.Build.VERSION_CODES.M;

/**
 * 使用说明
 * 使用前请商家注册自己的apiKey和secret
 * apikey和secret建议放在自己的服务端确保自己的apikey和secret安全
 */
public class DemoActivity extends Activity implements View.OnClickListener, IDCardDetectListener {
    private TextView build_version, result_code, result_msg;
    private RadioButton rb_v, rb_h, rb_double, rb_front, rb_back;
    public static byte[] faceImg = null;
    public static byte[] portraitImg = null;
    public static byte[] emblemImg = null;
    private UserDetectConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findViewById(R.id.loading_all).setOnClickListener(this);
        build_version = (TextView) findViewById(R.id.build_version);
        build_version.setText("构建版本号：" + IDCardManager.getInstance().getBuildInfo());
        result_code = (TextView) findViewById(R.id.tv_result_code);
        result_msg = (TextView) findViewById(R.id.tv_result_msg);

        rb_v = (RadioButton) findViewById(R.id.rb_v);
        rb_h = (RadioButton) findViewById(R.id.rb_h);
        rb_double = (RadioButton) findViewById(R.id.rb_double);
        rb_front = (RadioButton) findViewById(R.id.rb_front);
        rb_back = (RadioButton) findViewById(R.id.rb_back);

        config = new UserDetectConfig();
        requestCameraPerm();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loading_all) {
            //                beginDetect();
            IDCardIdentifyUtil.init(this).startDetect(IDCardIdentifyUtil.LANDSCAPE, IDCardIdentifyUtil.MODE_DOUBLE, new IdCardDetectResultListener() {
                @Override
                protected void onIdCardDetectResult(IDCardResult result) {
                    Toast.makeText(DemoActivity.this, "code:" + result.getResultCode() + ",message:" + result.getResultMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    private void requestCameraPerm() {
        if (Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
    }

    private void beginDetect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String apiKey = "OlyqVqLg_LUBAa-0P1bPZ86gbY2c1ozR";
                String secret = "r0nyDM37J4DEJcXOkZLGSh_W1DOYnE_z";
                long currtTime = System.currentTimeMillis() / 1000;
                long expireTime = System.currentTimeMillis() / 1000 + 60 * 60 * 24;
                String sign = GenerateSign.appSign(apiKey, secret, currtTime, expireTime);

                if (rb_v.isChecked()) {
                    config.setScreenDirection(0);
                } else {
                    config.setScreenDirection(1);
                }

                if (rb_double.isChecked()) {
                    config.setCaptureImage(0);
                } else if (rb_front.isChecked()) {
                    config.setCaptureImage(1);
                } else {
                    config.setCaptureImage(2);
                }
                IDCardManager.getInstance().init(DemoActivity.this, sign, "hmac_sha1", config, new IDCardManager.InitCallBack() {
                    @Override
                    public void initSuccess(String bizToken) {
                        IDCardManager.getInstance().setIdCardDetectListener(DemoActivity.this);
                        IDCardManager.getInstance().startDetect(DemoActivity.this, bizToken, "");
                    }

                    @Override
                    public void initFailed(int resultCode, String resultMessage) {

                    }
                });
            }
        }).start();


    }

    @Override
    public void onIdCardDetectFinish(IDCardResult result) {
        Toast.makeText(this, "code:" + result.getResultCode() + ",message:" + result.getResultMessage(), Toast.LENGTH_LONG).show();
        faceImg = null;
        portraitImg = null;
        emblemImg = null;
        result_code.setText("resultCode:" + result.getResultCode());
        result_msg.setText("resultMsg:" + result.getResultMessage());
        result_code.setVisibility(View.VISIBLE);
        result_msg.setVisibility(View.VISIBLE);
        if (result.getResultCode() == 1001 || result.getResultCode() == 1002) {
            Intent intent = new Intent(this, ResultActivity.class);
            if (config.getCaptureImage() == 0 || config.getCaptureImage() == 1) {//双面或人像面结果
                //人像面
                faceImg = result.getIdCardInfo().getImageFrontside();
                //头像
                portraitImg = result.getIdCardInfo().getImagePortrait();
                //姓名
                intent.putExtra("name", result.getIdCardInfo().getName().getText());
                //身份证号
                intent.putExtra("idcardNum", result.getIdCardInfo().getIdcardNumber().getText());
            }
            if (config.getCaptureImage() == 0 || config.getCaptureImage() == 2) {
                //国徽面
                emblemImg = result.getIdCardInfo().getImageBackside();
                //有效期
                intent.putExtra("dateBegin", result.getIdCardInfo().getValidDateStart().getText());
                intent.putExtra("dateEnd", result.getIdCardInfo().getValidDateEnd().getText());
            }
            startActivity(intent);
        } else {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        faceImg = null;
        portraitImg = null;
        emblemImg = null;
    }
}
