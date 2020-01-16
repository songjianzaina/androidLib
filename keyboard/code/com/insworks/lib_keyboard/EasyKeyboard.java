package com.insworks.lib_keyboard;

import android.app.Activity;

import com.insworks.lib_keyboard.one.InputBusinessPwdDlg;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_keyboard
 * @ClassName: EasyKeyboard
 * @Author: Song Jian
 * @CreateDate: 2019/5/24 17:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/24 17:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class EasyKeyboard {
    private static InputBusinessPwdDlg inputBusinessPwdDlg;
    private static Activity mActivity;


    /**
     * 初始化扫描库
     *
     * @param activity
     */
    public static InputBusinessPwdDlg init(Activity activity) {
        inputBusinessPwdDlg = new InputBusinessPwdDlg(activity);
        return inputBusinessPwdDlg;
    }
}
