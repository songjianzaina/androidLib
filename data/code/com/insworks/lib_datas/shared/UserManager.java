package com.insworks.lib_datas.shared;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;
import com.insworks.lib_datas.EasyData;
import com.insworks.lib_datas.bean.ShareInfoBean;
import com.insworks.lib_datas.bean.common.ControlJsonBean;
import com.insworks.lib_datas.bean.common.UserInfoBean;
import com.insworks.lib_datas.utils.ActivityManager;
import com.insworks.lib_datas.utils.JsonUtil;
import com.insworks.lib_log.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户管理
 * <p>
 * SharedPreference 相关修改使用 apply 方法进行提交会先写入内存，然后异步写入磁盘，commit
 * 方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，apply会将最后修改内容写入磁盘。
 * 但是如果希望立刻获取存储操作的结果，并据此做相应的其他操作，应当使用 commit。
 * <p>
 * <p>
 * SharedPreferences 是以 xm l形式保存
 * ---------------------
 */
public class UserManager {
    private static final String USER_INFO = "user_info";
    private static final String GUIDE_PAGE = "guide_page";
    private static final String COPYRIGHT = "copyright";
    private static final String LOGIN_USERNAME = "login_username";
    private static final String NET_TOKEN = "net_token";
    private static final String IS_REMEM = "is_remember";
    private static final String Login_PSW = "user_password";
    private static final String SEARCH_HISTORY = "search_history";
    private static final String IS_REAL_AUTH = "is_real_auth";
    private static final String IS_REAL_LIVING = "is_real_living";
    private static final String IS_BIND_BANK_CARD = "is_bind_bank_card";
    private static final String SHARE_INFO = "share_info";
    private static final String APK_DOWNLOAD_URL = "apk_download_url";
    private static final String CONTROL_INFO = "control_info";


    private static UserManager instance;
    private UserInfoBean userInfoBean;
    private List<String> historyDatas;
    private ShareInfoBean shareInfo;
    private ControlJsonBean controlBean;

    private UserManager() {

    }

