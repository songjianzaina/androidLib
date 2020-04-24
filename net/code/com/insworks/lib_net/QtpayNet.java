package com.insworks.lib_net;

import android.text.TextUtils;
import android.widget.Toast;

import com.inswork.lib_cloudbase.utils.NetworkUtil;
import com.insworks.lib_base.utils.ToastUtil;
import com.insworks.lib_datas.bean.common.ResponseBean;
import com.insworks.lib_datas.shared.UserManager;
import com.insworks.lib_datas.utils.ActivityManager;
import com.insworks.lib_datas.utils.JsonUtil;
import com.insworks.lib_loading.LoadingUtil;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.EasyNetConfigure;
import com.insworks.lib_net.net.interceptor.CloudCallBack;
import com.insworks.lib_net.net.utils.AESCrypt;
import com.insworks.lib_net.net.utils.AppTools;
import com.insworks.lib_net.net.utils.MD5;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.request.PostRequest;

import java.security.SecureRandom;
import java.util.Random;
import java.util.TreeMap;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.net
 * @ClassName: QtpayNet
 * @Author: Song Jian
 * @CreateDate: 2019/6/19 18:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/19 18:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 新中付定制的网络
 */
public class QtpayNet {

    protected PostRequest postRequest;
    private String requesType;
    private String service;
    protected TreeMap<String, String> userParams;

    public QtpayNet(String type, String service) {
        requesType = type;
        this.service = service;
        if (type == "post") {
            postRequest = EasyHttp.post("Dxapi");

            userParams = new TreeMap<>();
        }


    }

    public QtpayNet param(String key, String value) {
        //检查网络连接 没有网络的话没法初始化
        if (!NetworkUtil.isConnected(ActivityManager.getInstance().getCurrentActivity())) {
            Toast.makeText(ActivityManager.getInstance().getCurrentActivity(), "请检查网络连接", Toast.LENGTH_LONG).show();
            return this;
        }
        userParams.put(key, value);
        return this;
    }

    private QtpayNet setObject(String encryptData) {
        TreeMap<String, String> params = getParam(service, encryptData);

        String requestJson = JsonUtil.beanToJson(params);
        LogUtil.d("postObject请求头: " + JsonUtil.beanToJson(EasyHttp.getInstance().getCommonHeaders()));
        LogUtil.d("postObject全部请求参数拦截:", requestJson);
        LogUtil.json("postObject全部请求参数拦截:", requestJson);
        postRequest.upJson(requestJson);
        return this;
    }

    /**
     * companyNo 机构编号 19001代表安卓端
     */
    private TreeMap<String, String> getParam(String service, String encryptData) {
        TreeMap<String, String> params = new TreeMap<>();
        String companyNo = "20001";
        String nonceStr = getGUID();

        //##################signData参数拼接####################
        StringBuilder md5Data = new StringBuilder();
        md5Data.append("companyNo=" + companyNo);
        //变量
        md5Data.append("&encryptData=" + encryptData);
        //变量
        md5Data.append("&nonceStr=" + nonceStr);
        //变量
        md5Data.append("&service=" + service);
        md5Data.append("&yun-app-version=" + AppTools.getVersionName(EasyNetConfigure.getApplication()));
        md5Data.append("&yun-device-type=android");
        if (UserManager.getInstance().hasUserInfo()) {

            md5Data.append("&yun-token=" + UserManager.getInstance().getToken());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.put("yun-token", UserManager.getInstance().getToken());
            postRequest.headers(httpHeaders);
        } else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.put("yun-token", "");
            postRequest.headers(httpHeaders);
        }
        md5Data.append("&key=mf1OrGubnatYgsOy0aYIRj3GSBFDCDu0");
        LogUtil.d("postObject请求 MD5拼接: " , md5Data.toString());
        String hexMD5 = MD5.computeMd5HexString(md5Data.toString()).toUpperCase();
        String hexMD52 = MD5.computeMd5HexString("companyNo=19001&encryptData=vAwqWdZQb7NWyLcrVluGwwlZjCKnXMvgrtbW57bBsPSMz8Q5jGcAOYzBfhtI7U2f&nonceStr=39C6ba4v1PIM7mm2&service=Login.Quickin&yun-api-version=1.0.2&yun-device-type=android&&key=mf1OrGubnatYgsOy0aYIRj3GSBFDCDu0").toUpperCase();

        //##################signData参数拼接####################

        //机构编号
        params.put("companyNo", companyNo);
        //16位随机值
        params.put("nonceStr", nonceStr);
        //签名
        params.put("signData", hexMD5);
        //业务类型
        params.put("service", service);
        //业务数据
        params.put("encryptData", encryptData);

        return params;

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

