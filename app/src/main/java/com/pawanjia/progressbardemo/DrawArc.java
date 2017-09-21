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

public class DrawArc extends View {

    private Paint mPaint;
    @BindColor(R.color.color_da5f3c)
    int color_da5f3c;
    private int startX = 200;
    private int startY = 400;
    private int radius = 80;
    private int section = 300;
    private int currentX;
    private boolean isStart = true;
    private Path mPath;
    private boolean isDrawLine = true;
    private int mWave = 1;
    private int mOldWave = 1;

    public DrawArc(Context context) {
        this(context, null);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);  //设置画笔光滑
        mPaint.setColor(getResources().getColor(R.color.color_da5f3c));//设置画笔颜色
        mPaint.setStrokeWidth(5);//设置画笔宽度
        //mPaint.setStyle(Paint.Style.FILL);//设置为描边模式
        mPaint.setStyle(Paint.Style.FILL);//设置为填充模式
        mPath = new Path();//创建路径
        mPath.moveTo(startX, startY);//设置起点
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
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            // setMeasuredDimension(widthSize, heigthSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
           // setMeasuredDimension(widthSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
           // setMeasuredDimension(widthSpecSize, heigthSize);
        }

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






    private void startAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startX, startX + section + radius);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }

    private void startAnimation(final int number) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startX, startX + number * section + radius);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                int moveX = currentX - startX;
                //右边半径规律                          //左边半径规律
                if (radius < moveX % section + 1 && moveX % section <= (section - radius)) {
                    isDrawLine = true;
                } else if (radius < moveX) {
                    //半径范围内
                    isDrawLine = false;
                }
                //计算当前为第几个圆
                for (int n = 0; n < number; n++) {
                    if (section * n + radius < moveX && moveX < (n + 1) * section + radius) {
                        mWave = n + 1;
                        if (mOldWave != mWave) {
                            //mPath.lineTo(currentX,startY);
                            mPath.moveTo(currentX, startY);
                        }
                        mOldWave = mWave;
                    }
                }
                Log.d("tag", "currentX=" + currentX);
                invalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.start();
    }

}

