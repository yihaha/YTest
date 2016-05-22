package com.yibh.mytest.refreshview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by y on 2016/5/21.
 */
public class YFooterView extends View {

    private float footHeight;
    private Paint bgPaint;
    private Paint textPaint;
    private boolean isLoading;
    private int mHeight;
    private int mWidth;
    public boolean isShowUp;//判断是否显示"上拉加载更多"

    public YFooterView(Context context) {
        this(context, null, 0);
    }

    public YFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        footHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.BLACK);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25f);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        if (changed) {
            if (mHeight < footHeight) {
                currStatus = AnimatorStatus.PULL_DOWN;
            } else {
                currStatus = AnimatorStatus.DRAG_DOWN;
            }
            if (isLoading) {
                currStatus = AnimatorStatus.REFRESH;
            }
        }
    }

    private AnimatorStatus currStatus;

    private enum AnimatorStatus {
        PULL_DOWN,
        DRAG_DOWN,
        REFRESH
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (currStatus) {
            case PULL_DOWN:
                canvas.drawRect(0, 0, mWidth, mHeight, bgPaint);
                if (isShowUp) {
                    canvas.drawText("上拉加载更多", mWidth / 2, mHeight / 2, textPaint);
                }
                break;
            case DRAG_DOWN:
                canvas.drawRect(0, 0, mWidth, mHeight, bgPaint);
                canvas.drawText("松手加载更多", mWidth / 2, mHeight / 2, textPaint);
                break;
            case REFRESH:
                loading(canvas, getLoadRation());
                invalidate();
                break;
        }
    }


    private long mStartTime;
    private long mTimeLimit = 10000;

    private float getLoadRation() {
        if (isLoading) {
            return (System.currentTimeMillis() - mStartTime) % mStartTime / (float) mTimeLimit;
        }
        return 1;
    }

    private void loading(Canvas canvas, float loadRation) {
        canvas.drawRect(0, 0, mWidth, mHeight, bgPaint);
        int count = (int) (loadRation * 4);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            stringBuilder.append(".");
        }
        canvas.drawText("正在加载" + stringBuilder.toString(), mWidth / 2, mHeight / 2, textPaint);
    }

    public void onStartLoading() {
        isLoading = true;
        mStartTime = System.currentTimeMillis();
        currStatus = AnimatorStatus.REFRESH;
        isShowUp=false;
        requestLayout();
    }

    public void onResetStatus(boolean isLoading) {
        this.isLoading = isLoading;
    }

}