    public QtpayNet execute(final CloudCallBack cloudCallBack) {
        if (requesType.equals("post")) {
            String encryptData = "";
            try {
                LogUtil.d("postObject业务请求参数 拦截: " + JsonUtil.beanToJson(userParams));
                encryptData = new AESCrypt().encrypt(JsonUtil.beanToJson(userParams)).replace("\n", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            setObject(encryptData);
            postExecute(cloudCallBack);
        }
//        else if (requesType.equals(GET)) {
//            getExecute(cloudCallBack);
//        } else if (requesType.equals(DOWNLOAD)) {
//            downloadExecute(cloudCallBack);
//        } else if (requesType.equals(UPLOAD)) {
//            uploadExecloadute(cloudCallBack);
//        }
        return this;
    }

    private void postExecute(final CloudCallBack cloudCallBack) {
        postRequest.execute(new SimpleCallBack<String>() {
            @Override
            public void onStart() {
                cloudCallBack.onStart();

            }

            @Override
            public void onCompleted() {
                cloudCallBack.onCompleted(null);

            }

            @Override
            public void onError(ApiException e) {
                LogUtil.e(e, "Post请求失败 错误码:" + e.getCode() + "  错误详情:" + e.getMessage());
                LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
                Toast.makeText(ActivityManager.getInstance().getCurrentActivity(), "请求错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                if (TextUtils.isEmpty(response)) {
                    ToastUtil.showToast("服务器未返回信息");
                    return;
                }
                response = response.substring(response.indexOf("{"));
                LogUtil.json("Post请求成功  Response: ", response);
                ResponseBean responseBean = JsonUtil.jsonToBean(response, ResponseBean.class);
                String code = responseBean.getCode();
                LogUtil.d("Post请求成功  code: ", code);


                if (code.equals("00")) {
                    //成功，无需解密encryptData 。使用msg提示

                    Toast.makeText(EasyNetConfigure.getApplication(), responseBean.getMsg(), Toast.LENGTH_SHORT).show();

                    //注册成功后跳转至登录界面
                    if (responseBean.getMsg().contains("注册成功")) {
                        UserManager.getInstance().logout();
                    }
                    try {

                        cloudCallBack.onSuccess(responseBean.getMsg());
                    } catch (Exception e) {
                        //解决 类型转换异常的问题 个人信息接口无数据时返回00 有数据返回01 内容不一致
                    }

                } else if (code.equals("02")) {
                    //失败，无需解密encryptData 。使用msg提示
                    if (service.equals("Realname.Idcard") || service.equals("Realname.Real")) {
                        cloudCallBack.onCompleted(responseBean.getMsg());
                        return;
                    } else if (service.equals("Additional.Cardbin")) {
                        Toast.makeText(EasyNetConfigure.getApplication(), "未检测出银行名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(EasyNetConfigure.getApplication(), responseBean.getMsg(), Toast.LENGTH_SHORT).show();
//                    cloudCallBack.onCompleted(responseBean.getMsg());
                } else if (code.equals("09")) {
                    //成功 明文结果 无需解密
                    LogUtil.json("Post请求成功  EncryptData: ", responseBean.getEncryptData());
                    cloudCallBack.onSuccess(JsonUtil.jsonToBean(responseBean.getEncryptData(), cloudCallBack.getType()));

                } else if (code.equals("01")) {
                    //成功，可解密加密域值。进行业务流程
                    //解密数据
                    String decodeData = null;
                    try {
                        decodeData = new AESCrypt().decrypt(responseBean.getEncryptData());
                        LogUtil.d("请求成功 解密后的数据为: " + decodeData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        //解析解密后的数据
                        Object datas = JsonUtil.jsonToBean(decodeData, cloudCallBack.getType());
                        if (datas == null) {
                            //json解析出现了异常 有可能返回的是数组,或者还未指定解析对象
                            if (decodeData.contains("[") && decodeData.replace(" ", "").length() >= 3) {
                                //说明是数组 那么直接返回json串
                                cloudCallBack.onSuccess(decodeData);
                            } else {
                                //否则 返回的是[]  转换的可能是对象 也可能是数组
                                cloudCallBack.onCompleted(null);
                            }
                        } else {
                            //解析指定对象成功
                            cloudCallBack.onSuccess(datas);

                        }

                    } catch (Exception e) {
                        //解决 Activity被销毁 网络请求未中断 从而导致空指针的情况
                    }


                } else if (code.equals("82") ) {
                    //弹出手淘授权框
                    cloudCallBack.onCompleted("datas");

                } else if (code.equals("90") || code.equals("91") || code.equals("92") || code.equals("93")) {
                    Toast.makeText(EasyNetConfigure.getApplication(), responseBean.getMsg() + " 请重新登录", Toast.LENGTH_SHORT).show();
                    //无登录权限 退出登录页面
                    UserManager.getInstance().logout();

                } else {
                    //94 服务类型错误 95 系统内部错误 96当前服务不可用 97验签失败 98加密数据异常 99缺少参数
                    Toast.makeText(EasyNetConfigure.getApplication(), responseBean.getMsg() + " 请求错误", Toast.LENGTH_SHORT).show();
//                    UserManager.getInstance().logout();

                }


            }
        });
    }

}
