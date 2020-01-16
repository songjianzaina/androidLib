package com.insworks.lib_bank_number_identify.domain;

import java.util.List;

/**
 * Created by niutingting on 2017/5/20.
 * <p>
 * 银行卡信息
 */
public class BankCardInfoPatten {

    /**
     * bankName : 邮政储蓄银行
     * bankCode : PSBC
     * patterns : [{"reg":"(621096|621098|622150|622151|622181|622188|622199|955100|621095|620062|621285|621798|621799|621797|620529|621622|621599|621674|623218|623219)\\d{13}$","cardType":"DC"},{"reg":"(62215049|62215050|62215051|62218850|62218851|62218849)\\d{11}$","cardType":"DC"},{"reg":"(622812|622810|622811|628310|625919)\\d{10}$","cardType":"CC"}]
     */

    private String bankName;
    private String bankCode;
    private List<PatternsBean> patterns;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public List<PatternsBean> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternsBean> patterns) {
        this.patterns = patterns;
    }

    public static class PatternsBean {
        /**
         * reg : (621096|621098|622150|622151|622181|622188|622199|955100|621095|620062|621285|621798|621799|621797|620529|621622|621599|621674|623218|623219)\d{13}$
         * cardType : DC
         */

        private String reg;
        private String cardType;

        public String getReg() {
            return reg;
        }

        public void setReg(String reg) {
            this.reg = reg;
        }

        public String getCardType() {
            return cardType;
        }

        public void setCardType(String cardType) {
            this.cardType = cardType;
        }
    }
}
