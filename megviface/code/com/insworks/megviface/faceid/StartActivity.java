package com.insworks.megviface.faceid;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;
import com.insworks.megviface.FaceResultListener;
import com.insworks.megviface.FeceUtil;
import com.megvii.meglive_sdk.sdk.listener.FaceIdDetectListener;
import com.megvii.meglive_sdk.sdk.listener.FaceIdInitListener;
import com.megvii.meglive_sdk.sdk.manager.FaceIdManager;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.M;
import static com.insworks.megviface.faceid.GenerateSign.appSign;


public class StartActivity extends Activity {
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE = 101;

    private Button btnGetToken;

    private ProgressDialog mProgressDialog;


    private String mName;
    private String mNum;
    private String mSign;
    private String mSignVersion;
    private String bizToken;
    private String livenessType;
    private int comparisonType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_face);
        FaceIdManager.getInstance(this).setFaceIdInitListener(mInitListener);
        FaceIdManager.getInstance(this).setFaceIdDetectListener(mDetectListener);

        initUI();
    }

    private void initUI() {
        btnGetToken = (Button) findViewById(R.id.button);
//        btnGetToken.setOnClickListener(mOnClickListener);
        btnGetToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeceUtil.init(StartActivity.this).setDetectInfo("张三", "330721197403162417").setFaceResultListener(new FaceResultListener() {
                    @Override
                    public void onSuccess(int code, String msg) {

                    }

                    @Override
                    public void onFailed(int code, String msg) {

                    }
                }).start();
            }
        });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            String apiKey = "OlyqVqLg_LUBAa-0P1bPZ86gbY2c1ozR";
            String secret = "r0nyDM37J4DEJcXOkZLGSh_W1DOYnE_z";
            long currtTime = System.currentTimeMillis() / 1000;
            long expireTime = (System.currentTimeMillis() + 60 * 60 * 100) / 1000;
            mSign = appSign(apiKey, secret, currtTime, expireTime).replaceAll("[\\s*\t\n\r]", "");

            mSignVersion = "hmac_sha1";
            mName = "张三";
            mNum = "330721197403162417";
            livenessType = "meglive";
            comparisonType = 1;

            HttpRequestManager.getInstance().getBizToken(StartActivity.this, HttpRequestManager.URL_GET_BIZTOKEN, mSign, mSignVersion, livenessType, comparisonType, mName, mNum, "", null,"" ,new HttpRequestCallBack() {

                @Override
                public void onSuccess(String responseBody) {
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        bizToken = json.optString("biz_token");
                        requestCameraPerm();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, byte[] responseBody) {
                    Toast.makeText(StartActivity.this, "服务异常，请稍后再试", Toast.LENGTH_LONG).show();
                }

            });
        }
    };

    private void requestCameraPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSION_REQUEST_CODE);
            } else {
                requestWriteExternalPerm();
            }
        } else {
            enterNextPage();
        }
    }

    private void requestWriteExternalPerm() {
        if (android.os.Build.VERSION.SDK_INT >= M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //进行权限请求
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE);
            } else {
                enterNextPage();
            }
        } else {
            enterNextPage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            } else {
                requestWriteExternalPerm();
            }
        } else if (requestCode == EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.length < 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            } else {
                enterNextPage();
            }
        }
    }

    private void enterNextPage() {
        init();
    }

    private void init() {
        FaceIdManager.getInstance(StartActivity.this).setLanguage(StartActivity.this, "zh");
        FaceIdManager.getInstance(StartActivity.this).setHost(StartActivity.this, "https://openapi.faceid.com");
        FaceIdManager.getInstance(StartActivity.this).init(bizToken);
    }

    private FaceIdInitListener mInitListener = new FaceIdInitListener() {
        @Override
        public void onSuccess() {
            FaceIdManager.getInstance(StartActivity.this).startDetect();
        }

        @Override
        public void onFailed(int code, String msg) {
        }
    };

    private FaceIdDetectListener mDetectListener = new FaceIdDetectListener() {
        @Override
        public void onSuccess(int code, String msg) {
        }

        @Override
        public void onFailed(int code, String msg) {
        }
    };
}
