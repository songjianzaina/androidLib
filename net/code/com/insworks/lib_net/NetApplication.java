package com.insworks.lib_net;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.EasyNetConfigure;
import com.insworks.lib_net.net.constant.AppConstant;
import com.insworks.lib_net.net.interceptor.CustomRequestInterceptor;
import com.insworks.lib_net.net.utils.SystemInfoUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.utils.HttpLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class NetApplication extends Application {

    private static NetApplication instance;
    private String runningEnvironment;
    private boolean isPrintLog = true;

    /**
     * 网络模块初始化工作
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        init();
    }

//    private NetApplication() {
//    }
//
//    private static class SingleTonHoler {
//        private static NetApplication INSTANCE = new NetApplication();
//    }
//
//    public static NetApplication getInstances() {
//        return SingleTonHoler.INSTANCE;
//    }

    public static NetApplication getInstance() {
        return instance;
    }

//    /**
//     * 这个带参的init是为了 模块化开发时反射调用
//     *
//     * @param application
//     */
//    public void init(Application application) {
//        if (instance == null) {
//            instance = application;
//        }
//        //如果反射调用了带参init 那么说明onCreate方法没走 需要再次调用init 用于本模块的初始化
//        init();
//    }

    private void init() {
        //该方法已经包含了下面四个方法
        EasyNetConfigure.getInstance(instance);
//        LogUtil.setLogTag("net");
//        readEnvironmentConfig();
//        setIsWriteLog();
//        initEasyHttp();
    }

    private void initEasyHttp() {
        EasyHttp.init(this);

        //这里涉及到安全我把url去掉了，demo都是调试通的
        String Url = Api.getServerHost();

        LogUtil.d("主机地址:" + Url);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
        //设置请求参数
        HttpParams params = new HttpParams();
        params.put("appId", AppConstant.APPID);
        EasyHttp.getInstance()
                .debug("EasyHttp", isPrintLog)
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 1000)
                .setConnectTimeout(60 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Url)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
                .setHostnameVerifier(new UnSafeHostnameVerifier(Url))//全局访问规则
                .setCertificates()//信任所有证书
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params)//设置全局公共参数
                .addInterceptor(new CustomRequestInterceptor());//添加参数签名拦截器

        //.addInterceptor(new HeTInterceptor());//处理自己业务的拦截器
    }

    public class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            HttpLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            HttpLog.i("############### verify " + hostname + " " + this.host);
            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
                return false;
            return true;
        }
    }


    /**
     * 获取应用运行环境，如果应用没有启动，获取该值没有意义，所有和运行环境相关的配置必须依赖于该值
     *
     * @return {@link Constant.EnvironmentVariables}
     */
    public String getRunningEnvironment() {
        return runningEnvironment == null ? Constant.EnvironmentVariables.ONLINE : runningEnvironment;
    }

    // 读取AndroidManifest里面配置的运行环境
    private void readEnvironmentConfig() {
        try {
            ApplicationInfo appInfo = instance.getPackageManager().getApplicationInfo(
                    instance.getPackageName(), PackageManager.GET_META_DATA);
            runningEnvironment = appInfo.metaData.getString("environment", Constant.EnvironmentVariables.ONLINE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    //设置是否打印log
    private void setIsWriteLog() {
        if (runningEnvironment.equals(Constant.EnvironmentVariables.ONLINE)) {
            isPrintLog = false;
            LogUtil.setIsPrintLog(false);
        } else {
            isPrintLog = true;
            LogUtil.setIsPrintLog(true);
        }
    }
}
