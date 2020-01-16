package com.insworks.megviface.net;

import com.insworks.lib_net.EasyNet;
import com.insworks.lib_net.net.interceptor.CloudCallBack;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.megviface.net
 * @ClassName: UserApi
 * @Author: Song Jian
 * @CreateDate: 2019/6/21 17:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/21 17:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class UserApi {


    /**
     * 获取活体检测64编码照片
     */
    public static void getFaceReuslt(String sign, String signVersoin, String bizToken, CloudCallBack cloudCallBack) {

        EasyNet.get("get_result").baseUrl("https://openapi.faceid.com/face/v1.2/sdk/").params("sign", sign).params("sign_version", signVersoin).params("biz_token", bizToken).params("verbose", 1 + "").execute(cloudCallBack);
    }
//    /**
//     * 获取BizToken
//     */
//    public static void getBizToken(String sign, String signVersoin, String livenessType, int comparisonType, String idcardName, String idcardNum, String uuid, byte[] image_ref1, CloudCallBack cloudCallBack) {
//      EasyNet.post("get_biz_token").baseUrl("https://openapi.faceid.com/face/v1.2/sdk/").params("sign", sign).params("sign_version", signVersoin).params("liveness_type", livenessType).params("comparison_type", "" + comparisonType).params("verbose", 1 + "").params("idcard_name", idcardName).params("idcard_number", idcardNum).execute(cloudCallBack);;
//
//    }


}


