package com.pawanjia.progressbardemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import butterknife.BindColor;

/**
 * Description des
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class DrawProgress extends View {

    private Paint mPaint;
    @BindColor(R.color.color_da5f3c)
    int color_da5f3c;
    private int startX = 200;
    private int startY = 400;
    private int radius = 80;
    private int section = 300;
    private int currentX;
    private Path mPath;
    private boolean isDrawLine = true;
    private int mWave = 1;

    public DrawProgress(Context context) {
        this(context, null);
    }

    public DrawProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.color_da5f3c));
        mPaint.setStrokeWidth(1);//设置画笔宽度
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
        mPath.moveTo(startX, startY);
        currentX = startX;
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSlicedCircle(canvas);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
    /**
     * @param canvas 画平切圆
     */
    private void drawSlicedCircle(Canvas canvas) {
        if (startX + section - radius >= currentX) {
            mPath.lineTo(currentX, startY);
        }
        if (startX + section - radius <= currentX) {//这里是范围
            RectF rectF = new RectF(startX + section - radius, startY - radius, startX + section + radius, startY + radius);
            int dia = radius * 2;
            int defAngle = (currentX - (startX + section - radius)) * 180 / dia;
            Log.d("tag", "currentX=" + currentX + "defAngle=" + defAngle);
            mPath.addArc(rectF, 180 - defAngle, defAngle * 2);
        }
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * @param canvas 画平切圆
     */
    private void drawSlicedCircle2(Canvas canvas) {
        if (isDrawLine) {
            mPath.lineTo(currentX, startY);
        } else {
            RectF rectF = new RectF(startX + mWave * section - radius, startY - radius, startX + mWave * section + radius, startY + radius);
            int dia = radius * 2;
            int defAngle = (currentX - (startX + mWave * section - radius)) * 180 / dia;
            Log.d("tag", "currentX=" + currentX + "defAngle=" + defAngle + "mWave=" + mWave);
            mPath.addArc(rectF, 180 - defAngle, defAngle * 2);
        }
        canvas.drawPath(mPath, mPaint);
    }


    /**
     * @param canvas 画扇形
     */
    private void drawSector(Canvas canvas) {
        if (startX + section - radius >= currentX) {
            mPath.lineTo(currentX, startY);
        }
        if (startX + section - radius <= currentX) {//这里是范围
            RectF rectF = new RectF(startX + section - radius, startY - radius, startX + section + radius, startY + radius);
            int dia = radius * 2;
            int defAngle = (currentX - (startX + section - radius)) * 180 / dia;
            Log.d("tag", "currentX=" + currentX + "defAngle=" + defAngle);
            mPath.addArc(rectF, 180 - defAngle, defAngle * 2);
            mPath.lineTo(startX + section, startY);//加这个画动态扇形效果
        }
        canvas.drawPath(mPath, mPaint);
    }


    /**
     * @param canvas 画扇形
     */
    private void drawSector2(Canvas canvas) {
        if (isDrawLine) {
            mPath.lineTo(currentX, startY);
        } else {
            RectF rectF = new RectF(startX + mWave * section - radius, startY - radius, startX + mWave * section + radius, startY + radius);
            int dia = radius * 2;
            int defAngle = (currentX - (startX + mWave * section - radius)) * 180 / dia;
            Log.d("tag", "currentX=" + currentX + "defAngle=" + defAngle + "mWave=" + mWave);
            mPath.addArc(rectF, 180 - defAngle, defAngle * 2);
            mPath.lineTo(startX + mWave * section, startY);//加这个画动态扇形效果
        }
        canvas.drawPath(mPath, mPaint);
    }




    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startX, startX + section + radius);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(10000);
        valueAnimator.start();
    }



}

