package com.inswork.indexbar.bank;

import android.content.Context;
import android.content.Intent;

/**
 * @ProjectName: cloudpay
 * @Package: com.inswork.indexbar.bank
 * @ClassName: BankIndexBarManager
 * @Author: Song Jian
 * @CreateDate: 2019/5/5 11:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/5 11:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: BankIndex管理类
 */
public class BankIndexBarManager {

    private BankIndexBarManager() {
        if (context == null) {
            return;
        }
        //进入银行选择页面
        context.startActivity(new Intent(context, BankQuickActivity.class));
        //设置事件监听
        if (listener != null) {
            BankQuickActivity.setOnItemClickListener(listener);
        }
    }

    private static Context context;
    private static OnItemClickListener listener;

    /**
     * 初始化manager的时候跳转至index页面
     *
     * @param context
     * @return
     */
    public static BankIndexBarManager init(Context context, OnItemClickListener listener) {
        BankIndexBarManager.context = context;
        BankIndexBarManager.listener = listener;
        return new BankIndexBarManager();
    }


}
