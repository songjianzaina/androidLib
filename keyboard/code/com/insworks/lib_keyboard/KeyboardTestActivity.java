package com.insworks.lib_keyboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_base.utils.ToastUtil;
import com.insworks.lib_keyboard.one.OnUserInputListener;

public class KeyboardTestActivity extends Activity implements OnUserInputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_keyboard_activity_key_test);


    }

    public void startKey(View view) {
        //弹出键盘
        EasyKeyboard.init(this).setOnUserInputListener(this).setWithdrawCash(50.0).show();
    }

    public void startKey2(View view) {
        //另一个插入式键盘
        startActivity(new Intent(getApplicationContext(),KeyboardInsertActivity.class));
    }

    @Override
    public void onUserInput(String password, double withdrawnumber) {
        ToastUtil.showToast("你输入的密码为: "+password);
    }
}
