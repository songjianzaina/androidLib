package com.chhd.customkeyboard.builder;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.EditText;

import com.chhd.customkeyboard.BottomDialog;
import com.chhd.customkeyboard.CustomKeyboardView;

/**
 * PopupBuilder
 *
 * @author : 陈伟强 (2018/7/10)
 */
public class PopupBuilder {

    private boolean isVibrate = true;

    private boolean isCircle = true;

    private OnKeyClickListener onKeyClickListener;
    private OnOkClickListener onOkClickListener;
    private OnTabClickListener onTabClickListener;
    private OnFocusChangeListener onFocusChangeListener;

    public PopupBuilder() {
    }

    public PopupBuilder setVibrate(boolean isVibrate) {
        this.isVibrate = isVibrate;
        return this;
    }

    public PopupBuilder setCircle(boolean circle) {
        isCircle = circle;
        return this;
    }

    @Deprecated
    public PopupBuilder setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
        return this;
    }

    public PopupBuilder setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
        return this;
    }

    public PopupBuilder setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
        return this;
    }

    public PopupBuilder setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
        return this;
    }

    public Dialog show(View containerView) {
        Activity activity = (Activity) containerView.getContext();
        CustomKeyboardView contentView = new CustomKeyboardView(activity);
        contentView.setVibrate(isVibrate);
        contentView.setCircle(isCircle);
        contentView.setContainerView(containerView);
        final Dialog dialog = new BottomDialog(activity);
        dialog.setContentView(contentView);
        dialog.show();
        contentView.setOnKeyClickListener(new CustomKeyboardView.OnKeyClickListener() {

            @Override
            public void onTabClick(CustomKeyboardView root, EditText current) {
                if (onTabClickListener != null) {
                    onTabClickListener.onTabClick(root, current);
                }
            }

            @Override
            public void onOkClick(CustomKeyboardView root) {
                if (onKeyClickListener != null) {
                    onKeyClickListener.onOkClick(dialog, root);
                }
                if (onOkClickListener != null) {
                    onOkClickListener.onOkClick(dialog, root);
                }
            }
        });
        contentView.setOnFocusChangeListener(new CustomKeyboardView.OnFocusChangeListener() {
            @Override
            public void onFocusChange(CustomKeyboardView root, EditText current) {
                if (onFocusChangeListener != null) {
                    onFocusChangeListener.onFocusChange(root, current);
                }
            }
        });
        return dialog;
    }

    @Deprecated
    public interface OnKeyClickListener {

        @Deprecated
        void onOkClick(Dialog dialog, CustomKeyboardView root);
    }

    public interface OnOkClickListener {

        void onOkClick(Dialog dialog, CustomKeyboardView root);
    }

    public interface OnTabClickListener {

        void onTabClick(CustomKeyboardView root, EditText current);
    }

    public interface OnFocusChangeListener {

        void onFocusChange(CustomKeyboardView root, EditText current);
    }
}
