package com.insworks.lib_keyboard.one;

import android.app.Activity;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.event.BusEvent;
import com.inswork.lib_cloudbase.event.EventCode;
import com.insworks.lib_datas.utils.KeyboardUtil;

/**
 * 直接传入包含键盘和密码框的布局  适用于自定义
 */

public class InputBusinessBind  implements View.OnClickListener {
    private static final String TAG = "InputBusinessBind";

    private Activity mContext;


    EditText pwd1;
    EditText pwd2;
    EditText pwd3;
    EditText pwd4;
    EditText pwd5;
    EditText pwd6;
    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;
    TextView number5;
    TextView number6;
    TextView number7;
    TextView number8;
    TextView number9;
    TextView number0;
    ImageView delete;

    private int currentPwd = 1;
    private OnUserInputListener listeneristener;


    public InputBusinessBind(@NonNull Activity activity) {
        mContext = activity;
        initView();
        KeyboardUtil.hideKeyBoard(mContext);
    }


    private void initView() {
        pwd1 = mContext.findViewById(R.id.pwd1);
        pwd2 = mContext.findViewById(R.id.pwd2);
        pwd3 = mContext.findViewById(R.id.pwd3);
        pwd4 = mContext.findViewById(R.id.pwd4);
        pwd5 = mContext.findViewById(R.id.pwd5);
        pwd6 = mContext.findViewById(R.id.pwd6);
        number0 = mContext.findViewById(R.id.number0);
        number1 = mContext.findViewById(R.id.number1);
        number2 = mContext.findViewById(R.id.number2);
        number3 = mContext.findViewById(R.id.number3);
        number4 = mContext.findViewById(R.id.number4);
        number5 = mContext.findViewById(R.id.number5);
        number6 = mContext.findViewById(R.id.number6);
        number7 = mContext.findViewById(R.id.number7);
        number8 = mContext.findViewById(R.id.number8);
        number9 = mContext.findViewById(R.id.number9);
        delete = mContext.findViewById(R.id.delete);

        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        delete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            if (currentPwd > 1) {
                currentPwd--;
                getCurrentPwdView().setText("");
            }
            return;
        }
        TextView currentView = getCurrentPwdView();
        if (currentView == null) {
            return;
        }
        int i = v.getId();
        if (i == R.id.number1) {
            currentView.setText("1");
        } else if (i == R.id.number2) {
            currentView.setText("2");
        } else if (i == R.id.number3) {
            currentView.setText("3");
        } else if (i == R.id.number4) {
            currentView.setText("4");
        } else if (i == R.id.number5) {
            currentView.setText("5");
        } else if (i == R.id.number6) {
            currentView.setText("6");
        } else if (i == R.id.number7) {
            currentView.setText("7");
        } else if (i == R.id.number8) {
            currentView.setText("8");
        } else if (i == R.id.number9) {
            currentView.setText("9");
        } else if (i == R.id.number0) {
            currentView.setText("0");
        }
        currentPwd++;
        if (currentPwd > 6) {
            resetBusinessPwd();
        }
    }

    private TextView getCurrentPwdView() {
        int id = mContext.getResources().getIdentifier("pwd" + currentPwd, "id", mContext.getPackageName());
        return (TextView) mContext.findViewById(id);
    }

    public InputBusinessBind setOnUserInputListener(OnUserInputListener listeneristener) {

        this.listeneristener = listeneristener;
        return this;
    }

    /**
     * 密码输满就启动回调
     */
    private void resetBusinessPwd() {
        StringBuilder newPwd = new StringBuilder();
        newPwd.append(pwd1.getText());
        newPwd.append(pwd2.getText());
        newPwd.append(pwd3.getText());
        newPwd.append(pwd4.getText());
        newPwd.append(pwd5.getText());
        newPwd.append(pwd6.getText());
        if (listeneristener != null) {
            listeneristener.onUserInput(newPwd.toString(),0);
        }
        //将密码传递至立即提现界面
        BusEvent.send(EventCode.WITHDRAW, newPwd.toString());
        //清空密码框
        clearAllEdit();
    }

    private void clearAllEdit() {
        pwd1.setText("");
        pwd2.setText("");
        pwd3.setText("");
        pwd4.setText("");
        pwd5.setText("");
        pwd6.setText("");
        //光标重置为第一格
        currentPwd = 1;
    }

}
