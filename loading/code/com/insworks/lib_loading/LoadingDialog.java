package com.insworks.lib_loading;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;

/**
 * 自定义加载进度对话框
 * Created by Dylan on 2016-10-28.
 */

public class LoadingDialog extends Dialog {
    private TextView tvRightText;
    private LinearLayout llStyleVertical;
    private LinearLayout llStyleHorizontal;
    private TextView tv_text;
    public static int loadingStyle_HORIZONTAL = 0;
    public static int loadingStyle_VERTICAL = 1;
    private Activity activity;

    public LoadingDialog(Activity activity) {

        super(activity);
        this.activity = activity;
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //大版面样式
//        setContentView(R.layout.lib_loading_loading);
//        styleTwo();
        styleThree();
//        styleFour();
        setCanceledOnTouchOutside(false);
    }

    /**
     * 垂直方向的加载框 样式不同而已
     */
    private void styleThree() {
        setContentView(R.layout.lib_loading_loading3);
        tv_text = (TextView) findViewById(R.id.tv_text);

    }

    /**
     * 单纯原型旋转
     */
    private void styleFour() {
        setContentView(R.layout.lib_loading_loading4);

    }

    /**
     * 带有垂直和水平两种方向的加载框  和setStyle配合使用
     */
    private void styleTwo() {
        setContentView(R.layout.lib_loading_loading2);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tvRightText = (TextView) findViewById(R.id.tv_right_text);
        llStyleHorizontal = (LinearLayout) findViewById(R.id.ll_style_horizontal);
        llStyleVertical = (LinearLayout) findViewById(R.id.ll_style_vertical);
    }


    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public LoadingDialog setMessage(String message) {
        if (message == null) {
            //如果设置为null 那么只显示圆圈
            tv_text.setVisibility(View.GONE);
            tvRightText.setVisibility(View.GONE);
            return this;
        }
        tv_text.setText(message);
        tvRightText.setText(message);
        return this;
    }

    /**
     * 设置加载框样式  垂直 或者 水平  默认垂直方向
     *
     * @param loadingStyle
     * @return
     */
    public LoadingDialog setStyle(int loadingStyle) {
        if (loadingStyle == loadingStyle_HORIZONTAL) {
            //水平方向
            llStyleHorizontal.setVisibility(View.VISIBLE);
            llStyleVertical.setVisibility(View.GONE);
        } else {
            //垂直方向
            llStyleHorizontal.setVisibility(View.GONE);
            llStyleVertical.setVisibility(View.VISIBLE);
        }
        return this;
    }

    @Override
    public void show() {
        if (!activity.isFinishing()) {

            super.show();
        }
    }
}
