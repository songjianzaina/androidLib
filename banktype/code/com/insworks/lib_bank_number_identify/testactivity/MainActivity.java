package com.insworks.lib_bank_number_identify.testactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.inswork.lib_cloudbase.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAuto,btnComplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banktype_activity_main);
        initView();
    }

    private void initView() {
        //输入卡号自动识别，前6或8或9可以确定是什么银行
        btnAuto = (Button) findViewById(R.id.btn_auto);
        btnComplate = (Button) findViewById(R.id.btn_complate);
        btnAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AutoIndentifyActivity.class);
                startActivity(intent);
            }
        });

        //输入完成后识别
        btnComplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ComPlateIdentifyActivity.class);
                startActivity(intent);
            }
        });
    }
}
