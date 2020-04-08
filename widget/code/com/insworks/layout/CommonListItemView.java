//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;

public class CommonListItemView extends RelativeLayout {
    public static final int LIST_ITEM_VIEW_STYLE_SINGLE_ROW = 1;
    public static final int LIST_ITEM_VIEW_STYLE_DOUBLE_ROW = 2;
    private RelativeLayout mRootView;
    private ImageView mLeftIcon;
    private View mTextContainer;
    private TextView mText;
    private TextView mDetailText;
    private TextView mRightText;
    private ImageView mRightIcon;
    private ImageView mTipDot;
    private SwitchButton mSwitchButton;

    public CommonListItemView(Context context) {
        super(context);
        this.initView();
    }

    public CommonListItemView(Context context, int rowStyle) {
        super(context);
        this.initView();
        this.setStyle(rowStyle);
    }

    public CommonListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView();
        this.setAttrs(context, attrs);
    }

    public CommonListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView();
        this.setAttrs(context, attrs);
    }

    private void initView() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.view_common_item_list, this, true);
        this.mRootView = (RelativeLayout)this.findViewById(R.id.root_container);
        this.mTextContainer = this.findViewById(R.id.ll_text_container);
        this.mLeftIcon = (ImageView)this.findViewById(R.id.view_left_icon);
        this.mText = (TextView)this.findViewById(R.id.view_title_text);
        this.mDetailText = (TextView)this.findViewById(R.id.view_descript_text);
        this.mRightText = (TextView)this.findViewById(R.id.view_right_text);
        this.mRightIcon = (ImageView)this.findViewById(R.id.view_right_icon);
        this.mTipDot = (ImageView)this.findViewById(R.id.view_tip_dot);
        this.mSwitchButton = (SwitchButton)this.findViewById(R.id.switch_button);
    }

    public void addRightView(View rightView) {
        LayoutParams params = (LayoutParams)rightView.getLayoutParams();
        this.addRightView(rightView, params);
    }

    public void addRightView(View rightView, LayoutParams params) {
        if (params != null) {
            params.addRule(11);
            this.mRootView.addView(rightView);
        }

    }

    public void hideTipDot() {
        this.mTipDot.setVisibility(INVISIBLE);
    }

    public void showTipDot() {
        this.mTipDot.setVisibility(VISIBLE);
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonListItemView);
        int textColor;
        if (a.hasValue(R.styleable.CommonListItemView_item_style)) {
            textColor = a.getInt(R.styleable.CommonListItemView_item_style, 1);
            this.setStyle(textColor);
        }

        Drawable d;
        if (a.hasValue(R.styleable.CommonListItemView_leftSrc)) {
            d = a.getDrawable(R.styleable.CommonListItemView_leftSrc);
            this.mLeftIcon.setImageDrawable(d);
            this.mLeftIcon.setVisibility(VISIBLE);
            LayoutParams params = (LayoutParams)this.mTextContainer.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            this.mTextContainer.setLayoutParams(params);
        }

        String description;
        if (a.hasValue(R.styleable.CommonListItemView_text)) {
            description = a.getString(R.styleable.CommonListItemView_text);
            this.mText.setText(description);
        }

        if (a.hasValue(R.styleable.CommonListItemView_mainTextColor)) {
            textColor = a.getColor(R.styleable.CommonListItemView_mainTextColor, -65536);
            this.mText.setTextColor(textColor);
        }

        if (a.hasValue(R.styleable.CommonListItemView_hint)) {
            description = a.getString(R.styleable.CommonListItemView_hint);
            this.mText.setHint(description);
        }

        if (a.hasValue(R.styleable.CommonListItemView_co_textSize)) {
            this.mText.setTextSize(0, a.getDimension(R.styleable.CommonListItemView_co_textSize, 0.0F));
        }

        if (a.hasValue(R.styleable.CommonListItemView_detailText)) {
            description = a.getString(R.styleable.CommonListItemView_detailText);
            this.setDetailText(description);
        }

        if (a.hasValue(R.styleable.CommonListItemView_rightSrc)) {
            d = a.getDrawable(R.styleable.CommonListItemView_rightSrc);
            this.mRightIcon.setImageDrawable(d);
            this.mRightIcon.setVisibility(VISIBLE);
        }

        if (a.hasValue(R.styleable.CommonListItemView_rightVisibility)) {
            this.mRightIcon.setVisibility(a.getInt(R.styleable.CommonListItemView_rightVisibility, 0));
        }

        if (a.hasValue(R.styleable.CommonListItemView_rightText)) {
            this.mRightText.setVisibility(VISIBLE);
            this.mRightText.setText(a.getString(R.styleable.CommonListItemView_rightText));
            if (this.mRightIcon.getVisibility() != VISIBLE) {
                this.updateRightTextPos();
            }
        }

        if (a.hasValue(R.styleable.CommonListItemView_rightTextColor)) {
            textColor = a.getColor(R.styleable.CommonListItemView_rightTextColor, -65536);
            this.mRightText.setTextColor(textColor);
        }

        a.recycle();
    }

    private void updateRightTextPos() {
        int rightIconVisibility = this.mRightIcon.getVisibility();
        LayoutParams params = (LayoutParams)this.mRightText.getLayoutParams();
        if (rightIconVisibility != 0) {
            params.rightMargin = this.getResources().getDimensionPixelSize(R.dimen.item_list_padding);
            params.addRule(11);
            this.mRightText.setLayoutParams(params);
        } else {
            params.rightMargin = this.getResources().getDimensionPixelSize(R.dimen.item_list_content_padding_s);
            params.addRule(11, 0);
            this.mRightText.setLayoutParams(params);
        }

    }

    private void setViewVisibility(boolean visible, View view) {
        if (visible) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }

    }

    public void setStyle(int itemStyle) {
        int viewHeight = 0;
        int leftSize = 0;
        switch(itemStyle) {
        case 1:
            viewHeight = this.getResources().getDimensionPixelOffset(R.dimen.item_list_row_height);
            leftSize = this.getResources().getDimensionPixelSize(R.dimen.ic_1);
            this.mDetailText.setVisibility(GONE);
            break;
        case 2:
            viewHeight = this.getResources().getDimensionPixelOffset(R.dimen.item_list_double_row_height);
            leftSize = this.getResources().getDimensionPixelSize(R.dimen.at_1);
            this.mDetailText.setVisibility(VISIBLE);
        }

        this.setRootHeight(viewHeight);
        this.setLeftIconSize(leftSize);
    }

    private void setRootHeight(int height) {
        LayoutParams params = (LayoutParams)this.mRootView.getLayoutParams();
        params.height = height;
        this.mRootView.setLayoutParams(params);
    }

    public void setRootMargin(int left, int top, int right, int bottom) {
        LayoutParams params = (LayoutParams)this.mRootView.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        this.mRootView.setLayoutParams(params);
    }

    public void setLeftIconSize(int size) {
        LayoutParams leftParams = (LayoutParams)this.mLeftIcon.getLayoutParams();
        leftParams.width = size;
        leftParams.height = size;
        this.mLeftIcon.setLayoutParams(leftParams);
    }

    public void setLeftIconResource(int resId) {
        this.mLeftIcon.setImageResource(resId);
        this.setLeftIconVisibility(0);
    }

    public void setLeftIconVisibility(int visibility) {
        this.mLeftIcon.setVisibility(visibility);
        LayoutParams params = (LayoutParams)this.mTextContainer.getLayoutParams();
        if (visibility == 0) {
            params.setMargins(0, 0, 0, 0);
        } else {
            params.setMargins(this.getResources().getDimensionPixelSize(R.dimen.item_list_padding), 0, 0, 0);
        }

        this.mTextContainer.setLayoutParams(params);
    }

    public ImageView getLeftImageView() {
        return this.mLeftIcon;
    }

    public void setText(CharSequence text) {
        this.mText.setText(text);
    }

    public void setText(int resId) {
        this.mText.setText(resId);
    }

    public CharSequence getText() {
        return this.mText.getText();
    }

    public void setDetailText(CharSequence text) {
        this.mDetailText.setText(text);
        this.setDetailTextVisibility(0);
    }

    public void setDetailText(int resId) {
        this.mDetailText.setText(resId);
        this.setDetailTextVisibility(0);
    }

    public void setDetailTextVisibility(int visibility) {
        this.mDetailText.setVisibility(visibility);
    }

    public void setRightIconResource(int resId) {
        this.mRightIcon.setImageResource(resId);
        this.setRightIconVisibility(0);
    }

    public void setRightIconResource(Bitmap bitmap) {
        this.mRightIcon.setImageBitmap(bitmap);
        this.setRightIconVisibility(0);
    }

    public ImageView getRightImageView() {
        return this.mRightIcon;
    }

    public void setRightIconVisibility(int visibility) {
        this.mRightIcon.setVisibility(visibility);
        if (visibility != 0) {
            this.updateRightTextPos();
        }

    }

    public void setRightText(String rightText) {
        this.setRightText(rightText, "");
    }

    public void setRightText(String rightText, String textColor) {
        this.mRightText.setText(rightText);
        if (!TextUtils.isEmpty(textColor)) {
            this.mRightText.setTextColor(Color.parseColor(textColor));
        }

        if (this.mRightText.getVisibility() != VISIBLE) {
            this.mRightText.setVisibility(VISIBLE);
            this.updateRightTextPos();
        }

    }

    public View getTextContainer() {
        return this.mTextContainer;
    }

    public TextView getRightTextView() {
        return this.mRightText;
    }

    public CharSequence getRightText() {
        return this.mRightText.getText();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mSwitchButton.setOnCheckedChangeListener(listener);
        this.mSwitchButton.setVisibility(VISIBLE);
    }

    public SwitchButton getSwitch() {
        return this.mSwitchButton;
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        this.mRootView.setBackgroundResource(resid);
    }

    public void setTextColor(int color) {
        this.mText.setTextColor(color);
    }
}
