package com.insworks.lib_control;

import android.app.Activity;

import com.inswork.lib_cloudbase.BuildConfig;
import com.insworks.lib_datas.bean.common.ControlJsonBean;
import com.insworks.lib_control.net.UserApi;
import com.insworks.lib_datas.shared.UserManager;
import com.insworks.lib_log.LogUtil;
import com.insworks.lib_net.net.interceptor.CloudCallBack;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.insworks.lib_control
 * @ClassName: EasyControl
 * @Author: Song Jian
 * @CreateDate: 2019/8/12 10:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/12 10:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 控制入口类
 */
public class EasyControl {

    private static EasyControl easyControl;
    private static Activity activity;

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyControl.init() 初始化！");
    }

    private EasyControl() {

    }

    /**
     * 初始化
     *
     * @param activity
     */
    public static EasyControl init(Activity activity) {
        EasyControl.activity = activity;
        if (easyControl == null) {
            synchronized (EasyControl.class) {
                if (easyControl == null) {
                    easyControl = new EasyControl();
                }
            }
        }
        //加载控制信息
        loadControlInfo();
        return easyControl;
    }

    private static void loadControlInfo() {
        UserApi.getControlJson(new CloudCallBack<ControlJsonBean>() {
            @Override
            public void onSuccess(ControlJsonBean controlJsonBean) {
                if (!BuildConfig.DEBUG) {
                    //远程控制log开关  线上环境控制 非线上环境不控制
                    LogUtil.setIsPrintLog(controlJsonBean.isIsOpenLog());
                }

                UserManager.getInstance().updateControlInfo(controlJsonBean);
            }
        });
    }
}
