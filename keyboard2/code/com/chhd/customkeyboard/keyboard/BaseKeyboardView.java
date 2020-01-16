package com.chhd.customkeyboard.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.EditText;

import com.chhd.customkeyboard.KeyboardUtils;
import com.inswork.lib_cloudbase.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * BaseKeyboardView
 *
 * @author : 葱花滑蛋 (2018/7/11)
 */
public abstract class BaseKeyboardView extends KeyboardView
        implements KeyboardView.OnKeyboardActionListener {

    protected final int KEYCODE_INVALID = -9999;
    protected final int KEYCODE_TAB = -9998;
    protected final int KEYCODE_NUMBER = 123123;
    protected final int KEYCODE_ABC = 789789;

    protected EditText editText;

    public void attachTo(EditText editText) {
        this.editText = editText;
    }

    private boolean isVibrate = true;

    public void setVibrate(boolean isVibrate) {
        this.isVibrate = isVibrate;
    }

    protected boolean isLast = true;

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    protected OnKeyClickListener onKeyClickListener;

    public void setOnKeyClickListener(OnKeyClickListener onKeyClickListener) {
        this.onKeyClickListener = onKeyClickListener;
    }

    public BaseKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void drawNotTouchKey(Keyboard.Key key, Canvas canvas) {
        if (!isValidKey(key)) {
            drawKeyBackground(R.drawable.bg_not_touch, canvas, key);
        }
    }

    void drawDoneKey(Keyboard.Key key, Canvas canvas) {
        int backgroundColor = getBackgroundColor();
        int contentColor = getContext().getResources().getColor(R.color.color_done_content);
        if (isLast) {
            if (key.codes[0] == KEYCODE_TAB) {
                if (backgroundColor != -1) {
                    drawKeyBackgroundDrawable(buildBGDrawable(backgroundColor), canvas, key);
                    drawContent(canvas, key, contentColor);
                }
            }
        } else {
            if (key.codes[0] == Keyboard.KEYCODE_DONE) {
                if (backgroundColor != -1) {
                    drawKeyBackgroundDrawable(buildBGDrawable(backgroundColor), canvas, key);
                    drawContent(canvas, key, contentColor);
                }
            }
        }
    }

    private void drawContent(Canvas canvas, Keyboard.Key key, int color) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setAntiAlias(true);

        paint.setColor(color);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) {
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    keyTextSize = (int) field.get(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            int left = key.x + (key.width - key.icon.getIntrinsicWidth()) / 2;
            int top = key.y + (key.height - key.icon.getIntrinsicHeight()) / 2;
            int right = key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth();
            int bottom = key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight();
            key.icon.setBounds(left, top, right, bottom);
            key.icon.draw(canvas);
            DrawableCompat.setTint(key.icon, color);
        }
    }

    protected Drawable buildBGDrawable(int color) {
        int roundRadius = KeyboardUtils.dp2px(getContext(), 4);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(color);
        gd.setCornerRadius(roundRadius);
        return gd;
    }

    protected int getBackgroundColor() {
        /*TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();*/
        return getContext().getResources().getColor(R.color.color_done_background);
    }

    protected void drawKeyBackgroundDrawable(Drawable npd, Canvas canvas, Keyboard.Key key) {
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        npd.draw(canvas);
    }

    private boolean isValidKey(Keyboard.Key key) {
        return key != null && (!TextUtils.isEmpty(key.label) || key.icon != null);
    }

    protected void drawKeyBackground(int drawableId, Canvas canvas, Keyboard.Key key) {
        Drawable npd = getContext().getResources().getDrawable(
                drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        npd.setBounds(key.x, key.y, key.x + key.width, key.y
                + key.height);
        npd.draw(canvas);
    }

    protected Keyboard.Key getKey(int code) {
        List<Keyboard.Key> keyList = getKeyboard().getKeys();
        for (Keyboard.Key key : keyList) {
            if (key.codes[0] == code) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void onPress(int primaryCode) {
        Keyboard.Key key = getKey(primaryCode);
        if (isValidKey(key)) {
            vibrate();
        }
    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_SHIFT) {
            // 大写
        } else if (primaryCode == Keyboard.KEYCODE_CANCEL) {
            // 返回
        } else if (primaryCode == Keyboard.KEYCODE_DONE) {
            // 确定
            if (onKeyClickListener != null) {
                onKeyClickListener.onOkClick();
            }
        } else if (primaryCode == KEYCODE_INVALID) {
            // 无效
        } else if (primaryCode == KEYCODE_TAB) {
            // 下一步
            if (onKeyClickListener != null) {
                if (isLast) {
                    onKeyClickListener.onOkClick();
                } else {
                    onKeyClickListener.onTabClick();
                }
            }
        } else if (primaryCode == Keyboard.KEYCODE_DELETE) {
            // 回退
            if (editText != null && editText.getText().length() > 0) {
                int start = editText.getSelectionStart();
                if (start > 0) {
                    editText.getText().delete(start - 1, start);
                }
            }
        } else if (primaryCode == KEYCODE_NUMBER) {
            // 字母键盘
        } else if (primaryCode == KEYCODE_ABC) {
            // 数字键盘
        } else {
            if (editText != null) {
                int start = editText.getSelectionStart();
                char c = (char) primaryCode;
                editText.getText().insert(start, c + "");
            }
        }
    }

    protected void vibrate() {
        if (isVibrate) {
            Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                int millis = getContext().getResources().getInteger(R.integer.vibrate_millis);
                vibrator.vibrate(millis);
            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public interface OnKeyClickListener {

        void onTabClick();

        void onOkClick();
    }
}
