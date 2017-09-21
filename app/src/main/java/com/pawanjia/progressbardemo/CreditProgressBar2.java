package com.pawanjia.progressbardemo;

import android.animation.TypeEvaluator;
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

import static android.R.attr.width;

/**
 * Description des
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class CreditProgressBar2 extends View {

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

    public CreditProgressBar2(Context context) {
        this(context, null);
    }

    public CreditProgressBar2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditProgressBar2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        startAnimation(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawSlicedCircle2(canvas);
        drawSector2(canvas);
        //drawSlicedCircle(canvas);
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
            //setMeasuredDimension(widthSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            //setMeasuredDimension(widthSpecSize, heigthSize);
        }
        Log.d("tag", "widthSpecMode=" + widthSpecMode + "widthSpecSize=" + widthSpecSize + "widthMeasureSpec=" + widthMeasureSpec);
        boolean a = heightSpecMode == MeasureSpec.UNSPECIFIED;
        boolean b = heightSpecMode == MeasureSpec.EXACTLY;
        boolean c = heightSpecMode == MeasureSpec.AT_MOST;
        Log.d("tag", a+ "..." + b+ "..." + c);
        int measuredWidth2 = getMeasuredWidth();
        int width2 = getWidth();
        Log.d("tag", "measuredWidth=" + "measuredWidth2=" + measuredWidth2 + "width=" + width + "width2=" + width2);
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
            // mPath.addCircle(startX + mMoveX, startY, radius, Path.Direction.CCW);
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

    /**
     * @param canvas 画扇形
     */
    private void drawArc(Canvas canvas) {
        RectF rectF = new RectF(startX - radius, startY - radius, startX + radius, startY + radius);
        mPath.addArc(rectF, 30, 300);
        mPath.lineTo(startX, startY);//加上这句是扇形
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
        valueAnimator.setDuration(2000);
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
                            mPath.moveTo(currentX,startY);
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


    public class FloatEvaluator implements TypeEvaluator {
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            float startFloat = ((Number) startValue).floatValue();
            return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
        }
    }
}

