package com.pawanjia.progressbardemo;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Description 诊所积分等级进度
 *
 * @author liupeng502
 * @data 2017/8/11
 */

public class PayProgressBar extends View {

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
    private int LEVEL_COMMON = 0;
    private int LEVEL_GOLD = 20000;
    private int LEVEL_PLATINUM = 60000;
    private int LEVEL_DIAMOND = 180000;
    private List<String> listLevel;
    private List<Integer> listPoint;
    private String level;
    private int hightPoint;
    private int measuredWidth;
    private Bitmap srcBitmap;


    public PayProgressBar(Context context) {
        this(context, null);
    }

    public PayProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
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
        listPoint=new ArrayList<>();
        listPoint.add(LEVEL_COMMON);
        listPoint.add(LEVEL_GOLD);
        listPoint.add(LEVEL_PLATINUM);
        listPoint.add(LEVEL_DIAMOND);
        srcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgressBar(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();

    }

    private void drawProgressBar(Canvas canvas) {
        mPath.moveTo(0,startY);
        mPath.lineTo(measuredWidth,startY);
        canvas.drawPath(mPath,mPaintBg);

        mPath.moveTo(0,startY);
        mPath.lineTo(currentX,startY);
        canvas.drawPath(mPath,mPaintProgress);
       // canvas.drawBitmap(srcBitmap,);
    }


    /**
     * 最大分数
     *
     * @param maxValue
     * @return
     */
    public PayProgressBar setMaxValue(int maxValue) {
        if (0 < maxValue) {
            this.maxValue = maxValue;
            enableMaxValue = true;
        } else {
            enableMaxValue = false;
        }
        return this;
    }


    public PayProgressBar setLevelPoint(ArrayList<String> listLevel,ArrayList<Integer> listPoint) {
        if (listLevel != null) {
            this.listLevel=listLevel;
        }
        if (listPoint != null && listPoint.size()==4) {
            this.listPoint.clear();
            this.listPoint.addAll(listPoint);
        }
        return this;
    }

    /**
     * 实际分数  先调setLevel();
     *
     * @param currentProgress
     * @return
     */
    public PayProgressBar setProgress(int currentProgress) {
        switch(level){
            case "0":
                hightPoint=listPoint.get(0);
                moveLength = (int) (measuredWidth * (currentProgress * 1.f / hightPoint));
                break;
            case "1":
                hightPoint=listPoint.get(1);
                moveLength = (int) (measuredWidth * (currentProgress * 1.f / hightPoint));
                break;
            case "2":
                hightPoint=listPoint.get(2);
                moveLength = (int) (measuredWidth * (currentProgress * 1.f / hightPoint));
                break;
            case "3":
                hightPoint=listPoint.get(3);
                moveLength = (int) (measuredWidth * (currentProgress * 1.f / hightPoint));
                break;
            default:
        }
        startAnimation();
        return this;
    }



    public PayProgressBar setProgressLevel(String level) {
        this.level=level;
        return this;
    }


    private void startAnimation() {
        ValueAnimator valueAnimatorLine = ValueAnimator.ofInt(0, moveLength);
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
