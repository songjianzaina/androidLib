package com.insworks.ocrbaidu;

/**
 * @ProjectName: OCR_identify
 * @Package: com.ocr
 * @ClassName: OnBankIdentifyListener
 * @Author: Song Jian
 * @CreateDate: 2019/6/5 17:09
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/5 17:09
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 银行卡识别监听类
 */
public interface OnBankIdentifyListener {

    void onSuccess(String cardNumber,String cardType,String bank);

    void onfailed(String msg);
}
