package com.inswork.indexbar.net;

import com.insworks.lib_net.EasyNet;
import com.insworks.lib_net.net.interceptor.CloudCallBack;

/**
 * @ProjectName: tftpay
 * @Package: com.inswork.indexbar.net
 * @ClassName: UserApi
 * @Author: Song Jian
 * @CreateDate: 2019/6/24 18:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/24 18:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class UserApi {

    /**
     * 结算卡银行列表
     * @param cloudCallBack
     */
    public static void getTradeBankList(CloudCallBack cloudCallBack) {
        EasyNet.postObject("Additional.Getbank").execute(cloudCallBack);
    }
}
