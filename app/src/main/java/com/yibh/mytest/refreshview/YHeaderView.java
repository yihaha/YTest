package com.yibh.mytest.refreshview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by y on 2016/5/21.
 */
public class YHeaderView extends View {

    private float header_height;
    private float circleRadius;
    private Path mPath;
    private int mHeiht;
    private int mWidth;
    private boolean isRefresh; //是否在刷新状态
    private Paint bgPaint;
    private Paint frontPaint;
    private Paint aniPaint;
    public boolean isShowDown=true; //是否显示下拉刷新,用来判断是刷新完成,还是按着下滑

    public YHeaderView(Context context) {
        this(context, null, 0);
    }

    public YHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //高度
        header_height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        //半径
        circleRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, context.getResources().getDisplayMetrics());

        //新建3个画笔,分别是背景,文字,动画效果
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.BLACK);

        frontPaint = new Paint();
        frontPaint.setAntiAlias(true);
        frontPaint.setStyle(Paint.Style.FILL);
        frontPaint.setColor(Color.WHITE);
        frontPaint.setTextAlign(Paint.Align.CENTER); //居中
        frontPaint.setTextSize(25f);

        aniPaint = new Paint();
        aniPaint.setAntiAlias(true);
        aniPaint.setStyle(Paint.Style.STROKE);
        aniPaint.setColor(Color.WHITE);
        aniPaint.setStrokeWidth(2f);

        mPath = new Path(); //路径

    }


    private enum AnimatorStatus {
        PULL_DOWN, //小于头部高度时
        DRAG_DOWN, //大于头部高度
        REFRESH   //刷新时
    }

    private AnimatorStatus currStatus = AnimatorStatus.PULL_DOWN; //默认状态

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mHeiht = getMeasuredHeight();
            mWidth = getMeasuredWidth();
            if (mHeiht < header_height) {
                currStatus = AnimatorStatus.PULL_DOWN;
            } else {
                currStatus = AnimatorStatus.DRAG_DOWN;
            }
            if (isRefresh) {
                currStatus = AnimatorStatus.REFRESH;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (currStatus) {
            case PULL_DOWN:
                canvas.drawRect(0, 0, mWidth, mHeiht, bgPaint);
                if (isShowDown) {
                    canvas.drawText("下拉刷新", mWidth / 2, mHeiht / 2, frontPaint);
                }
                break;
            case DRAG_DOWN:
                canvas.drawRect(0, 0, mWidth, mHeiht, bgPaint);
                canvas.drawText("松开刷新", mWidth / 2, mHeiht / 2, frontPaint);
                break;

            case REFRESH:
                refresh(canvas, getRefreshRation());
                invalidate();
                break;
        }

    }


    private long mStartTime;
    private long mTimeLimit = 10000;

    private float getRefreshRation() {
        if (isRefresh) {
            return (System.currentTimeMillis() - mStartTime) % mStartTime / (float) mTimeLimit;
        }
        return 1;
    }

    private void refresh(Canvas canvas, float refreshRation) {
        canvas.drawRect(0, 0, mWidth, header_height, bgPaint); //背景
        canvas.drawCircle(mWidth / 2, header_height / 2, circleRadius, aniPaint); //圆
        int x0 = mWidth / 2;
        float y0 = header_height / 2;

        //秒针长度
        double v = refreshRation * Math.PI * 24;
        float x1 = (float) (x0 + circleRadius * Math.cos(v) * 0.9);
        float y1 = (float) (y0 + circleRadius * Math.sin(v) * 0.9);
        mPath.reset();
        mPath.moveTo(x0, y0);
        mPath.lineTo(x1, y1);
        canvas.drawPath(mPath, aniPaint);

        v = refreshRation * Math.PI * 12;
        x1 = (float) (x0 + circleRadius * Math.cos(v) * 0.6);
        y1 = (float) (y0 + circleRadius * Math.sin(v) * 0.6);
        mPath.reset();
        mPath.moveTo(x0, y0);
        mPath.lineTo(x1, y1);
        canvas.drawPath(mPath, aniPaint);

        v = refreshRation * Math.PI * 6;
        x1 = (float) (x0 + circleRadius * Math.cos(v) * 0.4);
        y1 = (float) (y0 + circleRadius * Math.sin(v) * 0.4);
        mPath.reset();
        mPath.moveTo(x0, y0);
        mPath.lineTo(x1, y1);
        canvas.drawPath(mPath, aniPaint);


    }


    public void onStartRefresh() {
        isRefresh = true;
        mStartTime = System.currentTimeMillis();
        currStatus = AnimatorStatus.REFRESH;
        isShowDown=false; //刷新就不再显示"下拉刷新"
        requestLayout();
    }

    public void onResetStatus(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

}
