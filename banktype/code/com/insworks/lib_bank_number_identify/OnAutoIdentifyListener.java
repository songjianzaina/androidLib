package com.insworks.lib_bank_number_identify;

import com.insworks.lib_bank_number_identify.domain.BankCardInfo;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_bank_number_identify
 * @ClassName: OnAutoIdentifyListener
 * @Author: Song Jian
 * @CreateDate: 2019/6/6 12:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 12:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 银行卡号自动识别监听类
 */
public interface OnAutoIdentifyListener {
    void onResult(BankCardInfo bankCardInfo);
}
