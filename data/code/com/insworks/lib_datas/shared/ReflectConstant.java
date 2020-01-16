package com.insworks.lib_datas.shared;

/**
 * @ProjectName: cloudpay
 * @Package: com.insworks.lib_datas.news
 * @ClassName: JumpConstant
 * @Author: Song Jian
 * @CreateDate: 2019/5/8 9:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/8 9:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 反射常量  用于反射创建 fragment对象
 */
public class ReflectConstant {

    //后期复用直接修改该处即可
    public static final String FRAGMENT_REFIX = "com.insworks.module_";

    public static final String FRAGMENT_HOME = FRAGMENT_REFIX+"home.HomeFragment";
    public static final String FRAGMENT_SERVICE = FRAGMENT_REFIX+"service.ServiceFragment";
    public static final String FRAGMENT_PORFIT = FRAGMENT_REFIX+"profit.ProfitFragment";
    public static final String FRAGMENT_ME = FRAGMENT_REFIX+"me.MeFragment";

}
