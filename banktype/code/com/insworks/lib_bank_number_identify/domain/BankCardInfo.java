package com.insworks.lib_bank_number_identify.domain;

/**
 * Created by niutingting on 2017/5/20.
 * <p>
 * 返回银行卡信息
 */
public class BankCardInfo {
    private String bankCode;
    private String bankName;
    private String cardType;
    private String cardTypeName;

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "BankCardInfo{" +
                "bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardTypeName='" + cardTypeName + '\'' +
                '}';
    }
}
