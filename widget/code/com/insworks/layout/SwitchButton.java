//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.insworks.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;

import androidx.core.content.ContextCompat;

import com.inswork.lib_cloudbase.R;
import com.inswork.lib_cloudbase.utils.ResourceUtils;
import com.nineoldandroids.animation.ObjectAnimator;

public class SwitchButton extends CompoundButton {
    public static final float DEFAULT_BACK_MEASURE_RATIO = 1.8F;
    public static final int DEFAULT_THUMB_SIZE_DP = 20;
    public static final int DEFAULT_THUMB_MARGIN_DP = 0;
    public static final int DEFAULT_ANIMATION_DURATION = 250;
    public static final int DEFAULT_TINT_COLOR = 3309506;
    private static int[] CHECKED_PRESSED_STATE = new int[]{16842912, 16842910, 16842919};
    private static int[] UNCHECKED_PRESSED_STATE = new int[]{-16842912, 16842910, 16842919};
    private Drawable mThumbDrawable;
    private Drawable mBackDrawable;
    private ColorStateList mBackColor;
    private ColorStateList mThumbColor;
    private float mThumbRadius;
    private float mBackRadius;
    private RectF mThumbMargin;
    private float mBackMeasureRatio;
    private long mAnimationDuration;
    private boolean mFadeBack;
    private int mTintColor;
    private PointF mThumbSizeF;
    private int mCurrThumbColor;
    private int mCurrBackColor;
    private int mNextBackColor;
    private Drawable mCurrentBackDrawable;
    private Drawable mNextBackDrawable;
    private RectF mThumbRectF;
    private RectF mBackRectF;
    private RectF mSafeRectF;
    private Paint mPaint;
    private boolean mIsThumbUseDrawable;
    private boolean mIsBackUseDrawable;
    private boolean mDrawDebugRect = false;
    private ObjectAnimator mProcessAnimator;
    private float mProcess;
    private RectF mPresentThumbRectF;
    private float mStartX;
    private float mStartY;
    private float mLastX;
    private int mTouchSlop;
    private int mClickTimeout;
    private Paint mRectPaint;

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(attrs);
    }

    public SwitchButton(Context context) {
        super(context);
        this.init((AttributeSet)null);
    }

    private void init(AttributeSet attrs) {
        this.mTouchSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mPaint = new Paint(1);
        this.mRectPaint = new Paint(1);
        this.mRectPaint.setStyle(Style.STROKE);
        this.mRectPaint.setStrokeWidth(this.getResources().getDisplayMetrics().density);
        this.mThumbRectF = new RectF();
        this.mBackRectF = new RectF();
        this.mSafeRectF = new RectF();
        this.mThumbSizeF = new PointF();
        this.mThumbMargin = new RectF();
        this.mProcessAnimator = ObjectAnimator.ofFloat(this, "process", new float[]{0.0F, 0.0F}).setDuration(250L);
        this.mProcessAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mPresentThumbRectF = new RectF();
        Resources res = this.getResources();
        float density = res.getDisplayMetrics().density;
        Drawable thumbDrawable = null;
        ColorStateList thumbColor = null;
        float margin = density * 0.0F;
        float marginLeft = 0.0F;
        float marginRight = 0.0F;
        float marginTop = 0.0F;
        float marginBottom = 0.0F;
        float thumbWidth = density * 20.0F;
        float thumbHeight = density * 20.0F;
        float thumbRadius = density * 20.0F / 2.0F;
        float backRadius = thumbRadius;
        Drawable backDrawable = null;
        ColorStateList backColor = null;
        float backMeasureRatio = 1.8F;
        int animationDuration = 250;
        boolean fadeBack = true;
        int tintColor = -2147483648;
        TypedArray ta = attrs == null ? null : this.getContext().obtainStyledAttributes(attrs, R.styleable.SwitchButton);
        if (ta != null) {
            thumbDrawable = ta.getDrawable(R.styleable.SwitchButton_kswThumbDrawable);
            thumbColor = ta.getColorStateList(R.styleable.SwitchButton_kswThumbColor);
            margin = ta.getDimension(R.styleable.SwitchButton_kswThumbMargin, margin);
            marginLeft = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginLeft, margin);
            marginRight = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginRight, margin);
            marginTop = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginTop, margin);
            marginBottom = ta.getDimension(R.styleable.SwitchButton_kswThumbMarginBottom, margin);
            thumbWidth = ta.getDimension(R.styleable.SwitchButton_kswThumbWidth, thumbWidth);
            thumbHeight = ta.getDimension(R.styleable.SwitchButton_kswThumbHeight, thumbHeight);
            thumbRadius = ta.getDimension(R.styleable.SwitchButton_kswThumbRadius, Math.min(thumbWidth, thumbHeight) / 2.0F);
            backRadius = ta.getDimension(R.styleable.SwitchButton_kswBackRadius, thumbRadius + density * 2.0F);
            backDrawable = ta.getDrawable(R.styleable.SwitchButton_kswBackDrawable);
            backColor = ta.getColorStateList(R.styleable.SwitchButton_kswBackColor);
            backMeasureRatio = ta.getFloat(R.styleable.SwitchButton_kswBackMeasureRatio, backMeasureRatio);
            animationDuration = ta.getInteger(R.styleable.SwitchButton_kswAnimationDuration, animationDuration);
            fadeBack = ta.getBoolean(R.styleable.SwitchButton_kswFadeBack, true);
            tintColor = ta.getColor(R.styleable.SwitchButton_kswTintColor, tintColor);
            ta.recycle();
        }

        this.mThumbDrawable = thumbDrawable;
        this.mThumbColor = thumbColor;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        this.mTintColor = tintColor;
        if (this.mTintColor == -2147483648) {
            this.mTintColor = 3309506;
        }

        if (!this.mIsThumbUseDrawable && this.mThumbColor == null) {
            this.mThumbColor = ResourceUtils.generateThumbColorWithTintColor(this.mTintColor);
            this.mCurrThumbColor = this.mThumbColor.getDefaultColor();
        }

        this.mThumbSizeF.set(thumbWidth, thumbHeight);
        this.mBackDrawable = backDrawable;
        this.mBackColor = backColor;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        if (!this.mIsBackUseDrawable && this.mBackColor == null) {
            this.mBackColor = ResourceUtils.generateBackColorWithTintColor(this.mTintColor);
            this.mCurrBackColor = this.mBackColor.getDefaultColor();
            this.mNextBackColor = this.mBackColor.getColorForState(CHECKED_PRESSED_STATE, this.mCurrBackColor);
        }

        this.mThumbMargin.set(marginLeft, marginTop, marginRight, marginBottom);
        this.mBackMeasureRatio = this.mThumbMargin.width() >= 0.0F ? Math.max(backMeasureRatio, 1.0F) : backMeasureRatio;
        this.mThumbRadius = thumbRadius;
        this.mBackRadius = backRadius;
        this.mAnimationDuration = (long)animationDuration;
        this.mFadeBack = fadeBack;
        this.mProcessAnimator.setDuration(this.mAnimationDuration);
        this.setFocusable(true);
        this.setClickable(true);
        if (this.isChecked()) {
            this.setProcess(1.0F);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension(this.measureWidth(widthMeasureSpec), this.measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int minWidth = (int)(this.mThumbSizeF.x * this.mBackMeasureRatio);
        if (this.mIsBackUseDrawable) {
            minWidth = Math.max(minWidth, this.mBackDrawable.getMinimumWidth());
        }

        minWidth = Math.max(minWidth, (int)((float)minWidth + this.mThumbMargin.left + this.mThumbMargin.right));
        minWidth = Math.max(minWidth, minWidth + this.getPaddingLeft() + this.getPaddingRight());
        minWidth = Math.max(minWidth, this.getSuggestedMinimumWidth());
        int measuredWidth;
        if (widthMode == 1073741824) {
            measuredWidth = Math.max(minWidth, widthSize);
        } else {
            measuredWidth = minWidth;
            if (widthMode == -2147483648) {
                measuredWidth = Math.min(minWidth, widthSize);
            }
        }

        return measuredWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int minHeight = (int)Math.max(this.mThumbSizeF.y, this.mThumbSizeF.y + this.mThumbMargin.top + this.mThumbMargin.right);
        minHeight = Math.max(minHeight, this.getSuggestedMinimumHeight());
        minHeight = Math.max(minHeight, minHeight + this.getPaddingTop() + this.getPaddingBottom());
        int measuredHeight;
        if (heightMode == 1073741824) {
            measuredHeight = Math.max(minHeight, heightSize);
        } else {
            measuredHeight = minHeight;
            if (heightMode == -2147483648) {
                measuredHeight = Math.min(minHeight, heightSize);
            }
        }

        return measuredHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            this.setup();
        }

    }

    private void setup() {
        float thumbTop = (float)this.getPaddingTop() + Math.max(0.0F, this.mThumbMargin.top);
        float thumbLeft = (float)this.getPaddingLeft() + Math.max(0.0F, this.mThumbMargin.left);
        if (this.mIsThumbUseDrawable) {
            this.mThumbSizeF.x = Math.max(this.mThumbSizeF.x, (float)this.mThumbDrawable.getMinimumWidth());
            this.mThumbSizeF.y = Math.max(this.mThumbSizeF.y, (float)this.mThumbDrawable.getMinimumHeight());
        }

        this.mThumbRectF.set(thumbLeft, thumbTop, thumbLeft + this.mThumbSizeF.x, thumbTop + this.mThumbSizeF.y);
        float backLeft = this.mThumbRectF.left - this.mThumbMargin.left;
        this.mBackRectF.set(backLeft, this.mThumbRectF.top - this.mThumbMargin.top, backLeft + this.mThumbMargin.left + this.mThumbSizeF.x * this.mBackMeasureRatio + this.mThumbMargin.right, this.mThumbRectF.bottom + this.mThumbMargin.bottom);
        this.mSafeRectF.set(this.mThumbRectF.left, 0.0F, this.mBackRectF.right - this.mThumbMargin.right - this.mThumbRectF.width(), 0.0F);
        float minBackRadius = Math.min(this.mBackRectF.width(), this.mBackRectF.height()) / 2.0F;
        this.mBackRadius = Math.min(minBackRadius, this.mBackRadius);
        if (this.mBackDrawable != null) {
            this.mBackDrawable.setBounds((int)this.mBackRectF.left, (int)this.mBackRectF.top, (int)this.mBackRectF.right, (int)this.mBackRectF.bottom);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int alpha;
        if (this.mIsBackUseDrawable) {
            if (this.mFadeBack && this.mCurrentBackDrawable != null && this.mNextBackDrawable != null) {
                alpha = (int)(255.0F * (this.isChecked() ? this.getProcess() : 1.0F - this.getProcess()));
                this.mCurrentBackDrawable.setAlpha(alpha);
                this.mCurrentBackDrawable.draw(canvas);
                alpha = 255 - alpha;
                this.mNextBackDrawable.setAlpha(alpha);
                this.mNextBackDrawable.draw(canvas);
            } else {
                this.mBackDrawable.setAlpha(255);
                this.mBackDrawable.draw(canvas);
            }
        } else if (this.mFadeBack) {
            alpha = (int)(255.0F * (this.isChecked() ? this.getProcess() : 1.0F - this.getProcess()));
            int colorAlpha = Color.alpha(this.mCurrBackColor);
            colorAlpha = colorAlpha * alpha / 255;
            this.mPaint.setARGB(colorAlpha, Color.red(this.mCurrBackColor), Color.green(this.mCurrBackColor), Color.blue(this.mCurrBackColor));
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
            alpha = 255 - alpha;
            colorAlpha = Color.alpha(this.mNextBackColor);
            colorAlpha = colorAlpha * alpha / 255;
            this.mPaint.setARGB(colorAlpha, Color.red(this.mNextBackColor), Color.green(this.mNextBackColor), Color.blue(this.mNextBackColor));
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
            this.mPaint.setAlpha(255);
        } else {
            this.mPaint.setColor(this.mCurrBackColor);
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
        }

        this.mPresentThumbRectF.set(this.mThumbRectF);
        this.mPresentThumbRectF.offset(this.mProcess * this.mSafeRectF.width(), 0.0F);
        if (this.mIsThumbUseDrawable) {
            this.mThumbDrawable.setBounds((int)this.mPresentThumbRectF.left, (int)this.mPresentThumbRectF.top, (int)this.mPresentThumbRectF.right, (int)this.mPresentThumbRectF.bottom);
            this.mThumbDrawable.draw(canvas);
        } else {
            this.mPaint.setColor(this.mCurrThumbColor);
            canvas.drawRoundRect(this.mPresentThumbRectF, this.mThumbRadius, this.mThumbRadius, this.mPaint);
        }

        if (this.mDrawDebugRect) {
            this.mRectPaint.setColor(Color.parseColor("#AA0000"));
            canvas.drawRect(this.mBackRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawRect(this.mPresentThumbRectF, this.mRectPaint);
        }

    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (!this.mIsThumbUseDrawable && this.mThumbColor != null) {
            this.mCurrThumbColor = this.mThumbColor.getColorForState(this.getDrawableState(), this.mCurrThumbColor);
        } else {
            this.setDrawableState(this.mThumbDrawable);
        }

        int[] nextState = this.isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        if (!this.mIsBackUseDrawable && this.mBackColor != null) {
            this.mCurrBackColor = this.mBackColor.getColorForState(this.getDrawableState(), this.mCurrBackColor);
            this.mNextBackColor = this.mBackColor.getColorForState(nextState, this.mCurrBackColor);
        } else {
            if (this.mBackDrawable instanceof StateListDrawable && this.mFadeBack) {
                this.mBackDrawable.setState(nextState);
                this.mNextBackDrawable = this.mBackDrawable.getCurrent().mutate();
            } else {
                this.mNextBackDrawable = null;
            }

            this.setDrawableState(this.mBackDrawable);
            if (this.mBackDrawable != null) {
                this.mCurrentBackDrawable = this.mBackDrawable.getCurrent().mutate();
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isEnabled()) {
            return false;
        } else {
            int action = event.getAction();
            float deltaX = event.getX() - this.mStartX;
            float deltaY = event.getY() - this.mStartY;
            switch(action) {
            case 0:
                this.catchView();
                this.mStartX = event.getX();
                this.mStartY = event.getY();
                this.mLastX = this.mStartX;
                this.setPressed(true);
                break;
            case 1:
            case 3:
                this.setPressed(false);
                boolean nextStatus = this.getStatusBasedOnPos();
                float time = (float)(event.getEventTime() - event.getDownTime());
                if (deltaX < (float)this.mTouchSlop && deltaY < (float)this.mTouchSlop && time < (float)this.mClickTimeout) {
                    this.performClick();
                } else if (nextStatus != this.isChecked()) {
                    this.playSoundEffect(0);
                    this.setChecked(nextStatus);
                } else {
                    this.animateToState(nextStatus);
                }
                break;
            case 2:
                float x = event.getX();
                this.setProcess(this.getProcess() + (x - this.mLastX) / this.mSafeRectF.width());
                this.mLastX = x;
            }

            return true;
        }
    }

    private boolean getStatusBasedOnPos() {
        return this.getProcess() > 0.5F;
    }

    public final float getProcess() {
        return this.mProcess;
    }

    public final void setProcess(float process) {
        float tp = process;
        if (process > 1.0F) {
            tp = 1.0F;
        } else if (process < 0.0F) {
            tp = 0.0F;
        }

        this.mProcess = tp;
        this.invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    protected void animateToState(boolean checked) {
        if (this.mProcessAnimator != null) {
            if (this.mProcessAnimator.isRunning()) {
                this.mProcessAnimator.cancel();
            }

            this.mProcessAnimator.setDuration(this.mAnimationDuration);
            if (checked) {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 1.0F});
            } else {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 0.0F});
            }

            this.mProcessAnimator.start();
        }
    }

    private void catchView() {
        ViewParent parent = this.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }

    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        this.animateToState(checked);
    }

    public void setCheckedImmediately(boolean checked) {
        super.setChecked(checked);
        if (this.mProcessAnimator != null && this.mProcessAnimator.isRunning()) {
            this.mProcessAnimator.cancel();
        }

        this.setProcess(checked ? 1.0F : 0.0F);
        this.invalidate();
    }

    public void toggleImmediately() {
        this.setCheckedImmediately(!this.isChecked());
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            int[] myDrawableState = this.getDrawableState();
            drawable.setState(myDrawableState);
            this.invalidate();
        }

    }

    public boolean isDrawDebugRect() {
        return this.mDrawDebugRect;
    }

    public void setDrawDebugRect(boolean drawDebugRect) {
        this.mDrawDebugRect = drawDebugRect;
        this.invalidate();
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        this.mAnimationDuration = animationDuration;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public void setThumbDrawable(Drawable thumbDrawable) {
        this.mThumbDrawable = thumbDrawable;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        this.setup();
        this.refreshDrawableState();
        this.requestLayout();
        this.invalidate();
    }

    public void setThumbDrawableRes(int thumbDrawableRes) {
        this.setThumbDrawable(ContextCompat.getDrawable(this.getContext(), thumbDrawableRes));
    }

    public Drawable getBackDrawable() {
        return this.mBackDrawable;
    }

    public void setBackDrawable(Drawable backDrawable) {
        this.mBackDrawable = backDrawable;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        this.setup();
        this.refreshDrawableState();
        this.requestLayout();
        this.invalidate();
    }

    public void setBackDrawableRes(int backDrawableRes) {
        this.setBackDrawable(ContextCompat.getDrawable(this.getContext(), backDrawableRes));
    }

    public ColorStateList getBackColor() {
        return this.mBackColor;
    }

    public void setBackColor(ColorStateList backColor) {
        this.mBackColor = backColor;
        if (this.mBackColor != null) {
            this.setBackDrawable((Drawable)null);
        }

        this.invalidate();
    }

    public void setBackColorRes(int backColorRes) {
        this.setBackColor(this.getContext().getResources().getColorStateList(backColorRes));
    }

    public ColorStateList getThumbColor() {
        return this.mThumbColor;
    }

    public void setThumbColor(ColorStateList thumbColor) {
        this.mThumbColor = thumbColor;
        if (this.mThumbColor != null) {
            this.setThumbDrawable((Drawable)null);
        }

    }

    public void setThumbColorRes(int thumbColorRes) {
        this.setThumbColor(this.getContext().getResources().getColorStateList(thumbColorRes));
    }

    public float getBackMeasureRatio() {
        return this.mBackMeasureRatio;
    }

    public void setBackMeasureRatio(float backMeasureRatio) {
        this.mBackMeasureRatio = backMeasureRatio;
        this.requestLayout();
    }

    public RectF getThumbMargin() {
        return this.mThumbMargin;
    }

    public void setThumbMargin(RectF thumbMargin) {
        if (thumbMargin == null) {
            this.setThumbMargin(0.0F, 0.0F, 0.0F, 0.0F);
        } else {
            this.setThumbMargin(thumbMargin.left, thumbMargin.top, thumbMargin.right, thumbMargin.bottom);
        }

    }

    public void setThumbMargin(float left, float top, float right, float bottom) {
        this.mThumbMargin.set(left, top, right, bottom);
        this.requestLayout();
    }

    public void setThumbSize(float width, float height) {
        this.mThumbSizeF.set(width, height);
        this.setup();
        this.requestLayout();
    }

    public float getThumbWidth() {
        return this.mThumbSizeF.x;
    }

    public float getThumbHeight() {
        return this.mThumbSizeF.y;
    }

    public void setThumbSize(PointF size) {
        if (size == null) {
            float defaultSize = this.getResources().getDisplayMetrics().density * 20.0F;
            this.setThumbSize(defaultSize, defaultSize);
        } else {
            this.setThumbSize(size.x, size.y);
        }

    }

    public PointF getThumbSizeF() {
        return this.mThumbSizeF;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public void setThumbRadius(float thumbRadius) {
        this.mThumbRadius = thumbRadius;
        if (!this.mIsThumbUseDrawable) {
            this.invalidate();
        }

    }

    public PointF getBackSizeF() {
        return new PointF(this.mBackRectF.width(), this.mBackRectF.height());
    }

    public float getBackRadius() {
        return this.mBackRadius;
    }

    public void setBackRadius(float backRadius) {
        this.mBackRadius = backRadius;
        if (!this.mIsBackUseDrawable) {
            this.invalidate();
        }

    }

    public boolean isFadeBack() {
        return this.mFadeBack;
    }

    public void setFadeBack(boolean fadeBack) {
        this.mFadeBack = fadeBack;
    }

    public int getTintColor() {
        return this.mTintColor;
    }

    public void setTintColor(int tintColor) {
        this.mTintColor = tintColor;
        this.mThumbColor = ResourceUtils.generateThumbColorWithTintColor(this.mTintColor);
        this.mBackColor = ResourceUtils.generateBackColorWithTintColor(this.mTintColor);
        this.mIsBackUseDrawable = false;
        this.mIsThumbUseDrawable = false;
        this.refreshDrawableState();
        this.invalidate();
    }
}
