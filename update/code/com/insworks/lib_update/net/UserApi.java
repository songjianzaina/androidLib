package com.insworks.lib_update.net;

import android.os.Environment;

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
    private static String mayunBaseUrl= "https://gitee.com/songjianzaina/app_server_data/raw/master/update/";
    //github
    private static String githubBaseUrl= "https://raw.githubusercontent.com/songjianzaina/app_server_data/master/update/";
    //哪个app  以/结尾 比如:cloudpay/
    private static String whichApp= "";

    private static String jsonUrl= "update.json";


    public static void downloadApk(String url, CloudCallBack callBack) {
        EasyNet.downLoad(url)
                .setFilePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/")//默认在：/storage/emulated/0/Android/data/包名/files/1494647767055
//                .setFileName("custom_name")//默认名字是时间戳生成的
                .execute(callBack);
    }

    public static void getUpdateJson( CloudCallBack callBack) {
        EasyNet.get(whichApp+jsonUrl).baseUrl(githubBaseUrl)
                .execute(callBack);
    }
}
