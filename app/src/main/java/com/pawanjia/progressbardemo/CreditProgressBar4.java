package com.pawanjia.progressbardemo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.R.attr.width;

/**
 * Description des
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class CreditProgressBar4 extends View {

    private Paint mPaintBg;
    private int radius = ToolUtils.dip2px(getContext(), 4);
    private int startX = radius * 2;
    private int startY = radius * 2;
    private int section = ToolUtils.dip2px(getContext(), 60);
    private int maxLength = ToolUtils.dip2px(getContext(), 240);
    private int heigthSize = ToolUtils.dip2px(getContext(), 30);
    private int widthSize = maxLength + radius * 4;
    private int currentX = startX;
    private Path mPath;
    private int paintWidth = ToolUtils.sp2px(getContext(), 2);
    private Paint mPaintProgress;
    private String[] levelText = {"V1", "V2", "V3", "V4", "V5"};
    private Paint paintText;
    private Path progressPath;
    private int maxValue;
    private int number = 4;
    private int moveLength;
    private int currentNumber;
    private boolean enableMaxValue;
    private boolean enableMove;
    private AnimatorSet mSet;
    private int mWidth;
    private int mHeight;

    public CreditProgressBar4(Context context) {
        this(context, null);
    }

    public CreditProgressBar4(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditProgressBar4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setTextAlign(Paint.Align.CENTER);
        mPaintBg.setStyle(Paint.Style.FILL_AND_STROKE);//
        mPaintBg.setColor(getResources().getColor(R.color.color_da5f3c));
        mPaintBg.setStrokeWidth(paintWidth);//设置画笔宽度


        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(ToolUtils.sp2px(getContext(), 12));
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setColor(getResources().getColor(R.color.color_da5f3c));

        mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setColor(getResources().getColor(R.color.color_fff2c1));
        mPaintProgress.setStrokeWidth(paintWidth);//设置画笔宽度
        mPaintProgress.setStyle(Paint.Style.FILL_AND_STROKE);


        mPath = new Path();
        mPath.moveTo(startX, startY);
        progressPath = new Path();
        progressPath.moveTo(startX, startY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        Log.d("tag",  "mWidth=" + mWidth + "mHeight=" + mHeight);
        drawProgressBar(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, heigthSize);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, heigthSize);
        }
        //scrollview中遇到
        if (heightSpecMode == MeasureSpec.UNSPECIFIED) {
            setMeasuredDimension(widthSize, heigthSize);
        }
        Log.d("tag", "widthSpecMode=" + widthSpecMode + "widthSpecSize=" + widthSpecSize + "widthMeasureSpec=" + widthMeasureSpec+"heightSpecSize="+heightSpecSize);
        boolean a = heightSpecMode == MeasureSpec.UNSPECIFIED;
        boolean b = heightSpecMode == MeasureSpec.EXACTLY;
        boolean c = heightSpecMode == MeasureSpec.AT_MOST;
        Log.d("tag", a+ "..." + b+ "..." + c);
        int measuredWidth2 = getMeasuredWidth();
        int width2 = getWidth();
        Log.d("tag", "measuredWidth=" + "measuredWidth2=" + measuredWidth2 + "width=" + width + "width2=" + width2);
    }

    private void drawProgressBar(Canvas canvas) {
        Paint.FontMetricsInt fm = paintText.getFontMetricsInt();
        int ascent = fm.ascent;
        int textY = startY - ascent + ToolUtils.dip2px(getContext(), 10);
        for (int i = 0; i <= maxLength / section; i++) {
            mPath.addCircle(startX + i * section, startY, radius, Path.Direction.CCW);
            paintText.setColor(getResources().getColor(R.color.color_da5f3c));
            canvas.drawText(levelText[i], startX + i * section, textY, paintText);
        }
        mPath.moveTo(startX, startY);
        mPath.lineTo(startX + maxLength, startY);
        canvas.drawPath(mPath, mPaintBg);

        progressPath.moveTo(startX, startY);
        progressPath.lineTo(currentX, startY);

        for (int i = 0; i <= currentNumber; i++) {
            if (i == 0 && !enableMove) {
                break;
            }
            paintText.setColor(getResources().getColor(R.color.color_fff2c1));
            if (levelText.length <= i) {
                break;
            }
            canvas.drawText(levelText[i], startX + i * section, textY, paintText);
            progressPath.addCircle(i * section + startX, startY, radius, Path.Direction.CCW);
        }
        Log.d("tag", "currentX=" + currentX + "moveLength=" + moveLength+"draw");
        canvas.drawPath(progressPath, mPaintProgress);
    }


    /**
     * 最大长度
     *
     * @param maxLength px
     * @return
     */
    public CreditProgressBar4 setMaxLength(int maxLength) {
        if (0 < maxLength)
            this.maxLength = maxLength;
        return this;
    }

    /**
     * 最大分数
     *
     * @param maxValue
     * @return
     */
    public CreditProgressBar4 setMaxValue(int maxValue) {
        if (0 < maxValue) {
            this.maxValue = maxValue;
            enableMaxValue = true;
        } else {
            enableMaxValue = false;
        }
        return this;
    }

    /**
     * 实际分数
     *
     * @param currentValue
     * @return
     */
    public CreditProgressBar4 setProgressBar(int currentValue) {
        if (0 < currentValue && enableMaxValue) {
            moveLength = (int) (maxLength * (currentValue * 1.f / maxValue));
            number = moveLength / section;
            enableMove = true;
            startAnimation();
        } else {
            enableMove = false;
            currentNumber = 0;
            currentX = startX;
            invalidate();
        }
        return this;
    }

    private void startAnimation() {
        ValueAnimator valueAnimatorLine = ValueAnimator.ofInt(startX, startX + moveLength);
        valueAnimatorLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                Log.d("tag", "currentX=" + currentX + "moveLength=" + moveLength);
                invalidate();
            }
        });
        valueAnimatorLine.setDuration(500);
        ValueAnimator valueAnimatorCircle = ValueAnimator.ofInt(0, number);
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentNumber = (int) animation.getAnimatedValue();
                Log.d("tag", "currentNumber=" + currentNumber + "number=" + number);
                invalidate();
            }
        });
        valueAnimatorCircle.setDuration(500);

        mSet = new AnimatorSet();
        mSet.playTogether(valueAnimatorLine, valueAnimatorCircle);
        mSet.start();
    }

    /**
     * @return 当前等级
     */
    public int getCurrentLevel() {
        return number;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSet != null) {
            mSet.end();
            mSet = null;
        }
    }
}
