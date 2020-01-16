package com.insworks.multipletheme.colorUi.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.insworks.multipletheme.colorUi.ColorUiInterface;
import com.insworks.multipletheme.colorUi.util.ViewAttributeUtil;


/**
 * Created by chengli on 15/6/8.
 */
public class ColorButton extends Button implements ColorUiInterface {

    private int attr_background = -1;
    private int attr_textAppreance = -1;
    private int attr_textColor = -1;

    public ColorButton(Context context) {
        super(context);
    }

    public ColorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
        this.attr_textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    public ColorButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        this.attr_textAppreance = ViewAttributeUtil.getTextApperanceAttribute(attrs);
        this.attr_textColor = ViewAttributeUtil.getTextColorAttribute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attr_textAppreance != -1) {
            ViewAttributeUtil.applyTextAppearance(this, themeId, attr_textAppreance);
        }
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
        if (attr_textColor != -1) {
            ViewAttributeUtil.applyTextColor(this, themeId, attr_textColor);
        }
    }
}
