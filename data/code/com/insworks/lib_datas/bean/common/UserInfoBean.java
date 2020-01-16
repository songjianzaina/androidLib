package com.insworks.lib_datas.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户信息
 * <p/>
 * Created by jiangyujiang on 16/6/2.
 */
public class UserInfoBean extends ResponseBean implements Parcelable {




    /**
     * fee : 0.60
     * token : 8c75c44a05092e9752e39758deef2991024b8681abb49470a6b76d4ecbd45eda
     * username : 18630620063
     */

    private String fee;//用户当前收款费率
    private String token;
    private String businesshours;//结算时间
    private String cid;//用于推送别名
    private String username;//手机号
    private String realname;//是否实名 Y 已实名 N 未实名
    private String realbank;//是否绑定结算卡 Y 已绑定 N 未绑定
    private String vip;//VIP分1-5个等级 N非VIP用户
    private String have_transpwd;//是否已经设置交易密码 Y 未设置，N已设置
    private String realliving;//是否通过活体检测
    private String idcardnname;//真实姓名 realname为Y时才有值 配合使用
    private String idcardnumber;//身份证号 realname为Y时才有值 配合使用

    public String getRealname() {
        return realname;
    }

    public String getBusinesshours() {
        return businesshours;
    }

    public void setBusinesshours(String businesshours) {
        this.businesshours = businesshours;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealbank() {
        return realbank;
    }

    public void setRealbank(String realbank) {
        this.realbank = realbank;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getHave_transpwd() {
        return have_transpwd;
    }

    public void setHave_transpwd(String have_transpwd) {
        this.have_transpwd = have_transpwd;
    }


    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getRealliving() {
        return realliving;
    }

    public void setRealliving(String realliving) {
        this.realliving = realliving;
    }

    public String getIdcardnname() {
        return idcardnname;
    }

    public void setIdcardnname(String idcardnname) {
        this.idcardnname = idcardnname;
    }

    public String getIdcardnumber() {
        return idcardnumber;
    }

    public void setIdcardnumber(String idcardnumber) {
        this.idcardnumber = idcardnumber;
    }


    public UserInfoBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fee);
        dest.writeString(this.token);
        dest.writeString(this.username);
        dest.writeString(this.realname);
        dest.writeString(this.realbank);
        dest.writeString(this.vip);
        dest.writeString(this.have_transpwd);
        dest.writeString(this.realliving);
        dest.writeString(this.idcardnname);
        dest.writeString(this.idcardnumber);
        dest.writeString(this.cid);
        dest.writeString(this.businesshours);
    }

    protected UserInfoBean(Parcel in) {
        this.fee = in.readString();
        this.token = in.readString();
        this.username = in.readString();
        this.realname = in.readString();
        this.realbank = in.readString();
        this.vip = in.readString();
        this.have_transpwd = in.readString();
        this.realliving = in.readString();
        this.idcardnname = in.readString();
        this.idcardnumber = in.readString();
        this.cid = in.readString();
        this.businesshours = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
