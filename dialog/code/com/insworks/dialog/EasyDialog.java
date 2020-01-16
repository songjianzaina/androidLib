package com.insworks.dialog;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.insworks.lib_base.base.BaseDialog;
import com.insworks.lib_base.base.BaseDialogFragment;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.dialog
 * @ClassName: EasyDialog
 * @Author: Song Jian
 * @CreateDate: 2019/6/5 14:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 14:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 自定义dialog入口类
 */
public class EasyDialog {
    private static EasyDialog easyDialog;
    private static FragmentActivity activity;

    public EasyDialog() {

    }

    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先调用 EasyDialog.init() 初始化！");
    }

    /**
     * 初始化极简dialog
     *
     * @param activity
     */
    public static EasyDialog init(FragmentActivity activity) {
        EasyDialog.activity = activity;
        if (easyDialog == null) {
            synchronized (EasyDialog.class) {
                if (easyDialog == null) {
                    easyDialog = new EasyDialog();
                }
            }
        }
        return easyDialog;
    }

    public void showSimple(int layoutId) {
        testInitialize();
        new BaseDialogFragment.Builder(activity)
                .setContentView(layoutId)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                //.setText(id, "我是预设置的文本")
                .show();
    }

    public void showSimple(View view) {
        testInitialize();
        new BaseDialogFragment.Builder(activity)
                .setContentView(view)
                .setAnimStyle(BaseDialog.AnimStyle.SCALE)
                //.setText(id, "我是预设置的文本")
                .show();
    }
}
