package com.insworks.lib_keyboard;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.widget.Toast;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_keyboard.one.InputBusinessBind;
import com.insworks.lib_keyboard.one.OnUserInputListener;

/**
 * @ProjectName: tftpay
 * @Package: com.wintone.lib_keyboard
 * @ClassName: KeyboardInsertActivity
 * @Author: Song Jian
 * @CreateDate: 2019/6/6 14:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/6 14:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 插入式的自定义键盘 演示界面
 */
public class KeyboardInsertActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_keyboard_business_pwd_reset);
        new InputBusinessBind(this).setOnUserInputListener(new OnUserInputListener() {
            @Override
            public void onUserInput(String password, double withdrawnumber) {
                Toast.makeText(KeyboardInsertActivity.this, "密码: "+password, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
