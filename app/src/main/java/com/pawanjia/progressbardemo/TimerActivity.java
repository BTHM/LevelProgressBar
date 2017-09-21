package com.pawanjia.progressbardemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {
    private CountDownTimerButton timeBtn;
    private CountDownTime mTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        timeBtn = (CountDownTimerButton) findViewById(R.id.timeBtn);
        timeBtn.setOnClickListener(this);
        mTime = new CountDownTime(10, 1);//初始化对象
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeBtn:
                mTime.start(); //开始计时
                break;
        }
    }

    /**
     * 第一种方法 使用android封装好的 CountDownTimer
     * 创建一个类继承 CountDownTimer
     */
    class CountDownTime extends CountDownTimer {

        //构造函数  第一个参数代表总的计时时长  第二个参数代表计时间隔  单位都是毫秒
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) { //每计时一次回调一次该方法
            timeBtn.setClickable(false);
            timeBtn.setText(l/10 + "秒后重新开始");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            timeBtn.setClickable(true);
            timeBtn.setText("倒计时开始");
        }
    }
}
