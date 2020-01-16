package com.insworks.lib_bank_number_identify.testactivity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_bank_number_identify.domain.BankCardInfo;
import com.insworks.lib_bank_number_identify.util.CheckBankCardUtil;
import com.insworks.lib_bank_number_identify.util.CheckBankNameUtil;

import java.util.regex.Pattern;

/**
 * Created by niutingting on 2017/5/23.
 */

public class ComPlateIdentifyActivity extends AppCompatActivity {
    private TextInputLayout bankNumInput;
    private EditText etBankNum;
    private String bankText;
    private TextView tvBankName;
    private Button btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complate_identify);
        initView();
    }

    private void initView() {
        tvBankName = (TextView) findViewById(R.id.tv_bank_name);
        bankNumInput = (TextInputLayout) findViewById(R.id.input_bank_num);
        etBankNum = bankNumInput.getEditText();
        etBankNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bankNumInput.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                bankText = editable.toString();
            }
        });

        btnCheck = (Button) findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(bankText)) {
                    checkBankName(bankText);
                } else {
                    Toast.makeText(ComPlateIdentifyActivity.this, "请输入银行卡号码", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    private void checkBankName(String bankText) {

        Pattern pattern = Pattern.compile("[0-9]*");
        if (!pattern.matcher(bankText).matches()) {
            bankNumInput.setError("银行卡号必须是数字");

        } else if (bankText.length() < 15 || bankText.length() > 19) {
            bankNumInput.setError("银行卡位数必须是15到19位");

        } else if (!CheckBankCardUtil.checkBankCard(bankText)) {
            bankNumInput.setError("卡号不合规");

        } else {
            BankCardInfo bankCardInfo = null;
            try {
                bankCardInfo = new
                        CheckBankNameUtil().getBankCardInfo(this, bankText);

                if (bankCardInfo == null) {//返回为null，代表没有识别到
                    tvBankName.setText("没有识别到");
                } else {//识别到了
                    String bankName = bankCardInfo.getBankName();
                    String bankType = bankCardInfo.getCardType();
                    String bankCode = bankCardInfo.getBankCode();
                    String cardTypeName = bankCardInfo.getCardTypeName();
                    tvBankName.setText("bankName:" + bankName + "---bankCode:" + bankCode
                            + "---bankType:" + bankType + "---cardTypeName:" + cardTypeName);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
