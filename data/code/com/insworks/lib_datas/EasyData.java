package com.insworks.lib_datas;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * @ProjectName: cloudpay
 * @Package: com.insworks.lib_datas
 * @ClassName: DatasUtil
 * @Author: Song Jian
 * @CreateDate: 2019/5/8 18:36
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/8 18:36
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 数据库初始化配置
 */
public class EasyData {
    private volatile static EasyData singleton = null;
    private static Application mApplication;

    public static void init(Application application) {
        EasyData.mApplication = application;
        getInstance(application);
    }

    private EasyData(Application application) {
        //在这里进行数据初始化操作
    }


    private static EasyData getInstance(Application application) {
        testInitialize();
        if (singleton == null) {
            synchronized (EasyData.class) {
                if (singleton == null) {
                    singleton = new EasyData(application);
                }
            }
        }
        return singleton;
    }

    private static void testInitialize() {
        if (mApplication == null)
            throw new ExceptionInInitializerError("请先在全局Application中调用 EasyData.init() 初始化！");
    }

    /**
     * 该方法提供给本库内部使用
     *
     * @return
     */
    public static Application getApplication() {
        testInitialize();
        return mApplication;
    }
}
