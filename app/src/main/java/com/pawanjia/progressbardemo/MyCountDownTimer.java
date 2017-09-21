package com.pawanjia.progressbardemo;

import android.os.CountDownTimer;
import android.util.Log;

/**
 * Description
 *
 * @author liupeng502
 * @data 2017/8/17
 */

public class MyCountDownTimer extends CountDownTimer {

    private TimerListener mTimerListener;
    interface TimerListener {
        void timerUpdateListener(long currentValue);
        void timerFinishListener();
    }

    public void setTimerListener(TimerListener timerListener){
        mTimerListener = timerListener;
    }



    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        Log.d("tag","millisUntilFinished="+millisUntilFinished);
        int sec = (int) Math.round((float) millisUntilFinished / 1000);
        Log.d("tag","sec="+sec);
    }

    @Override
    public void onFinish() {
        Log.d("tag","onFinish=");
        //Log.d("tag","sec="+0);
    }
}
