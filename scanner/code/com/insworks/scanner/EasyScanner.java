package com.insworks.scanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import android.view.View;

import com.inswork.lib_cloudbase.R;
import com.insworks.scanner.utils.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_scanner
 * @ClassName: EasyScanner
 * @Author: Song Jian
 * @CreateDate: 2019/5/23 10:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/23 10:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 扫描入口方法  其他模块使用该类进行扫描操作 采用链式调用
 */
public class EasyScanner {


    private static final int REQUEST_CODE = 010;
    private static Activity activity;
    private static SannerCallBack analyzeCallback;
    public static int REQUEST_IMAGE = 020;
    private static EasyScanner easyScanner;
    private static boolean isOpen = false;


    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先在全局BaseActivity中调用 EasyScanner.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static EasyScanner init(Activity activity) {
        EasyScanner.activity = activity;
        //初始化第三方库  该库后期可替换成其他
        if (easyScanner == null) {
            synchronized (EasyScanner.class) {
                if (easyScanner == null) {
                    ZXingLibrary.initDisplayOpinion(activity);
                    easyScanner = new EasyScanner();
                }
            }
        }
        return easyScanner;
    }

    /**
     * 进入默认的二维码扫描界面
     */
    public void startDefaultScanView(Activity activity) {
        testInitialize();
        Intent intent = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    /**
     * 初始化自定义的二维码扫描界面
     */
    static void initCloudScanView(final FragmentActivity activity) {
        activity.setContentView(R.layout.fragment_cloud_scanner);
        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.lib_scanner_my_camera);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
        // 返回按钮
        activity.findViewById(R.id.top_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        // 开启闪光灯
        activity.findViewById(R.id.top_mask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开或者关闭闪光灯
                if (!isOpen) {
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }

            }
        });
        // 打开图库
        activity.findViewById(R.id.top_openpicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开图库进行解析
                openGallery(activity);
            }
        });
    }

    /**
     * 打开图库
     *
     * @param activity
     * @param
     */
    private static void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_IMAGE);
    }

    /**
     * 生成带logo的二维码
     *
     * @param
     * @param textContent 二维码内容
     * @param logoRes     logo
     */
    public static Bitmap createQrImage(String textContent, int logoRes) {
        return CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(activity.getResources(), logoRes));
    }

    /**
     * 生成带logo的二维码
     *
     * @param
     * @param textContent 二维码内容
     * @param logoRes     logo
     */
    public Bitmap createQrImage(String textContent, int width, int height, int logoRes) {
        return CodeUtils.createImage(textContent, width, height, BitmapFactory.decodeResource(activity.getResources(), logoRes));
    }

    /**
     * 生成不带logo的二维码
     *
     * @param
     * @param textContent 二维码内容
     */
    public static Bitmap createQrImage(String textContent, int width, int height) {
        return CodeUtils.createImage(textContent, width, width, null);
    }


    /**
     * 生成不带logo的二维码
     *
     * @param
     * @param textContent 二维码内容
     */
    public static Bitmap createQrImage(String textContent) {
        return CodeUtils.createImage(textContent, 400, 400, null);
    }


//    /**
//     * 打开图库 供外部调用
//     *
//     * @param activity
//     * @param
//     */
//    public static void openGallery(Activity activity,SannerCallBack analyzeCallback) {
//        EasyScanner.analyzeCallback = analyzeCallback;
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        activity.startActivityForResult(intent, REQUEST_IMAGE);
//    }

    /**
     * 进入自定义的二维码扫描界面
     */
    public void startCloudScanView(SannerCallBack analyzeCallback) {
        testInitialize();
        EasyScanner.analyzeCallback = analyzeCallback;
        Intent intent = new Intent(activity, CloudScannerActivity.class);
//        activity.startActivityForResult(intent, REQUEST_CODE);
        activity.startActivity(intent);

    }

    /**
     * 获取观察者对象 也就是回调实例
     */
    static SannerCallBack getScannerCallback() {
        testInitialize();
        if (EasyScanner.analyzeCallback == null) {
            throw new ExceptionInInitializerError("analyzeCallback不能为空, 可能是没有传入analyzeCallback 请在调用处检查一下");
        }
        return analyzeCallback;
    }


    /**
     * 从图库获取图片进行解析
     */
    public void pickScan() {
        new EasyScanner();
    }


    /**
     * 二维码解析解析
     */
    public void pickFilePath(String path, CodeUtils.AnalyzeCallback callback) {
        CodeUtils.analyzeBitmap(path, callback);
    }

    /**
     * 二维码解析解析
     */
    public void pickFile(File file, CodeUtils.AnalyzeCallback callback) {
        CodeUtils.analyzeBitmap(file.getPath(), callback);
    }

    /**
     * 二维码解析解析
     */
    public void pickUri(Uri uri, CodeUtils.AnalyzeCallback callback) {
        CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(activity, uri), callback);
    }

}
