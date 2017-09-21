package com.pawanjia.progressbardemo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description 诊所积分等级进度
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class LevelProgressBar extends View {

    /**
     * 画第一层画笔
     */
    private Paint mPaintBack;
    /**
     * 文本的画笔
     */
    private Paint mPaintText;
    /**
     * 进度条的画笔
     */
    private Paint mPaintProgress;
    private int radius = ToolUtils.dip2px(getContext(), 4);
    private int startX = radius * 2;
    private int startY = radius * 2;
    private int section = ToolUtils.dip2px(getContext(), 80);
    private int maxLength = ToolUtils.dip2px(getContext(), 240);
    private int heigthSize = ToolUtils.dip2px(getContext(), 30);
    private int widthSize = maxLength + radius * 4;
    private int currentX = startX;
    private int paintWidth = ToolUtils.sp2px(getContext(), 2);
    private String[] levelText = {"V1", "V2", "V3", "V4"};

    private Path mPath;
    private Path progressPath;

    private int maxValue;
    private int moveLength;
    private int currentNumber;
    private boolean enableMaxValue;
    private boolean enableMove;
    private AnimatorSet mSet;
    private int LEVEL_COMMON = 0;
    private int LEVEL_GOLD = 2000;
    private int LEVEL_PLATINUM = 6000;
    private int LEVEL_DIAMOND = 18000;
    private Context mContext;
    private int backColor;
    private int foreColor;
    private boolean isFirst = true;
    private int mHeight;
    private int mWidth;


    public LevelProgressBar(Context context) {
        this(context, null);
    }

    public LevelProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LevelProgressBar);
        section = attributes.getInteger(R.styleable.LevelProgressBar_section, section);
        backColor = attributes.getColor(R.styleable.LevelProgressBar_backColor, getResources().getColor(R.color.color_da5f3c));
        foreColor = attributes.getColor(R.styleable.LevelProgressBar_foreColor, getResources().getColor(R.color.color_fff2c1));
        radius = attributes.getInteger(R.styleable.LevelProgressBar_radius, radius);
        init();
    }

    private void init() {
        mPaintBack = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBack.setStyle(Paint.Style.FILL_AND_STROKE);//
        mPaintBack.setColor(backColor);
        mPaintBack.setStrokeWidth(paintWidth);//设置画笔宽度


        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(ToolUtils.sp2px(getContext(), 12));
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(backColor);

        mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setColor(foreColor);
        mPaintProgress.setStrokeWidth(paintWidth);
        mPaintProgress.setStyle(Paint.Style.FILL_AND_STROKE);


        mPath = new Path();
        mPath.moveTo(startX, startY);
        progressPath = new Path();
        progressPath.moveTo(startX, startY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            mHeight = getHeight() - paddingTop - paddingBottom;
            mWidth = getWidth() - paddingLeft - paddingRight;
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
        Log.d("LevelProgressBar", "isFirst" + isFirst);
        Paint.FontMetricsInt fm = mPaintText.getFontMetricsInt();
        int ascent = fm.ascent;
        int textY = startY - ascent + ToolUtils.dip2px(getContext(), 10);
        for (int i = 0; i <= maxLength / section; i++) {
            mPath.addCircle(startX + i * section, startY, radius, Path.Direction.CCW);
            mPaintText.setColor(getResources().getColor(R.color.color_da5f3c));
            canvas.drawText(levelText[i], startX + i * section, textY, mPaintText);
        }
        mPath.moveTo(startX, startY);
        mPath.lineTo(startX + maxLength, startY);
        canvas.drawPath(mPath, mPaintBack);
        mPath.reset();







        /*progressPath.addCircle(startX, startY, radius, Path.Direction.CCW);
        canvas.drawPath(progressPath, mPaintProgress);
        mPaintText.setColor(getResources().getColor(R.color.color_fff2c1));
        canvas.drawText(levelText[0], startX, textY, mPaintText);


        progressPath.moveTo(startX, startY);
        progressPath.lineTo(currentX, startY);

        for (int i = 1; i < currentNumber; i++) {
            if (!enableMove) {
                break;
            }
            mPaintText.setColor(getResources().getColor(R.color.color_fff2c1));
            if (levelText.length <= i) {
                break;
            }
            canvas.drawText(levelText[i], startX + i * section, textY, mPaintText);
            progressPath.addCircle(i * section + startX, startY, radius, Path.Direction.CCW);
        }
        canvas.drawPath(progressPath, mPaintProgress);*/
    }


    /**
     * 最大分数
     *
     * @param maxValue
     * @return
     */
    public LevelProgressBar setMaxValue(int maxValue) {
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
    public LevelProgressBar setProgress(int currentProgress) {
        if (0 <= currentProgress && enableMaxValue) {
            if (currentProgress == LEVEL_COMMON) {//大众等级
                currentNumber = 1;
            } else if (0 < currentProgress && currentProgress < LEVEL_GOLD) {//大众等级
                currentNumber = 1;
                int dex = LEVEL_GOLD * radius / section;
                if (LEVEL_GOLD - dex < currentProgress)
                    currentProgress = LEVEL_GOLD - dex;

                moveLength = (int) (section * (currentProgress * 1.f / LEVEL_GOLD));
            } else if (LEVEL_GOLD <= currentProgress && currentProgress < LEVEL_PLATINUM) {//黄金等级
                currentNumber = 2;
                int platinum_gold = LEVEL_PLATINUM - LEVEL_GOLD;
                int dex = platinum_gold * radius / section;
                //保证直线不会进入园内
                int section2 = currentProgress - LEVEL_GOLD;
                if (platinum_gold - dex < currentProgress - LEVEL_GOLD)
                    section2 = platinum_gold - dex;

                moveLength = (int) (section * (section2 * 1.f / platinum_gold) + section);
            } else if (LEVEL_PLATINUM <= currentProgress && currentProgress < LEVEL_DIAMOND) {//铂金等级
                currentNumber = 3;

                int diamond_platinum = LEVEL_DIAMOND - LEVEL_PLATINUM;
                int dex = diamond_platinum * radius / section;
                int section3 = currentProgress - LEVEL_PLATINUM;
                if (diamond_platinum - dex < currentProgress - LEVEL_PLATINUM)
                    section3 = diamond_platinum - dex;

                moveLength = (int) (section * (section3 * 1.f / diamond_platinum) + section * 2);
            } else if (LEVEL_DIAMOND <= currentProgress) {//钻石等级
                currentNumber = 4;
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
