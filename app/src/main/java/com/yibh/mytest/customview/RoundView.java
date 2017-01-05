package com.yibh.mytest.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by yibh on 2017/1/3.
 * 饼图,每块的百分比
 */

public class RoundView extends View {
    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

//    private int[] mColors = {Color.BLACK, Color.BLUE, Color.GREEN};

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private List<RoundViewBean> mBeanList;
    private float mRadius;
    private float mCurrAngle;
    private RectF mRectF;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        //设置饼图半径
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.5);
        mRectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null != mBeanList && mBeanList.size() > 0) {
            canvas.translate(mWidth / 2, mHeight / 2);  //画布移动到中点
            for (int i = 0; i < mBeanList.size(); i++) {
                RoundViewBean roundViewBean = mBeanList.get(i);
                mPaint.setColor(roundViewBean.mColor);
                canvas.drawArc(mRectF, mCurrAngle, roundViewBean.mSweepAngle, true, mPaint);
                Log.w("mSweepAngle", roundViewBean.mSweepAngle + "");
                Log.w("mCurrAngle", mCurrAngle + "");
                mCurrAngle += roundViewBean.mSweepAngle;
//                canvas.drawText(roundViewBean.mName,);
            }
        }
    }

    /**
     * 设置数据源,并执行具体操作
     *
     * @param beanList
     */
    public void setData(List<RoundViewBean> beanList) {
        float totalValue = 0;
        //计算出总和
        for (int i = 0; i < beanList.size(); i++) {
            totalValue += beanList.get(i).mValue;
        }
        //计算出每个对象占用的百分比,弧度
        for (int i = 0; i < beanList.size(); i++) {
            RoundViewBean roundViewBean = beanList.get(i);
            roundViewBean.mPercentage = roundViewBean.mValue / totalValue;
            roundViewBean.mSweepAngle = roundViewBean.mPercentage * 360;
            roundViewBean.mColor = mColors[i % mColors.length];
            Log.w("i=" + i + "mPercentage", roundViewBean.mPercentage + "");

        }
        mBeanList = beanList;
        Log.w("mBeanList->0", mBeanList.get(0).mColor + "");
        invalidate();
    }

    /**
     * 设置起始角度
     *
     * @param startAngle
     */
    public void setStartAngle(float startAngle) {
        if (null != mBeanList && mBeanList.size() > 0) {
            mCurrAngle = startAngle;
            invalidate();
        }
    }

}
