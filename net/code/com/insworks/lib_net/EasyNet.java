package com.insworks.lib_net;


import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.insworks.lib_datas.bean.common.ResponseBean;
import com.insworks.lib_datas.utils.ActivityManager;
import com.insworks.lib_datas.utils.JsonUtil;
import com.insworks.lib_loading.LoadingUtil;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.EasyNetConfigure;
import com.insworks.lib_net.net.interceptor.CloudCallBack;
import com.insworks.lib_net.net.interceptor.CustomRequestInterceptor;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.DownloadRequest;
import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;

public class EasyNet {
    private static final String POST = "post";
    private static final String GET = "get";
    private static final String DOWNLOAD = "downLoad";
    private static final String UPLOAD = "upload";
    private static String requesType = null;
    private volatile static EasyNet singleton = null;
    protected PostRequest postRequest = null;
    protected GetRequest getRequest = null;
    protected DownloadRequest downloadRequest = null;
    protected ProgressDialog dialog;
    public static boolean isOldJson = false;


    private EasyNet(String type, String url) {
        //判断用户是什么请求
        requesType = type;
        if (type.equals(POST)) {
            //post请求
            postRequest = EasyHttp.post(url)
                    .accessToken(false);//请求的时候携带token
        } else if (type.equals(GET)) {
            //get请求
            getRequest = EasyHttp.get(url)
                    .accessToken(false);
        } else if (type.equals(DOWNLOAD)) {
            //文件下载
            downloadRequest = EasyHttp.downLoad(url)
                    .accessToken(true);
        } else if (type.equals(UPLOAD)) {
            //文件上传
            postRequest = EasyHttp.post(url)
                    .accessToken(true);

        } else {

        }
        //默认发送手机号
        isAccessPhoneAndVoucher(false);
        // TODO: 2019/5/13
        //创建下载对话框
        initProgressDialog();

        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        isOldJson = false;
    }

    private void initProgressDialog() {

        dialog = new ProgressDialog(ActivityManager.getInstance().getCurrentActivity());
        dialog.setTitle("加载中");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        dialog.setMessage("正在下载...");
    }

    public EasyNet baseUrl(String url) {
        if (requesType.equals(POST)) {
            postRequest.baseUrl(url);
        } else if (requesType.equals(GET)) {
            getRequest.baseUrl(url);
        } else if (requesType.equals(DOWNLOAD)) {
            downloadRequest.baseUrl(url);
        } else if (requesType.equals(UPLOAD)) {
            postRequest.baseUrl(url);
        }
        return this;
    }

    public EasyNet isAccessPhoneAndVoucher(Boolean isAccess) {
        CustomRequestInterceptor.isAccessPhoneAndVoucher = isAccess;
        return this;
    }

    public static void init(Application app) {
        EasyNetConfigure.getInstance(app);
    }

    /**
     * 不允许外部创建对象 只能通过调用post或者get 方法 避免异常
     */
    private EasyNet() {

    }

//    public static EasyNet getInstance() {
//        if (singleton ==  null) {
//            synchronized (EasyNet.class) {
//                if (singleton == null) {
//                    singleton = new EasyNet();
//                }
//            }
//        }
//        return singleton;
//    }


    /**
     * post请求
     */
    public static EasyNet post(String url) {
        return new EasyNet(POST, url);

    }

    /**
     * post 对象请求
     */
    public static QtpayNet postObject(String service) {

        return new QtpayNet(POST, service);
    }

    /**
     * get请求
     */
    public static EasyNet get(String url) {
        return new EasyNet(GET, url);

    }

    /**
     * 文件下载
     */
    public static EasyNet downLoad(String url) {
        return new EasyNet(DOWNLOAD, url);

    }

    /**
     * 文件上传
     */
    public static EasyNet upload(String url) {
        return new EasyNet(UPLOAD, url);

    }

    /**
     * 文件下载路径(可选)  默认在：/storage/emulated/0/Android/data/包名/files/1494647767055
     */
    public EasyNet setFilePath(String savePath) {
        if (downloadRequest != null) {
            downloadRequest.savePath(savePath);
        }
        return this;

    }

    /**
     * 文件下载路径 (可选)  默认名字是时间戳生成的
     */
    public EasyNet setFileName(String fileName) {
        if (downloadRequest != null) {
            downloadRequest.saveName(fileName);
        }
        return this;

    }

    public EasyNet params(String key, String value) {
        if (requesType.equals(POST) || requesType.equals(UPLOAD)) {
            postRequest.params(key, value);
        } else if (requesType.equals(DOWNLOAD)) {
            downloadRequest.params(key, value);
        } else {
            getRequest.params(key, value);
        }
        return this;
    }

