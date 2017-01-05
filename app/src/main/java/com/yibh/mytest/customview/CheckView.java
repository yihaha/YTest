package com.yibh.mytest.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yibh.mytest.R;

/**
 * Created by yibh on 2017/1/5.
 */

public class CheckView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private Bitmap mBitmap;
    private int mBtWidth;
    private int mRadius;
    private int mBtHeight;
    private long mTotalTime = 1000;   //1秒执行完成
    private long mIntervalTime = 20;    //20毫秒刷新一下
    private float mCurrProgress;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.GRAY);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.musicbj);
        mBtWidth = mBitmap.getWidth();
        mBtHeight = mBitmap.getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(mWidth, mHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);   //移到中点
        canvas.drawCircle(0, 0, mRadius, mPaint);
        int w = (int) (mRadius * 0.6);

        //绘制图片的区域
        Rect src = new Rect(0, 0, (int) (mBtWidth * mCurrProgress), mBtHeight);
        //计算进度
        mCurrProgress = (mCurrProgress + mIntervalTime) / mTotalTime;
        //显示目标图片的区域
        Rect dst = new Rect(-w, -w, w, w);
        canvas.drawBitmap(mBitmap, src, dst, null);
        Log.w("w", w + "");
        Log.w("mBtHeight", mBtHeight + "");
        Log.w("mBtWidth", mBtWidth + "");
    }


    public void startCheck() {
        mCurrProgress = 0;
        invalidate();
    }

    public void stopCheck() {

    }

}
