package com.insworks.lib_bank_number_identify;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.insworks.lib_bank_number_identify.domain.BankCardInfo;
import com.insworks.lib_bank_number_identify.util.CheckBankNameUtil;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_bank_number_identify
 * @ClassName: EasyBankNumber
 * @Author: Song Jian
 * @CreateDate: 2019/6/6 12:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 12:08
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 用户输入 银行卡号识别 入口类
 */
public class EasyBankNumber {
    private Context context;
    private EditText etBankNum;
    private OnAutoIdentifyListener listener;
    private String bankText;

    public EasyBankNumber(Context context, EditText etBankNum, OnAutoIdentifyListener listener) {
        this.context = context;
        this.etBankNum = etBankNum;
        this.listener = listener;

        initView();
    }

    private void initView() {
        etBankNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bankText = editable.toString().replace(" ","");
                if (bankText.length() == 10) {
                    checkBankName();
                }
            }
        });
    }

    /**
     * 识别银行卡名称
     */
    private void checkBankName() {
        BankCardInfo bankCardInfo = null;
        try {
            bankCardInfo = new
                    CheckBankNameUtil().getBankCardInfo(context, bankText);

            if (listener != null) {
                listener.onResult(bankCardInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
