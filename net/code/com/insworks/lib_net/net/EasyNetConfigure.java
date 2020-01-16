package com.insworks.lib_net.net;

import android.app.Application;
import android.util.Log;

import com.inswork.lib_cloudbase.BuildConfig;
import com.insworks.lib_datas.utils.MetaDataUtil;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.Api;
import com.insworks.lib_net.Constant;
import com.insworks.lib_net.net.constant.AppConstant;
import com.insworks.lib_net.net.interceptor.CustomRequestInterceptor;
import com.insworks.lib_net.net.utils.AppTools;
import com.insworks.lib_net.net.utils.SystemInfoUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.utils.HttpLog;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @ProjectName: cloudpay
 * @Package: com.insworks.net.net
 * @ClassName: EasyNetConfigure
 * @Author: Song Jian
 * @CreateDate: 2019/5/8 18:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/8 18:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 网络框架的配置
 */
public class EasyNetConfigure {
    protected static EasyNetConfigure easyNetConfigure;
    private String runningEnvironment;
    private boolean isPrintLog = true;
    private static Application mApplication;
    private String host;

    public static EasyNetConfigure getInstance(Application app) {
        mApplication = app;
        if (app == null) {
            Log.d("EasyNetConfigure", "EasyNet配置失败 ：初始化Application参数为空");
            return null;
        }
        if (easyNetConfigure == null) {
            easyNetConfigure = new EasyNetConfigure(app);
            //只初始化一次即可
        }
        return easyNetConfigure;
    }


    private EasyNetConfigure(Application app) {
        //设置当前LogTag
        LogUtil.setLogTag("insworks");
        readEnvironmentConfig();
        setIsWriteLog();
        initEasyHttp(app);
    }


    /**
     * 该方法提供给本库内部使用
     *
     * @return
     */
    public static Application getApplication() {
        testInitialize();
        return mApplication;
    }



    /**
     * 该方法提供给本库内部使用
     *
     * @return
     */
    public static EasyNetConfigure getInstance() {
        testInitialize();
        return easyNetConfigure;
    }

    private static void testInitialize() {
        if (mApplication == null)
            throw new ExceptionInInitializerError("请先在全局Application中调用 EasyNet.init() 初始化！");
    }

    private void initEasyHttp(Application app) {

        EasyHttp.init(app);

        //根据清单文件里面配置的环境获取主机地址
        String baseUrl = Api.getServerHost(getRunningEnvironment());

        LogUtil.d("初始化 主机地址:" + baseUrl);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", SystemInfoUtils.getUserAgent(app, AppConstant.APPID));
        //###########################新中付定制请求头参数################################
        //app 版本号
        headers.put("yun-api-version", AppTools.getVersionName(app));
        //设备类型
        headers.put("yun-device-type", "android");
        //token值 首次登陆可传空
        headers.put("yun-token","");
        //银行支行查询密钥
        headers.put("Authorization","APPCODE 0904e961aaef4609a4191018396cc90f");
        //###########################新中付定制请求头参数################################
        //设置公共请求参数
        HttpParams params = new HttpParams();
//        userParams.put("companyNo", "19001");

        EasyHttp.getInstance()
                .debug("EasyHttp", true)
                .setReadTimeOut(20 * 1000)
                .setWriteTimeOut(20 * 1000)
                .setConnectTimeout(10 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(baseUrl)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
                .setHostnameVerifier(new UnSafeHostnameVerifier(baseUrl))//全局访问规则
                .setCertificates()//信任所有证书
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params)//设置全局公共参数
                .addInterceptor(new CustomRequestInterceptor());//添加参数签名拦截器


        //.addInterceptor(new HeTInterceptor());//处理自己业务的拦截器
    }

    private class UnSafeHostnameVerifier implements HostnameVerifier {
        private String host;

        public UnSafeHostnameVerifier(String host) {
            this.host = host;
            HttpLog.i("###############　UnSafeHostnameVerifier " + host);
        }

        @Override
        public boolean verify(String hostname, SSLSession session) {
            HttpLog.i("############### verify " + hostname + " " + this.host);
//            if (this.host == null || "".equals(this.host) || !this.host.contains(hostname))
//                return false;
            if (this.host == null || "".equals(this.host) )
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
        runningEnvironment = MetaDataUtil.readEnvironmentConfig(mApplication);
    }

    // 读取AndroidManifest里面配置的主机地址
    public String readHost() {
        if (host == null) {

            host = MetaDataUtil.readHost(mApplication);
        }
        return host;
    }

    //设置是否打印log
    private void setIsWriteLog() {
        if (runningEnvironment.equals(Constant.EnvironmentVariables.ONLINE)|| !BuildConfig.DEBUG) {
            isPrintLog = false;
            LogUtil.setIsPrintLog(false);
        } else {
            isPrintLog = true;
            LogUtil.setIsPrintLog(true);
        }
    }


}
