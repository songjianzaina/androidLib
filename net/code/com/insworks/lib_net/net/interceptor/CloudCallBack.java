package com.insworks.lib_net.net.interceptor;

import com.insworks.lib_datas.utils.ActivityManager;
import com.insworks.lib_loading.LoadingUtil;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.Utils;

import java.lang.reflect.Type;

/**
 * @ProjectName: cloudpay
 * @Package: com.insworks.net.net.interceptor
 * @ClassName: CloudCallBack
 * @Author: Song Jian
 * @CreateDate: 2019/5/7 13:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/7 13:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 请求响应拦截器,返回数据后在本类进行过滤处理
 */
public abstract class CloudCallBack<T>  {


//
    public void onError(ApiException e,String msg){};

    public abstract void onSuccess(T t);


    public Type getType() {//获取需要解析的泛型T类型
        return Utils.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        return Utils.findRawType(getClass());
    }

    public void onStart() {
        //显示加载对话框
        LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).show();
    }

    public void onCompleted(String msg) {
        //关闭加载对话框
        LoadingUtil.init(ActivityManager.getInstance().getCurrentActivity()).dismiss();
    }

    /**
     * 文件下载进度
     * @param progress
     * @param done
     */
    public void update(int progress, boolean done) {

    }
}
