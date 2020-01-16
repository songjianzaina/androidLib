package com.insworks.lib_net;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_net.net.model.ApiInfo;
import com.insworks.lib_net.net.model.SkinTestResult;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;

import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetTestActivity extends Activity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        dialog.setMessage("正在下载...");
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        dialog.setTitle("下载文件");
        dialog.setMax(100);

        //测试代码  查询天津天气  该接口可用
        EasyHttp.get("weather/city/101030100")
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Toast.makeText(NetTestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(NetTestActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    //------------------------------以下代码为EasyHttp的使用范例


    /**
     * post请求
     */
    public void post(String url) {
        EasyHttp.post("v1/app/chairdressing/news/favorite")
                .params("newsId", "552")
                .accessToken(true)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(NetApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * post提交Object
     */
    public void onPostObject(View view) {
        ApiInfo apiInfo = new ApiInfo();
        ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
        apiInfoBean.setApiKey("12345");
        apiInfoBean.setApiName("zhou-you");
        apiInfo.setApiInfo(apiInfoBean);
        EasyHttp.post("client/shipper/getCarType")
                .baseUrl("http://WuXiaolong.me/")
                //如果是body的方式提交object，必须要加GsonConverterFactory.create()
                //他的本质就是把object转成json给到服务器，所以必须要加Gson Converter
                //切记！切记！切记！  本例可能地址不对只做演示用
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(apiInfo)//这种方式会自己把对象转成json提交给服务器
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage() + "  " + e.getCode());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * post提交json
     */
    public void onPostJson(View view) {
        EasyHttp.post("api/")
                .baseUrl("http://xxxx.xx.xx/dlydbg/")
                .upJson("{\"\":\"\",\"\":\"\",\"\":\"\",\"swry_dm\":\"127053096\",\"version\":\"1.0.0\"}")
                //这里不想解析，简单只是为了做演示 直接返回String
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        showToast(s);
                    }
                });
    }

    /**
     * put请求
     */
    public void onPut(View view) {
        //http://api.youdui.org/api/v1/cart/1500996?count=4
        EasyHttp.put("http://api.youdui.org/api/v1/cart/1500996")
                .removeParam("appId")
                .params("count", "4")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * delete请求
     */
    public void onDelete(View view) {
        //测试请用自己的URL，这里为了安全去掉了地址
        //这里采用的是delete请求提交json的方式，可以选择其他需要的方式
        EasyHttp.delete("https://www.xxx.com/v1/user/Frined")
                .upJson("{\"uid\":\"10008\",\"token\":\"5b305fbeaa331\"}\n")
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

    /**
     * 基础回调
     */
    public void onCallBack(View view) {
        //支持CallBack<SkinTestResult>、CallBack<String>回调
        Disposable mDisposable = EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new CallBack<SkinTestResult>() {
                    @Override
                    public void onStart() {
                        showToast("开始请求");
                    }

                    @Override
                    public void onCompleted() {
                        showToast("请求完成");
                    }

                    @Override
                    public void onError(ApiException e) {
                        showToast("请求失败：" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast("请求成功：" + response.toString());
                    }
                });
    }

    /**
     * 简单回调
     *
     * @param view
     */
    public void onSimpleCallBack(View view) {
        //支持SimpleCallBack<SkinTestResult>、SimpleCallBack<String>回调
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(NetApplication.getInstance());
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    /**
     * 带有加载进度框的回调，支持是否可以取消对话框，取消对话框时可以自动取消网络请求，不需要再手动取消。
     */
    public void onProgressDialogCallBack(View view) {
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<SkinTestResult>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });
    }

    /**
     * 文件下载
     * @param view
     */
    public void onDownloadFile1(View view) {//下载回调是在异步里处理的
        EasyHttp.downLoad("http://apk.hiapk.com/web/api.do?qt=8051&id=723")
                //EasyHttp.downLoad("http://crfiles2.he1ju.com/0/925096f8-f720-4aa5-86ae-ef30548d2fdc.txt")
                .savePath(Environment.getExternalStorageDirectory().getPath()+"/test/")//默认在：/storage/emulated/0/Android/data/包名/files/1494647767055
                .saveName("custom_name")//默认名字是时间戳生成的
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        dialog.setProgress(progress);
                        if (done) {
                            dialog.setMessage("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        HttpLog.i("======"+Thread.currentThread().getName());
                        dialog.show();
                    }

                    @Override
                    public void onComplete(String path) {
                        showToast("文件保存路径：" + path);
                        dialog.dismiss();
                    }

                    @Override
                    public void onError(final ApiException e) {
                        HttpLog.i("======"+Thread.currentThread().getName());
                        showToast(e.getMessage());
                        dialog.dismiss();
                    }
                });
    }

    //文件上传
    public void onUploadFile(View view) throws Exception {
        final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                HttpLog.e(progress + "% ");
                dialog.setProgress(progress);
                dialog.setMessage(progress + "%");
                if (done) {//完成
                    dialog.setMessage("上传完整");
                }
            }
        };
        File file = new File(Environment.getExternalStorageDirectory().getPath()+"/1.jpg");
        EasyHttp.post("/v1/user/uploadAvatar")
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .params("avatar", file, file.getName(), listener)
                //.userParams("avatar", file, file.getName(),MediaType.parse("image/*"), listener)
                .accessToken(true)
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
    }

}
