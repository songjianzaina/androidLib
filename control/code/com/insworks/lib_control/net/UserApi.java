package com.insworks.lib_control.net;

import com.insworks.lib_net.EasyNet;
import com.insworks.lib_net.net.interceptor.CloudCallBack;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_update.net
 * @ClassName: UserApi
 * @Author: Song Jian
 * @CreateDate: 2019/7/18 15:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/18 15:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 网络请求
 */
public class UserApi {
    //码云
    private static String mayunBaseUrl= "https://gitee.com/songjianzaina/app_server_data/raw/master/control/";
    //github
    private static String githubBaseUrl= "https://raw.githubusercontent.com/songjianzaina/app_server_data/master/control/";
    //哪个app  以/结尾 比如:cloudpay/
    private static String whichApp= "";
    private static String jsonUrl= "minead_control.json";


    /**
     * 获取控制信息
     * @param callBack
     */
    public static void getControlJson( CloudCallBack callBack) {
        EasyNet.get(whichApp+jsonUrl).baseUrl(githubBaseUrl)
                .execute(callBack);
    }
}
