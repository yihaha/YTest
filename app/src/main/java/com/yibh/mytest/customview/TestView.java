package com.yibh.mytest.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yibh on 2017/1/5.
 */

public class TestView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);

        RectF rectF = new RectF(0, -100, 100, 0);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(rectF, mPaint);
        canvas.skew(1, 0);   //错切
        canvas.skew(0, 1);   //错切
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF, mPaint);
    }
}
