package com.insworks.lib_datas.bean.common;

import android.os.Parcelable;

import com.insworks.lib_datas.bean.common.ResponseBean;

/**
 * @author : Song Jian
 * date   : 2020/3/16
 * desc   :
 *
 */
public class UserInfoBean extends ResponseBean  {

    /**
     * has_phone : 1
     * has_tbacc : 0
     * has_uinfo : 1
     * have_pid : Y
     * have_transpwd : N
     * level : L0
     * realbank : N
     * realliving : N
     * realname : N
     * recode : TDXS0053
     * token : 25d0ae7a8f88f4bbbfffb0935c1ffe5ec7c9eb1a1be6ec5c6f6ef762f3a39765
     * userId : 41
     * userinfo : {"nickName":"土豆粉","avatarUrl":"https://tdxs.oss-cn-shenzhen.aliyuncs.com/missing-face.png"}
     */

    public String has_phone;
    public String has_tbacc;
    public String has_uinfo;
    public String have_pid;
    public String have_transpwd;
    public String level;
    public String level_detail;
    public String realbank;
    public String realliving;
    public String realname;
    public String recode;
    public String Invitation_code;
    public String level_icon;
    public String token;
    public String cid="";
    public String invitation;
    public String url_super;
    public int userId;
    public UserinfoBean userinfo;



    public static class UserinfoBean {
        /**
         * nickName : 土豆粉
         * avatarUrl : https://tdxs.oss-cn-shenzhen.aliyuncs.com/missing-face.png
         */

        public String nickName;
        public String avatarUrl;

    }
}
