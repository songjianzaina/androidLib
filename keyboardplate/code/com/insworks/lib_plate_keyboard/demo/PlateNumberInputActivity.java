package com.insworks.lib_plate_keyboard.demo;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_plate_keyboard.lib.XKeyboardView;
import com.jungly.gridpasswordview.GridPasswordView;


public class PlateNumberInputActivity extends AppCompatActivity {
    GridPasswordView gpvPlateNumber;
    XKeyboardView viewKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plate_number_input);
        testPlateNumberInput();
    }

    private void testPlateNumberInput() {
        viewKeyboard = (XKeyboardView) findViewById(R.id.view_keyboard);
        gpvPlateNumber = (GridPasswordView) findViewById(R.id.gpvPlateNumber);
        viewKeyboard.setIOnKeyboardListener(new XKeyboardView.IOnKeyboardListener() {
            @Override
            public void onInsertKeyEvent(String text) {
                gpvPlateNumber.appendPassword(text);
            }

            @Override
            public void onDeleteKeyEvent() {
                gpvPlateNumber.deletePassword();
            }
        });
        gpvPlateNumber.togglePasswordVisibility();
        gpvPlateNumber.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public boolean beforeInput(int position) {
                if (position == 0) {
                    viewKeyboard.setKeyboard(new Keyboard(PlateNumberInputActivity.this, R.xml.provice));
                    viewKeyboard.setVisibility(View.VISIBLE);
                    return true;
                } else if (position >= 1 && position < 2) {
                    viewKeyboard.setKeyboard(new Keyboard(PlateNumberInputActivity.this, R.xml.english));
                    viewKeyboard.setVisibility(View.VISIBLE);
                    return true;
                } else if (position >= 2 && position < 6) {
                    viewKeyboard.setKeyboard(new Keyboard(PlateNumberInputActivity.this, R.xml.qwerty_without_chinese));
                    viewKeyboard.setVisibility(View.VISIBLE);
                    return true;
                } else if (position >= 6 && position < 7) {
                    if (gpvPlateNumber.getPassWord().startsWith("粤Z")) {
                        viewKeyboard.setKeyboard(new Keyboard(PlateNumberInputActivity.this, R.xml.qwerty));
                    } else {
                        viewKeyboard.setKeyboard(new Keyboard(PlateNumberInputActivity.this, R.xml.qwerty_without_chinese));
                    }
                    viewKeyboard.setVisibility(View.VISIBLE);
                    return true;
                }
                viewKeyboard.setVisibility(View.GONE);
                return false;
            }

            @Override
            public void onTextChanged(String psw) {
                Log.e("PlateNumber", "onTextChanged：" + psw);
            }

            @Override
            public void onInputFinish(String psw) {
                //在这里回调最后的结果
                Log.e("PlateNumber", "onInputFinish：" + psw);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (viewKeyboard.isShown()) {
                viewKeyboard.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
