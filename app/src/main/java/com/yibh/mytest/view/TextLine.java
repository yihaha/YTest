package com.yibh.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by y on 2016/6/1.
 * TextView上划一个横线,类似打折促销价格
 */
public class TextLine extends TextView{

    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    public TextLine(Context context) {
        this(context,null);
    }

    public TextLine(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

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
//        mPaint.setColor(Color.WHITE);
        int currentTextColor = this.getCurrentTextColor();
        mPaint.setColor(currentTextColor);
        //下面两种方式都可以实现
//        canvas.drawLine(0,mHeight/2,mWidth,mHeight/2,mPaint);
        canvas.drawRect(0,mHeight/2,mWidth,mHeight/2+2,mPaint);

    }
}
