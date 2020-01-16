package com.insworks.lib_base.utils;

import android.os.SystemClock;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.inswork.lib_cloudbase.utils
 * @ClassName: ClickManager
 * @Author: Song Jian
 * @CreateDate: 2019/8/8 10:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/8 10:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 点击次数管理工具类
 */
public class ClickManager {
    private static int COUNTS = 5;// 点击次数
    private static long[] mHits = new long[COUNTS];//记录点击次数
    private static long DURATION = 2000;//有效时间

    private static long lastClickTime=0;;//双击事件开始时间
    private static final int CLICK_TIME = 500; //快速点击间隔时间
    private static ClickManager instance;

    private ClickManager() {

    }
    public synchronized static ClickManager getInstance() {
        if (null == instance) {
            instance = new ClickManager();
        }
        return instance;
    }

    /**
     * 在开发中有时候需要做一些隐蔽的功能，例如：手机“设置”里的“开发模式”
     * ，需要连续点击7次版本号后才会显示出来。
     */
    public boolean  afterManyClick() {
        //将mHints数组内的所有元素左移一个位置
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //获得当前系统已经启动的时间
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            // 相关逻辑操作
            //初始化点击次数
            mHits = new long[COUNTS];
            return true;
        } else {
            //提示用户剩余点击次数
            ToastUtil.showToast("还剩"+mHits.length+"次点击");
            return false;
        }

    }

    /**
     * 连续多次点击
     * @return
     */
    public boolean doubleClick() {
        //uptimeMillis 为开机到现在的时间
        if (SystemClock.uptimeMillis() - lastClickTime < CLICK_TIME) {//判断两次点击时间差
            return true;
        } else {
            lastClickTime = SystemClock.uptimeMillis();
            return false;
        }
    }






}