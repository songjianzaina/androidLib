//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.lib_datas.bean.common;

/**
 * 网络请求返回的实体基类 网络请求的实例必须继承ResponseBean 否则无法解析 导致onsucees直接返回String
 */
public class ResponseBean extends BaseBean {
    /**
     * code : 01
     * msg : 登录成功
     * service : Login.Quickin
     * companyNo : 19001
     * encryptData : C1LsBgXu71pOFFs2V/G242iPTHLxBFe8gFERyQ06JvRNQXRcUZStFy/aObAfKDNfk2IdvdK51pcWzkfsrYM2t6is93Z6m7QH7u6QZkoKS2k=
     * nonceStr : 6G5DHpVdGFGcR6Me
     * signData : 71B18FC47827BF90107CD87558287F5C
     */

    private String code;
    private String msg;
    private String service;
    private String companyNo;
    private String encryptData;
    private String nonceStr;
    private String signData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getEncryptData() {
        return encryptData;
    }

    public void setEncryptData(String encryptData) {
        this.encryptData = encryptData;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

}
