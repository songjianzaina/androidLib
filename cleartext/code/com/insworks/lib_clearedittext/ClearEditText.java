package com.insworks.lib_clearedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_clearedittext.formatter.InputTextFormatter;
import com.insworks.lib_clearedittext.formatter.InputTextFormatterFactory;

import static com.insworks.lib_clearedittext.Constants.TYPE_TEXT;

/**
 * 带清除按钮，支持分段显示的输入框。
 * git@github.com:cxyzy1/clearEditText.git
 */
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener, TextWatcher {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;
    private int start, count, before;
    private int showType;
    private String textFormat;
    private InputTextFormatter inputTextFormatter;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(final Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        showType = typedArray.getInt(R.styleable.ClearEditText_showType, TYPE_TEXT);
        textFormat = typedArray.getString(R.styleable.ClearEditText_textFormat);

        inputTextFormatter = InputTextFormatterFactory.getFormatter(showType);

        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(getContext(), R.mipmap.cet_icon_delete);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);

        setAllowableCharacters();
        setTextFormat();
        setInputFilter();

    }

    private void setAllowableCharacters() {
        String allowableCharacters = inputTextFormatter.getAllowableCharacters();
        if (!TextUtils.isEmpty(allowableCharacters)) {
            if (!allowableCharacters.contains(" ")) {
                allowableCharacters = allowableCharacters + " ";
            }
            setKeyListener(DigitsKeyListener.getInstance(allowableCharacters));
        }
    }

    private void setTextFormat() {
        if (!TextUtils.isEmpty(textFormat)) {
            inputTextFormatter.setTextFormat(textFormat);
        }
    }

    private void setInputFilter() {
        if (getFilters() == null || getFilters().length == 0) {
            InputFilter[] filters = inputTextFormatter.getInputFilter();
            if (filters != null && filters.length != 0) {
                setFilters(filters);
            }
        }
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        if (hasFocus) {
            setClearIconVisible(s.length() > 0);
        }
        this.start = start;
        this.before = before;
        this.count = count;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        inputTextFormatter.format(this, this, start, before, count);
    }

    /**
     * 获取不含空格的文本内容
     * @return 不含空格的文本内容
     */
    public String getTextWithoutBlanks() {
        String result = null;
        Editable editable = getText();
        if (editable != null) {
            result = editable.toString().replaceAll(" ", "");
        }
        return result;
    }

}
