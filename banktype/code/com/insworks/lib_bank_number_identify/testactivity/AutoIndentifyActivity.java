package com.insworks.lib_bank_number_identify.testactivity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_bank_number_identify.EasyBankNumber;
import com.insworks.lib_bank_number_identify.OnAutoIdentifyListener;
import com.insworks.lib_bank_number_identify.domain.BankCardInfo;

/**
 * Created by niutingting on 2017/5/23.
 */

public class AutoIndentifyActivity extends AppCompatActivity {

    private TextInputLayout bankNumInput;
    private EditText etBankNum;
    private String bankText;
    private TextView tvBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auto_identify);
        initView();

    }

    private void initView() {
        tvBankName = (TextView) findViewById(R.id.tv_bank_name);
        bankNumInput = (TextInputLayout) findViewById(R.id.input_bank_num);
        etBankNum = bankNumInput.getEditText();

        //自动识别测试
        new EasyBankNumber(this, etBankNum, new OnAutoIdentifyListener() {
            @Override
            public void onResult(BankCardInfo bankCardInfo) {
                tvBankName.setText(bankCardInfo.getBankName());
            }
        });
    }


}