    /**
     * 文件上传调用该参数
     *
     * @param key
     * @param file
     * @param filename
     * @return
     */
    public EasyNet params(String key, File file, String filename) {
        if (requesType.equals(UPLOAD) && postRequest != null) {
            postRequest.params(key, file, filename, uploadListener);
        }
        return this;
    }

    public EasyNet execute(final CloudCallBack cloudCallBack) {
        if (requesType.equals(POST)) {
            postExecute(cloudCallBack);
        } else if (requesType.equals(GET)) {
            getExecute(cloudCallBack);
        } else if (requesType.equals(DOWNLOAD)) {
            downloadExecute(cloudCallBack);
        } else if (requesType.equals(UPLOAD)) {
            uploadExecloadute(cloudCallBack);
        }
        return this;
    }

    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(ActivityManager.getInstance().getCurrentActivity());
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };

    /**
     * 执行上传
     *
     * @param cloudCallBack
     */
    private void uploadExecloadute(CloudCallBack cloudCallBack) {
        postRequest.execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);

                LogUtil.e("上传失败 ：", e.getMessage());
            }

            @Override
            public void onSuccess(String response) {
                Log.e("EasyNet", "上传成功  response： " + response);
            }
        });
    }

    /**
     * 执行下载
     *
     * @param cloudCallBack
     */
    private void downloadExecute(final CloudCallBack cloudCallBack) {
        downloadRequest.execute(new DownloadProgressCallBack<String>() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                LogUtil.e(progress + "% ");
//                dialog.setProgress(progress);
//                if (done) {
//                    dialog.setMessage("下载完成");
//                }
                cloudCallBack.update(progress, done);
            }

            @Override
            public void onStart() {
                cloudCallBack.onStart();
//                dialog.show();
            }

            @Override
            public void onComplete(String path) {
                LogUtil.d("文件保存路径：" + path);
                cloudCallBack.onCompleted(path);
//                dialog.dismiss();
            }

            @Override
            public void onError(final ApiException e) {
                LogUtil.e(e, "Download失败 错误码:" + e.getCode() + "  错误详情:" + e.getMessage());
                cloudCallBack.onError(e, e.getDisplayMessage());
//                showToast(e.getMessage());
//                dialog.dismiss();
            }
        });

    }


    private void getExecute(final CloudCallBack cloudCallBack) {
        getRequest.execute(new SimpleCallBack<String>() {
            @Override
            public void onStart() {
                cloudCallBack.onStart();
                //显示加载对话框
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).show();

            }


            @Override
            public void onCompleted() {
                cloudCallBack.onCompleted(null);
                //关闭加载对话框
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
            }


            @Override
            public void onError(ApiException e) {
                cloudCallBack.onError(e, null);
                LogUtil.e("get请求失败: 错误码:" + e.getCode() + "   错误详情:" + e.getMessage());
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
                Toast.makeText(ActivityManager.getInstance().getCurrentActivity(), "请求错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {

                LogUtil.json("get请求成功  Response: ", response);
                try {
                    cloudCallBack.onSuccess(JsonUtil.jsonToBean(response, cloudCallBack.getType()));
                } catch (Exception e) {
                    LogUtil.e(e, "json解析异常");
                    cloudCallBack.onSuccess(response);

                }

            }
        });

    }

    private void postExecute(final CloudCallBack cloudCallBack) {
        postRequest.execute(new SimpleCallBack<String>() {
            @Override
            public void onStart() {
                cloudCallBack.onStart();
                //显示加载对话框
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).show();

            }


            @Override
            public void onCompleted() {
                cloudCallBack.onCompleted(null);
                //关闭加载对话框
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
            }


            @Override
            public void onError(ApiException e) {
                cloudCallBack.onError(e, null);
                LogUtil.e(e, "Post请求失败 错误码:" + e.getCode() + "  错误详情:" + e.getMessage());
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
                Toast.makeText(ActivityManager.getInstance().getCurrentActivity(), "请求错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                if (TextUtils.isEmpty(response)) {
                    Toast.makeText(ActivityManager.getInstance().getCurrentActivity(), "未请求到数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                LogUtil.json("Post请求成功  Response: ", response);
                //过滤 如果CloudCallBack<T> 的泛型是 ResponseBean的子类 就解析 如果不是就返回String
                //用户有可能传的泛型是String 这种情况下就没必要进行json的转换
                if (cloudCallBack.getType() instanceof ResponseBean) {
                    //网络请求的实例必须继承ResponseBean 否则无法解析 直接返回String
                    cloudCallBack.onSuccess(JsonUtil.jsonToBean(response, cloudCallBack.getType()));
                } else {
                    cloudCallBack.onSuccess(response);

                }

            }
        });
    }

    final UIProgressResponseCallBack uploadListener = new UIProgressResponseCallBack() {
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


}
