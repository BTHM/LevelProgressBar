package com.pawanjia.progressbardemo;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayProgressActivity extends AppCompatActivity {

    @Bind(R.id.tv_current_value)
    TextView tvCurrentValue;
    @Bind(R.id.view_progress)
    View viewProgress;
    @Bind(R.id.frame_container)
    FrameLayout frameContainer;
    private int maxValue = 60000;
    private float currentX;
    private ViewGroup.LayoutParams params;
    private LinearLayout.LayoutParams tvParams;
    private ValueAnimator valueAnimatorAlpha;
    private int measuredWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pay);
        ButterKnife.bind(this);
        frameContainer.post(new Runnable() {
            @Override
            public void run() {
                int width = frameContainer.getWidth();
                int moveWidth = (int) ((60000 * 1.f / maxValue) * width);
                startAnimation(moveWidth);
                viewProgress.setVisibility(View.VISIBLE);
                measuredWidth = tvCurrentValue.getMeasuredWidth();
            }
        });
        params = viewProgress.getLayoutParams();
        tvParams = (LinearLayout.LayoutParams) tvCurrentValue.getLayoutParams();

    }

    private void startAnimation(int moveLength) {
        ValueAnimator valueAnimatorLine = ValueAnimator.ofInt(0, moveLength);
        valueAnimatorLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (float) animation.getAnimatedValue();
                params.width = (int) currentX;
                if (animation == null) {

                }
                tvParams.leftMargin = (int) currentX-measuredWidth/2;
                tvCurrentValue.setLayoutParams(tvParams);
                viewProgress.setLayoutParams(params);
            }
        });
        valueAnimatorLine.setDuration(2000);
        valueAnimatorAlpha = ValueAnimator.ofFloat(0, 1);
        valueAnimatorAlpha.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                tvCurrentValue.setAlpha(alpha);
            }
        });
        valueAnimatorAlpha.setDuration(500);
        valueAnimatorLine.start();
        valueAnimatorLine.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimatorAlpha.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        /*mSet = new AnimatorSet();
        mSet.playTogether(valueAnimatorLine, valueAnimatorCircle);
        mSet.start();*/
    }

}
