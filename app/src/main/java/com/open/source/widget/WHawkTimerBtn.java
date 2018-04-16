package com.open.source.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;


@SuppressLint("AppCompatCustomView")
public class WHawkTimerBtn extends TextView {
    private static final int time = 60;

    public WHawkTimerBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void startDown() {
        this.setClickable(false);
        TimeCount timer = new TimeCount(time * 1000, 1000);
        timer.start();
    }

    class TimeCount extends CountDownTimer {
        private TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            WHawkTimerBtn.this.setText("重新发送");
            WHawkTimerBtn.this.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            WHawkTimerBtn.this.setText("　" + millisUntilFinished / 1000 + "s　");
        }
    }
}
