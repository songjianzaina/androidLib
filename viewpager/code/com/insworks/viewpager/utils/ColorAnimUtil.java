package com.insworks.viewpager.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.TextView;

import com.zhengsr.viewpagerlib.view.ColorTextView;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.viewpager.utils
 * @ClassName: ColorAnimUtil
 * @Author: Song Jian
 * @CreateDate: 2019/6/14 16:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/14 16:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: textView颜色逐渐改变 工具类  布局文件中使用ColorTextView空间
 */
public class ColorAnimUtil {
    public void leftchange(final ColorTextView view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "progress", 0, 1);
        animator.setDuration(2000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setprogress(value, ColorTextView.DEC_LEFT);
            }
        });


    }

    public void rightchange(final ColorTextView view) {

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "progress", 0, 1);
        animator.setDuration(2000).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setprogress(value, ColorTextView.DEC_RIGHT);
            }
        });
    }
}