    public synchronized static UserManager getInstance() {
        if (null == instance) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * 获取用户信息缓存表
     *
     * @return
     */
    public SharedPreferences getUserPreferences() {
        return EasyData.getApplication().getSharedPreferences("info", Context.MODE_PRIVATE);
    }

    /**
     * 获取登录信息缓存表
     *
     * @return
     */
    private SharedPreferences getLoginPreferences() {
        return EasyData.getApplication().getSharedPreferences("login", Context.MODE_PRIVATE);
    }


    /**
     * 获取控制信息缓存表
     *
     * @return
     */
    private SharedPreferences getControlPreferences() {
        return EasyData.getApplication().getSharedPreferences("control", Context.MODE_PRIVATE);
    }

    /**
     * 获取网络token缓存表
     *
     * @return
     */
    private SharedPreferences getTokenPreferences() {
        return EasyData.getApplication().getSharedPreferences("token", Context.MODE_PRIVATE);
    }

    /**
     * 获取导航页面表
     *
     * @return
     */
    private SharedPreferences getGuidePreferences() {
        return EasyData.getApplication().getSharedPreferences("guide", Context.MODE_PRIVATE);
    }

    /**
     * 获取历史记录表
     *
     * @return
     */
    private SharedPreferences getHistoryPreferences() {
        return EasyData.getApplication().getSharedPreferences("history", Context.MODE_PRIVATE);
    }

    /*
     *//**
     * 更新用户信息
     *
     * @param json
     *//*
    public void updateUserInfo(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        userInfoBean = JsonUtil.jsonToBean(json, UserInfoBean.class);
        //另外保存一份token
        setToken(userInfoBean.getToken());
        getUserPreferences().edit().putString(USER_INFO, json).apply();
    }


    */

    /**
     * 更新用户信息
     *
     * @param userInfo
     */
    public void updateUserInfo(UserInfoBean userInfo) {
        if (null == userInfo) {
            return;
        }
        //另外保存一份token
        setToken(userInfo.token);
        //另外保存一份手机号 用于登录
/*
        setLoginUsername(userInfo.getUsername());
        if (userInfo.getRealname().equals("Y")) {
            //是否实名认证
            setRealAuth(true);
        } else {

            setRealAuth(false);
        }
        if (userInfo.getRealliving().equals("Y")) {
            //是否活体认证
            setRealLiving(true);
        } else {

            setRealLiving(false);
        }
        if (userInfo.getRealbank().equals("Y")) {
            //是否绑定结算卡
            setBindBankCard(true);
        } else {

            setBindBankCard(false);
        }
*/

        userInfoBean = userInfo;
        String json = JsonUtil.beanToJson(userInfo);
        getUserPreferences().edit().putString(USER_INFO, json).apply();
    }

    /**
     * 更新远程控制信息
     *
     * @param controlJsonBean
     */
    public void updateControlInfo(ControlJsonBean controlJsonBean) {
        if (null == controlJsonBean) {
            return;
        }
        controlBean = controlJsonBean;
        String json = JsonUtil.beanToJson(controlJsonBean);
        getControlPreferences().edit().putString(CONTROL_INFO, json).apply();
    }

    /**
     * 获取远程控制信息
     *
     * @return
     */
    public ControlJsonBean readControlInfo() {
        if (null == controlBean) {
            String json = getControlPreferences().getString(CONTROL_INFO, null);
            if (json == null) {
                // 控制信息不存在 说明还未初始化控制库
                LogUtil.e("控制信息库还未初始化,未能获取到控制信息");
            }
            controlBean = JsonUtil.jsonToBean(json, ControlJsonBean.class);
        }
        return controlBean;
    }

    public void updateGuideLog(boolean isOver) {
        getGuidePreferences().edit().putBoolean(GUIDE_PAGE, isOver).apply();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfoBean readUserInfo() {
        if (null == userInfoBean) {
            String json = getUserPreferences().getString(USER_INFO, null);
            if (json == null) {
                // 用户数据不存在,说明还未登录,或者用户token过期 此时强制用户重新登录
                logout();
            }
            userInfoBean = JsonUtil.jsonToBean(json, UserInfoBean.class);
        }
        return userInfoBean;
    }

    /**
     * 获取token 值
     *
     * @return
     */
    public String getToken() {
        return getTokenPreferences().getString(NET_TOKEN, null);
    }


    /**
     * 获取登录用户名
     *
     * @return
     */
    public String getLoginUsername() {
        return getLoginPreferences().getString(LOGIN_USERNAME, null);
    }


    /**
     * 清除token 值
     *
     * @return
     */
    public void clearToken() {
        getTokenPreferences().edit().clear().apply();
    }

    /**
     * 清除历史记录 值
     *
     * @return
     */
    public void clearHistory() {
        historyDatas = null;
        getHistoryPreferences().edit().clear().apply();
    }

    /**
     * 获取token 值
     *
     * @return
     */
    public void setToken(String token) {
        if (null == token) {
            return;
        }
        getTokenPreferences().edit().putString(NET_TOKEN, token).apply();
    }

    /**
     * 保存登录用户名
     *
     * @return
     */
    public void setLoginUsername(String username) {
        if (username == null) {
            return;
        }
        getLoginPreferences().edit().putString(LOGIN_USERNAME, username).apply();
    }


    /**
     * 清除用户数据
     */
    public void clearUserInfo() {
        userInfoBean = null;
        getUserPreferences().edit().clear().apply();

        clearHistory();
    }


    /**
     * 单独保存一份明文密码 用于登录
     *
     * @return
     */
    public String getLoginPassword() {
        return getLoginPreferences().getString(Login_PSW, "");
    }

    /**
     * 用户是勾选记住密码
     *
     * @param checked
     */
    public void setRememberPsw(boolean checked) {
        getLoginPreferences().edit().putBoolean(IS_REMEM, checked).apply();
    }

    /**
     * 用户是勾选记住密码
     */
    public boolean isRememberPsw() {
        //默认勾选
        return getLoginPreferences().getBoolean(IS_REMEM, true);
    }

    /**
     * 单独保存一份明文密码 用于登录
     *
     * @return
     */
    public void setLoginPassword(String password) {
        if (password == null) {
            return;
        }
        getLoginPreferences().edit().putString(Login_PSW, password).apply();
    }

    /**
     * 清除登录数据
     */
    public void clearLoginInfo() {
        getLoginPreferences().edit().clear().apply();
    }

    /**
     * 判断用户数据是否存在
     *
     * @return
     */
    public boolean hasUserInfo() {
        return getUserPreferences().getString(USER_INFO, null) != null;
    }

    /**
     * 判断控制信息数据是否存在
     *
     * @return
     */
    public boolean hasControlInfo() {
        return getControlPreferences().getString(CONTROL_INFO, null) != null;
    }


    /**
     * 判断用户数据是否存在
     *
     * @return
     */
    public boolean hasLoginInfo() {
        return getLoginPreferences().getString(LOGIN_USERNAME, null) != null && getLoginPreferences().getString(Login_PSW, null) != null;
    }

    /**
     * 导航页判断
     *
     * @return
     */
    public int getGuideCode() {
        return getGuidePreferences().getInt(GUIDE_PAGE, -1);
    }

    /**
     * 导航页判断
     *
     * @return
     */
    public void setGuideCode(int versionCode) {
        getGuidePreferences().edit().putInt(GUIDE_PAGE, versionCode).apply();
    }


    public void logout() {
        Activity activity = ActivityManager.getInstance().getCurrentActivity();
        if (null == activity) return; // 为null说明没有界面打开
        clearUserInfo();
        ActivityManager.getInstance().finishAllActivity();

        //隐式跳转至登录界面
        Intent intent = new Intent(JumpConstant.GET_ACTIVITY_LOGIN());
        activity.startActivity(intent);

    }


    //---------------------以上是app通用方法  便于后期拷贝复用-------------------

//-------------------------以下是cloudpay特有方法-------------------------
//
//    /**
//     * 获取token 值
//     *
//     * @return
//     */
//    public String getVoucher() {
//        if (null == userInfoBean) {
//            readUserInfo();
//        }
//        return userInfoBean == null ? null : userInfoBean.getVoucher();
//    }
//
//    /**
//     * 获取用户cid 值
//     *
//     * @return
//     */
//    public String getUserCid() {
//        if (null == userInfoBean) {
//            readUserInfo();
//        }
//        return userInfoBean == null ? null : userInfoBean.getCid();
//    }
//

    /**
     * 是否实名认证
     *
     * @return
     */
    public boolean isRealAuth() {

        return getUserPreferences().getBoolean(IS_REAL_AUTH, false);
    }

    /**
     * 是否实名认证
     *
     * @return
     */
    public void setRealAuth(Boolean isRealName) {

        getUserPreferences().edit().putBoolean(IS_REAL_AUTH, isRealName).apply();
    }

    /**
     * 是否活体认证
     *
     * @return
     */
    public boolean isRealLiving() {

        return getUserPreferences().getBoolean(IS_REAL_LIVING, false);
    }

    /**
     * 是否活体认证
     *
     * @return
     */
    public void setRealLiving(Boolean isRealliving) {

        getUserPreferences().edit().putBoolean(IS_REAL_LIVING, isRealliving).apply();
    }

    /**
     * 是否绑定结算卡
     *
     * @return
     */
    public boolean isBindBankCard() {

        return getUserPreferences().getBoolean(IS_BIND_BANK_CARD, false);
    }

    /**
     * 是否绑定结算卡
     *
     * @return
     */
    public void setBindBankCard(Boolean isbindbankcard) {

        getUserPreferences().edit().putBoolean(IS_BIND_BANK_CARD, isbindbankcard).apply();
    }

    /*
     */

    /**
     * 获取用户手机号
     *
     * @return
     *//*

    public String getUserPhone() {
        if (null == userInfoBean) {
            readUserInfo();
        }
        return userInfoBean == null ? null : userInfoBean.u();
    }

*/
    public void setBigPosIsSuccess(boolean bigPosIsBean) {
    }

    /**
     * 保存搜索历史
     *
     * @param searchHistoryDatas
     */
    public void setSearchHistory(List<String> searchHistoryDatas) {
        if (null == searchHistoryDatas) {
            return;
        }
        if (searchHistoryDatas.size() > 15) {//最多保存15条
            searchHistoryDatas = searchHistoryDatas.subList(0, 15);
        }
        this.historyDatas = searchHistoryDatas;
        String json = JsonUtil.beanToJson(searchHistoryDatas);
        getHistoryPreferences().edit().putString(SEARCH_HISTORY, json).apply();
    }

    /**
     * 获取搜索历史
     *
     * @return
     */
    public List<String> getSearchHistory() {
        if (null == historyDatas) {
            String json = getHistoryPreferences().getString(SEARCH_HISTORY, null);
            if (json == null) {
                setSearchHistory(new ArrayList<>());
                return historyDatas;
            }

            historyDatas = JsonUtil.jsonToBean(json, new TypeToken<List<String>>() {
            }.getType());
        }
        return historyDatas;
    }

    /**
     * 添加单个历史
     *
     * @return
     */
    public void addSearchHistory(String one) {
        List<String> searchList = getSearchHistory();
        searchList.add(0, one);
        setSearchHistory(searchList);
    }

    /**
     * 是否已经设置了支付密码
     *
     * @return
     */
    public boolean hasPayPassword() {
        return false;
    }

    /**
     * 保存支付密码  是否要独立出来 待定
     *
     * @param payPassword
     */
    public void setPayPassword(String payPassword) {

    }

    /**
     * 获取支付密码
     */
    public String getPayPassword() {
        return null;
    }

    /**
     * 更新分享信息
     *
     * @param shareInfoBean
     */
    public void updateShareInfo(ShareInfoBean shareInfoBean) {
        if (shareInfoBean == null) {
            return;
        }
        shareInfo = shareInfoBean;
        String json = JsonUtil.beanToJson(shareInfoBean);
        getUserPreferences().edit().putString(SHARE_INFO, json).apply();
    }



    /**
     * 获取分享信息
     *
     * @param
     */
    public ShareInfoBean readShareInfo() {
        if (null == shareInfo) {
            String json = getUserPreferences().getString(SHARE_INFO, null);
            if (json == null) {
                // 分享信息不存在 可能是后台未提供信息 或者是网络不好未获取到
                return null;
            }
            shareInfo = JsonUtil.jsonToBean(json, ShareInfoBean.class);
        }
        return shareInfo;
    }


}
