package com.yibh.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by y on 2016/5/21.
 * 优惠券视图, 上下带锯齿
 */
public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;
    private float gap = 8; //圆间距
    private float radius = 10; //圆半径
    private int circleViewNum;//圆数量
    private float v;

    public CouponDisplayView(Context context) {
        this(context, null);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //去锯齿
        mPaint.setStyle(Paint.Style.FILL); //填充
        mPaint.setColor(Color.WHITE); //颜色

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算出剩余的宽度
        if (v == 0) {
            v = (w - gap) % (gap + radius * 2);
        }
        circleViewNum = (int) ((w - gap) / (gap + radius * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circleViewNum; i++) {
            // 当v!=0时,说明数量没有被整除,v/2是为了保证第一个圆间距和最后一个圆间距大小相同
            float x = gap + radius + v / 2 + (i * (gap + radius * 2)); //圆心位置
            canvas.drawCircle(x, 0, radius, mPaint); //上边圆
            canvas.drawCircle(x, getHeight(), radius, mPaint); //下边圆
        }
    }
}
