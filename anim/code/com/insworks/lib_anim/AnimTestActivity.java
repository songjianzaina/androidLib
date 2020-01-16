package com.insworks.lib_anim;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;


public class AnimTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_anim_activity_anim_test);

        final TextView tvMoney = (TextView) findViewById(R.id.tv_money);
        TextView tvBreath = (TextView) findViewById(R.id.tv_breath);
        TickerView tickerView = findViewById(R.id.tickerView);




        final float[] startMoney = {999.88f};
        //金额滚动动画
        AnimUtil.decimalAnim(tvMoney, startMoney[0]);
        new CountDownTimer(10000, 1000) {
            @Override
            public void onFinish() {// 计时完毕时触发

            }

            @Override
            public void onTick(long millisUntilFinished) {
                startMoney[0] = startMoney[0] + 0.8f;
                AnimUtil.decimalAnim(tvMoney, startMoney[0]);
            }
        }.start();


        //呼吸动画
        AnimUtil.startScaleBreathAnimation(tvBreath);

        //第三方金额滚动动画
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        tickerView.setAnimationInterpolator(new OvershootInterpolator());
        tickerView.setText("1235.99");
        tickerView.setText("4435.08");
    }


}
