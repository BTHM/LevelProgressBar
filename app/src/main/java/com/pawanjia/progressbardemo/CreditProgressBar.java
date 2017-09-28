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

import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Description 诊所积分等级进度
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class CreditProgressBar extends View {

    private Paint mPaintBg;
    private int radius = ToolUtils.dip2px(getContext(), 4);
    private int startX = radius * 2;
    private int startY = radius * 2;
    private int section = ToolUtils.dip2px(getContext(), 80);
    private int maxLength = ToolUtils.dip2px(getContext(), 240);
    private int heigthSize = ToolUtils.dip2px(getContext(), 40);
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
    private int LEVEL_COMMON = 0;
    private int LEVEL_GOLD = 2000;
    private int LEVEL_PLATINUM = 6000;
    private int LEVEL_DIAMOND = 18000;
    private int mHeight;
    private int mWidth;
    @BindColor(R.color.color_da5f3c)
    int color_da5f3c;
    @BindColor(R.color.color_fff2c1)
    int color_fff2c1;

    public CreditProgressBar(Context context) {
        this(context, null);
    }

    public CreditProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreditProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setTextAlign(Paint.Align.CENTER);
        mPaintBg.setStyle(Paint.Style.FILL_AND_STROKE);//
        mPaintBg.setColor(color_da5f3c);
        mPaintBg.setStrokeWidth(paintWidth);//设置画笔宽度


        paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setTextSize(ToolUtils.sp2px(getContext(), 12));
        paintText.setStyle(Paint.Style.STROKE);
        paintText.setColor(color_da5f3c);

        mPaintProgress = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgress.setColor(color_fff2c1);
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
        int ascent = fm.ascent;//测量字体高度
        int textY = startY - ascent + ToolUtils.dip2px(getContext(), 10);
        for (int i = 1; i <= maxLength / section; i++) {//有几个等级
            //画对对应位置圆
            mPath.addCircle(startX + i * section, startY, radius, Path.Direction.CCW);
            paintText.setColor(color_da5f3c);
            //画对应文字
            canvas.drawText(levelText[i], startX + i * section, textY, paintText);
            canvas.drawPath(mPath, mPaintBg);
        }
        //path回到,出发点 然后画直线
        mPath.reset();
        mPath.moveTo(startX, startY);
        mPath.lineTo(startX + maxLength, startY);

        //默认
        progressPath.addCircle(startX, startY, radius, Path.Direction.CCW);
        canvas.drawPath(progressPath, mPaintProgress);
        paintText.setColor(color_fff2c1);
        canvas.drawText(levelText[0], startX, textY, paintText);


        //进度层path直线部分连接
        progressPath.lineTo(currentX, startY);

        for (int i = 1; i < currentNumber; i++) {
            if (!enableMove) {
                break;
            }
            paintText.setColor(color_fff2c1);
            if (levelText.length <= i) {
                break;//文字获取不要越界
            }
            //绘制进度层文字
            canvas.drawText(levelText[i], startX + i * section, textY, paintText);
            //进度层path圆部分连接
            progressPath.addCircle(i * section + startX, startY, radius, Path.Direction.CCW);
        }
        //进度层绘制
        canvas.drawPath(progressPath, mPaintProgress);
    }


    /**
     * 最大分数
     *
     * @param maxValue
     * @return
     */
    public CreditProgressBar setMaxValue(int maxValue) {
        if (0 < maxValue) {
            this.maxValue = maxValue;
            enableMaxValue = true;
        } else {
            enableMaxValue = false;
        }
        return this;
    }

    /**
     * @param currentProgress 设置实际分数
     * @return  移动距离
     */
    public CreditProgressBar setProgress(int currentProgress) {
        if (0 <= currentProgress && enableMaxValue) {
            if (currentProgress == LEVEL_COMMON) {//大众等级
                //currentNumber = 1;
            } else if (0 < currentProgress && currentProgress < LEVEL_GOLD) {//大众等级
                currentNumber = 1;
                //计算需要移动距离、
                //圆的半径长度对应所在分值段对应分数为多少
                int dex = LEVEL_GOLD * radius / section;
                //不让当前值 大于直线段对应分数，保证直线不会进入圆内部
                if (LEVEL_GOLD - dex < currentProgress)
                    currentProgress = LEVEL_GOLD - dex;
                //实际需要移动距离
                moveLength = (int) (section * (currentProgress * 1.f / LEVEL_GOLD));
            } else if (LEVEL_GOLD <= currentProgress && currentProgress < LEVEL_PLATINUM) {//黄金等级
                currentNumber = 2;
                int platinum_gold = LEVEL_PLATINUM - LEVEL_GOLD;
                //圆的半径长度对应所在分值段对应分数为多少
                int dex = platinum_gold * radius / section;

                //保证直线不会进入园内
                int section2 = currentProgress - LEVEL_GOLD;

                //不让当前值 大于直线段对应分数，保证直线不会进入圆内部
                if (platinum_gold - dex < currentProgress - LEVEL_GOLD)
                    section2 = platinum_gold - dex;
                //实际需要移动距离
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
                //当前值
                currentX = (int) animation.getAnimatedValue();
                //请求重绘
                invalidate();
            }
        });
        valueAnimatorLine.setDuration(500);
        ValueAnimator valueAnimatorCircle = ValueAnimator.ofInt(0, currentNumber);
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //当前值
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
