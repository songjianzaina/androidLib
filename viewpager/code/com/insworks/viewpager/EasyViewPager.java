package com.insworks.viewpager;

import android.app.Activity;
import androidx.fragment.app.FragmentActivity;
import android.view.ViewGroup;

import com.insworks.viewpager.page.GuidePager;
import com.insworks.viewpager.page.LoopPager;
import com.insworks.viewpager.page.TabPager;
import com.zhengsr.viewpagerlib.bean.PageBean;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_viewpager
 * @ClassName: EasyViewPager
 * @Author: Song Jian
 * @CreateDate: 2019/6/14 10:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 10:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: viewpager快速集成操作入口
 */
public class EasyViewPager<T> {

    private static EasyViewPager easyViewPager;
    private static Activity activity;

    private static GuidePager guidePager;
    private static LoopPager loopPager;
    private static TabPager tabPager;
    private int pagerlayoutres;
    protected PageBean bean;


    private static void testInitialize() {
        if (activity == null)
            throw new ExceptionInInitializerError("请先在全局BaseActivity中调用 EasyScanner.initGuidePager() 初始化！");
    }



    /**
     * 初始化GuidePager
     *
     * @param activity
     */
    public static GuidePager initGuidePager(Activity activity, ViewGroup parentView) {
        EasyViewPager.activity = activity;

        if (guidePager == null) {
            synchronized (EasyViewPager.class) {
                if (guidePager == null) {
                    guidePager = new GuidePager(activity,parentView);
                }
            }
        }
        return guidePager;
    }


    /**
     * 初始化LoopPager
     *
     * @param activity
     */
    public static LoopPager initLoopPager(Activity activity, ViewGroup parentView) {
        EasyViewPager.activity = activity;

                    loopPager = new LoopPager(activity,parentView);

        return loopPager;
    }
    /**
     * 初始化TabPager
     *
     * @param activity
     */
    public static TabPager initTabPager(FragmentActivity activity, ViewGroup parentView) {
        EasyViewPager.activity = activity;
        if (tabPager == null) {
            synchronized (EasyViewPager.class) {
                if (tabPager == null) {
                    tabPager = new TabPager(activity,parentView);
                }
            }
        }
        return tabPager;
    }



}
