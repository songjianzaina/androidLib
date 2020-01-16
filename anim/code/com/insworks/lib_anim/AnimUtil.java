package com.insworks.lib_anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.widget.TextView;

import java.text.DecimalFormat;

public class AnimUtil {

    //金额滚动显示
    public static void decimalAnim(final TextView textView, Float money) {
        final DecimalFormat decimalFormat = new DecimalFormat(".00");
        ValueAnimator animator = ValueAnimator.ofFloat(0, money);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(decimalFormat.format(animation.getAnimatedValue()));
                SpannableStringUtil.setRelativeSizeText(textView, 0, textView.getText().length() - 3, 1.3f, textView.getText().toString());
            }
        });

    }

    /**
     * 开启缩放渐变呼吸动画
     */
    public static void startScaleBreathAnimation(Object target) {
        //缩放动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 0.8f, 1f);
        //渐变动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f);
        //无限循环
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alphaAnimator);
        animatorSet.setDuration(4000);
        animatorSet.setInterpolator(new BraetheInterpolator());
        animatorSet.start();


    }

    private static class BraetheInterpolator implements TimeInterpolator {
        @Override
        public float getInterpolation(float input) {

            float x = 6 * input;
            float k = 1.0f / 3;
            int t = 6;
            int n = 1;//控制函数周期，这里取此函数的第一个周期
            float PI = 3.1416f;
            float output = 0;

            if (x >= ((n - 1) * t) && x < ((n - (1 - k)) * t)) {
                output = (float) (0.5 * Math.sin((PI / (k * t)) * ((x - k * t / 2) - (n - 1) * t)) + 0.5);

            } else if (x >= (n - (1 - k)) * t && x < n * t) {
                output = (float) Math.pow((0.5 * Math.sin((PI / ((1 - k) * t)) * ((x - (3 - k) * t / 2) - (n - 1) * t)) + 0.5), 2);
            }
            return output;
        }
    }

    /**
     * 上下 左右浮动动画
     *
     * @param view
     * @param delay
     */
    private static void floatAnim(View view, int delay) {
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f, 6.0f, -6.0f);
        translationXAnim.setDuration(1500);
        translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//
        translationXAnim.start();
        animators.add(translationXAnim);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -3.0f, 3.0f, -3.0f);
        translationYAnim.setDuration(1000);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.setRepeatMode(ValueAnimator.INFINITE);
        translationYAnim.start();
        animators.add(translationYAnim);

        AnimatorSet btnSexAnimatorSet = new AnimatorSet();
        btnSexAnimatorSet.playTogether(animators);
        btnSexAnimatorSet.setStartDelay(delay);
        btnSexAnimatorSet.start();
    }
}
