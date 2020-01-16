/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.insworks.lib_net.net.interceptor;

import com.insworks.lib_datas.shared.UserManager;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.EasyNetConfigure;
import com.insworks.lib_net.net.constant.AppConstant;
import com.insworks.lib_net.net.constant.ComParamContact;
import com.insworks.lib_net.net.utils.AppTools;
import com.insworks.lib_net.net.utils.MD5;
import com.zhouyou.http.interceptor.BaseDynamicInterceptor;
import com.zhouyou.http.utils.HttpLog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static com.zhouyou.http.utils.HttpUtil.UTF8;

/**
 * 请求拦截器
 * 请求过程中需要拦截处理的
 *
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomRequestInterceptor extends BaseDynamicInterceptor<CustomRequestInterceptor> {
    public static boolean isAccessPhoneAndVoucher = true;

    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        if (isAccessToken()) {//是否添加token
            //设置token
            setAccessToken(dynamicMap);
        }

        if (isAccessPhoneAndVoucher) {//是否发送已经保存的手机号码
            setPhoneAndVoucher(dynamicMap);
        }
        if (isSign()) {//是否签名,因为你的字段key可能不是sign，这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.SIGN, sign(dynamicMap));
        }
        LogUtil.d("拦截  请求地址:" + getHttpUrl());
        LogUtil.d("拦截  请求参数:" + dynamicMap.toString());
        //HttpLog.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    /**
     * companyNo 机构编号 19001代表安卓端
     *
     * @param params
     */
    private void setAccessToken(TreeMap<String, String> params) {
        String companyNo = "19001";
        String nonceStr = getGUID();

        //##################signData参数拼接####################
        StringBuilder md5Data = new StringBuilder();
        md5Data.append("companyNo=" + companyNo);
        //变量
        md5Data.append("&encryptData=" + params.get("encryptData"));
        //变量
        md5Data.append("&nonceStr=" + nonceStr);
        //变量
        md5Data.append("&service=" + params.get("service"));
        md5Data.append("&yun-api-version=" + AppTools.getVersionName(EasyNetConfigure.getApplication()));
        md5Data.append("&yun-device-type=android");
        //变量 todo
        md5Data.append("&yun-token=" + "8c8a10ff2311cfb41a03f12b6243716fb2655c5549244ede8903adcfb4ca5523");
        md5Data.append("&key=9U6MmXeB4gOWlvf8qP2QGsKYtfOMhI2C");
        LogUtil.d("拼接后的MD5: " + md5Data.toString());
        String hexMD5 = MD5.computeMd5HexString(md5Data.toString()).toUpperCase();
        //##################signData参数拼接####################

//        {
//            "companyNo":"19999",
//                "encryptData":"/EBo1ig6Ls/7YoNqwAc5kh9A9/aaygpv1yMTxAH21y3l1OFyt74pPEKZanYoIuZg",
//                "nonceStr":"ec43912153fa43ff",
//                "service":"Login.Quickin",
//                "signData":"F75C3FE43553B2ADEBD9798A6EAB735E"
//        }

        //机构编号
        params.put("companyNo", companyNo);
        //16位随机值
        params.put("nonceStr", nonceStr);
        //签名
        params.put("signData", hexMD5);

    }

    /**
     * 生成16位不重复的随机数，含数字+大小写
     *
     * @return
     */
    public String getGUID() {
        StringBuilder uid = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type) {
                case 0:
                    //0-9的随机数
                    uid.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                    uid.append((char) (rd.nextInt(25) + 65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                    uid.append((char) (rd.nextInt(25) + 97));
                    break;
                default:
                    break;
            }
        }
        return uid.toString();
    }

    private void setPhoneAndVoucher(TreeMap<String, String> params) {
        //检查是否登陆 如果用户已经登陆 那么将用户信息传至后台 否则退出重新登录
        if (UserManager.getInstance().hasUserInfo()) {
            params.put("tel", UserManager.getInstance().getUserPhone());
//            params.put("voucher", UserManager.getInstance().getVoucher());
        } else {
            UserManager.getInstance().logout();
        }
    }

    //签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        String url = getHttpUrl().url().toString();
        //url = url.replaceAll("%2F", "/");
        StringBuilder sb = new StringBuilder("POST");
        sb.append(url);
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append(AppConstant.APP_SECRET);
        String signStr = sb.toString();
        try {
            signStr = URLDecoder.decode(signStr, UTF8.name());
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        HttpLog.i(signStr);
        return MD5.encode(signStr);
    }
}
