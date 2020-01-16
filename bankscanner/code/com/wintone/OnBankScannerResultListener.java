package com.wintone;

/**
 * @ProjectName: tftpay
 * @Package: com.wintone
 * @ClassName: OnBankScannerResultListener
 * @Author: Song Jian
 * @CreateDate: 2019/6/6 10:56
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 10:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 银行卡扫描结果监听类
 */
public interface OnBankScannerResultListener {

    void onResult(int[] cardPic, char[] cardNumber, String cardNumberStr);

}
