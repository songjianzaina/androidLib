package com.insworks.lib_loading;

import android.app.Activity;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_loading
 * @ClassName: LoadingUtil
 * @Author: Song Jian
 * @CreateDate: 2019/5/31 12:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/31 12:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 加载对话框工具类
 */
public class LoadingUtil {

    private static LoadingDialog dialog;
    private static Activity mActivity;
    protected static String mclassName;

    private static void testInitialize() {
        if (mActivity == null)
            throw new ExceptionInInitializerError("请先调用 LoadingUtil.init() 初始化！");
    }

    /**
     * 初始化扫描库
     *
     * @param
     */
    public static LoadingDialog init() {
        Activity act = LoadingApplication.getCurrentActivity();
        if (dialog == null) {
            synchronized (LoadingUtil.class) {
                if (dialog == null) {
                    dialog = new LoadingDialog(act);
                }
            }
        }
        return dialog;
    }

    /**
     * 初始化dialog
     *
     * @param
     */
    public static LoadingDialog init(Activity activity) {
        if (activity==null) {
            return dialog   ;
        }
        if (activity.getComponentName().getClassName().equals(mclassName)) {
            //同一个界面 不必要重新创建dialog对象 如果已经有了 就直接复用 没有就新建
            if (dialog == null) {
                synchronized (LoadingUtil.class) {
                    if (dialog == null) {
                        dialog = new LoadingDialog(activity);
                    }
                }
            }
        } else {
            //否则 销毁原来的Dialog 重新创建新的
            dialog = new LoadingDialog(activity);
            mclassName = activity.getComponentName().getClassName();
        }
        mActivity = activity;

        return dialog;
    }


}
