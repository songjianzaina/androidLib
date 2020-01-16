package com.insworks.lib_datas.shared;


import com.insworks.lib_datas.EasyData;
import com.insworks.lib_datas.utils.MetaDataUtil;

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
 * @Description: Activity 隐式跳转 常量
 */
public class JumpConstant {

    //后期复用直接修改该处即可
    protected static String perfix;



    //登录界面
    public static String GET_ACTIVITY_LOGIN() {
        return getActionPerfix()  + "activity_login";

    }


    //主页框架界面
    public static String GET_ACTIVITY_MIAN() {
        return getActionPerfix() + "activity_main";

    }



    //消息界面
    public static String GET_ACTIVITY_MSG() {
        return getActionPerfix() + "activity_msg_center";

    }


    //我的客户经理
    public static String GET_ACTIVITY_MY_MANAGER() {
        return getActionPerfix() + "activity_my_manager";

    }


    //产品介绍
    public static String GET_ACTIVITY_PRODUCT_INTRO() {
        return getActionPerfix() + "activity_product_intro";

    }


    //我的收益
    public static String GET_ACTIVITY_MY_PROFIT() {
        return getActionPerfix() + "activity_my_profit";

    }


    //机具采购
    public static String GET_ACTIVITY_PURCHASE_MACHINE() {
        return getActionPerfix() + "activity_purchase_machine";

    }


    //收货地址管理
    public static String GET_ACTIVITY_ADDRESS_MANAGE() {
        return getActionPerfix() + "activity_address_manage";

    }


    //机具查询
    public static String GET_ACTIVITY_PRODUCT_QUERY() {
        return getActionPerfix() + "activity_product_query";

    }


    //添加结算卡
    public static String GET_ACTIVITY_ADD_SETTLEMENT() {
        return getActionPerfix() + "activity_add_settlement";

    }


    //海报分享模块
    public static String GET_ACTIVITY_POSTER_SHARED() {
        return getActionPerfix() + "activity_poster_shared";

    }


    //历史订单
    public static String GET_ACTIVITY_ORDER_HISTORY() {
        return getActionPerfix() + "activity_order_history";

    }


    //密码管理
    public static String GET_ACTIVITY_PSW_MANAGE() {
        return getActionPerfix() + "activity_psw_manage";

    }


    //个人信息中心
    public static String GET_ACTIVITY_PERSONAL_INFO() {
        return getActionPerfix() + "activity_personal_info";

    }


    //我的信息
    public static String GET_ACTIVITY_MY_INFO() {
        return getActionPerfix() + "activity_my_info";

    }


    //展示结算卡信息
    public static String GET_ACTIVITY_SHOW_SETTLE_INFO() {
        return getActionPerfix() + "activity_show_card_info";

    }


    //老版实名认证页面
    public static String GET_ACTIVITY_REALNAME_AUTH() {
        return getActionPerfix() + "activity_realname_auth";

    }

    /**
     * 获取前缀标识
     * @return
     */
    private static String getActionPerfix() {
        if (perfix == null) {

            perfix = MetaDataUtil.readPerfix(EasyData.getApplication());

        }
        return perfix;
    }
}
