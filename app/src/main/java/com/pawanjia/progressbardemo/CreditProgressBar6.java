package com.pawanjia.progressbardemo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Description 诊所积分等级进度
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class CreditProgressBar6 extends View {

    private Paint mPaintBg;
    private int radius = ToolUtils.dip2px(getContext(), 4);
    private int startX = radius * 2;
    private int startY = radius * 2;
    private int section = ToolUtils.dip2px(getContext(), 80);
    private int maxLength = ToolUtils.dip2px(getContext(), 240);
    private int heigthSize = ToolUtils.dip2px(getContext(), 30);
    private int widthSize = maxLength + radius * 4;
    private int currentX = startX;
    private Path mPath;
    private int paintWidth = ToolUtils.sp2px(getContext(), 2);
    private Paint mPaintProgress;
    private String[] levelText = {"V1", "V2", "V3", "V4"};
    private Paint paintText;
    private Path progressPath;
    private int maxValue;
    private int moveLength;
    private int currentNumber;
    private boolean enableMaxValue;
    private boolean enableMove;
    private AnimatorSet mSet;
    private int LEVEL_GOLD = 2000;
    private int LEVEL_PLATINUM = 6000;
    private int LEVEL_DIAMOND = 18000;


    public CreditProgressBar6(Context context) {
        this(context, null);
    }

    public CreditProgressBar6(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditProgressBar6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        for (int i = 0; i < currentNumber; i++) {
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
        canvas.drawPath(progressPath, mPaintProgress);
    }



    /**
     * 最大分数
     *
     * @param maxValue
     * @return
     */
    public CreditProgressBar6 setMaxValue(int maxValue) {
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
     * @param currentProgress
     * @return
     */
    public CreditProgressBar6 setProgress(int currentProgress) {
        if (0 < currentProgress && enableMaxValue) {
            if (currentProgress < LEVEL_GOLD) {//黄金,及以下等级
                currentNumber=1;
                moveLength = (int) (section * (currentProgress * 1.f / LEVEL_GOLD));
            } else if (LEVEL_GOLD <= currentProgress && currentProgress < LEVEL_PLATINUM) {//铂金，及以下等级
                currentNumber=2;
                int section2 = currentProgress - LEVEL_GOLD;
                int platinum_gold = LEVEL_PLATINUM - LEVEL_GOLD;
                moveLength = (int) (section * (section2 * 1.f / platinum_gold)+section);
            } else if (LEVEL_PLATINUM <= currentProgress && currentProgress < LEVEL_DIAMOND) {//钻石，及以下等级
                currentNumber=3;
                int section3 = currentProgress - LEVEL_PLATINUM;
                int diamond_platinum = LEVEL_DIAMOND - LEVEL_PLATINUM;
                moveLength = (int) (section * (section3 * 1.f / diamond_platinum)+section*2);
            } else if (LEVEL_DIAMOND <= currentProgress) {//钻石，及以上等级
                currentNumber=4;
                moveLength = maxLength;
            }
            enableMove = true;
            startAnimation();
        } else {
            enableMove = false;
            currentNumber = 0;
            currentX = startX;
        }
        invalidate();
        return this;
    }

    private void startAnimation() {
        ValueAnimator valueAnimatorLine = ValueAnimator.ofInt(startX, startX + moveLength);
        valueAnimatorLine.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimatorLine.setDuration(500);
        ValueAnimator valueAnimatorCircle = ValueAnimator.ofInt(0, currentNumber);
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentNumber = (int) animation.getAnimatedValue();
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
        return currentNumber;
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
