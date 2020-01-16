package com.insworks.lib_net;

import com.insworks.lib_net.net.EasyNetConfigure;

/**
 * 数据接口基类
 * desc：直接在该类中修改host地址即可 host地址必须以 / 结尾
 */
public abstract class Api {
    //本地环境
    private static final String HOST_LOCAL = "http://t.weather.sojson.com/api/";//天气查询接口 用于网络框架测试
    //开发环境
    private static final String HOST_DEV = "https://test.qtopays.com/api.php/";
    //测试环境
    private static final String HOST_TEST = "";
    //模拟环境
    private static final String HOST_STAGING = "";
    //线上环境(生产环境)
    private static final String HOST_ONLINE = "https://test.qtopays.com/api.php/";
    //cloud专用
    public static final String IMAGE_URL = "http://cnpay.yoelian.cn/data/upload/";

    /**
     * 获取服务器主机地址
     *
     * @return 服务器地址
     */
    public static String getServerHost() {
        switch (EasyNetConfigure.getInstance().getRunningEnvironment()) {
            case Constant.EnvironmentVariables.LOCAL:
                return HOST_LOCAL;

            case Constant.EnvironmentVariables.DEV:
                return HOST_DEV;

            case Constant.EnvironmentVariables.TEST:
                return HOST_TEST;

            case Constant.EnvironmentVariables.STAGING:
                return HOST_STAGING;

            case Constant.EnvironmentVariables.ONLINE:
            default:
                return HOST_ONLINE;
        }
    }
  /**
     * 获取服务器主机地址
     *
     * @return 服务器地址
     */
    public static String getServerHost(String environment) {
        switch (environment) {
            case Constant.EnvironmentVariables.LOCAL:
                return HOST_LOCAL;

            case Constant.EnvironmentVariables.DEV:
                return HOST_DEV;

            case Constant.EnvironmentVariables.TEST:
                return HOST_TEST;

            case Constant.EnvironmentVariables.STAGING:
                return HOST_STAGING;

            case Constant.EnvironmentVariables.ONLINE:
            default:
                return HOST_ONLINE;
        }
    }


    public static String getAbsoluteUrl(String relativeUrl) {
        return getServerHost() + relativeUrl;
    }
}
