package com.insworks.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @ProjectName: tftpay
 * @Package: com.qtopays.yunzf.widget
 * @ClassName: HideTextView
 * @Author: Song Jian
 * @CreateDate: 2019/6/12 14:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/12 14:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 带有隐藏功能的TextView
 */
public class HideTextView extends AppCompatTextView {

    protected String visibleText;
    private boolean isHide;

    public HideTextView(Context context) {
        this(context, null);
    }

    public HideTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);



    }




    public void hideText(boolean isHide) {
        this.isHide = isHide;
        if (!getText().toString().contains("*")) {
            visibleText = getText().toString();
        }
        if (isHide) {
            //隐藏文本
            setText(hideAll(getText().toString()));
        } else {
            //显示文本
            setText(visibleText);
        }
    }

    /**
     * 文本是否被隐藏了
     *
     * @return
     */
    public boolean isHide() {
        return isHide;
    }

    /**
     * 隐藏开关
     */
    public void toggle() {
        if (isHide) {
            //显示文本
            hideText(false);
        } else {
            //隐藏文本
            hideText(true);
        }
    }

    /**
     * 隐藏所有字符
     *
     * @param data
     * @return
     */
    public String hideAll(String data) {
        if (data == null) {
            return data;
        }

        int length = data.length();
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(replaceSymbol);
        }

        return sb.toString();
    }
}
