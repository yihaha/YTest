package com.yibh.mytest.refreshview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by y on 2016/5/21.
 * 自定义刷新View
 */
public class YRefreshView extends FrameLayout {
    private static final int PULL_DOWN_MODE = 1;
    private static final int PULL_UP_MODE = -1;
    private float header_height;
    private float pull_height;
    private View childView;
    private Context mContext;
    private boolean isRefreshing;
    private int PULL_MODE = PULL_DOWN_MODE;

    private OnRefreshListener onRefreshListener;
    private YHeaderView yHeaderView;
    private YFooterView yFooterView;
    private ValueAnimator valueAnimator;

    public YRefreshView(Context context) {
        this(context, null);
    }

    public YRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        this.mContext = context;
        header_height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
        pull_height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, context.getResources().getDisplayMetrics());
        if (getChildCount() > 1) {
            throw new RuntimeException("只能有一个子view");
        }
        this.post(new Runnable() {

            @Override
            public void run() {
                //得到子view,一般是Listview,或者RecycleView
                childView = getChildAt(0);
                addHeaderView();
                addFooterView();
                if (isOpenRefresh){
                    autoRefresh();
                }
            }
        });

    }

    private void addFooterView() {
        yFooterView = new YFooterView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.BOTTOM;
        yFooterView.setLayoutParams(layoutParams);
        addView(yFooterView);
    }

    private void addHeaderView() {
        yHeaderView = new YHeaderView(mContext);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.TOP;
        yHeaderView.setLayoutParams(layoutParams);
        addView(yHeaderView);
    }


    public interface OnRefreshListener {
        void onRefreshing();

        void onLoading();
    }

    public void setRefreshListener(OnRefreshListener listener) {
        this.onRefreshListener = listener;
    }

    private float mStartDownY;
    private float mMoveY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing) {
            return true; //交给onTouchEvent
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartDownY = ev.getY(); //得到按下时y坐标
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = ev.getY();
                float y = mMoveY - mStartDownY;
                if (childView != null) {
                    //向下滚动
                    if ((y > 0 && !ViewCompat.canScrollVertically(childView, -1))
                            //向上滚动
                            || (y < 0 && !ViewCompat.canScrollVertically(childView, 1))) {
                        return true;
                    }
                }
                return false;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRefreshing) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                isRefreshing = false;
                float y = event.getY() - mStartDownY;
                PULL_MODE = y > 0 ? PULL_DOWN_MODE : PULL_UP_MODE;
                //此处PULL_HEIGHT*2是为了保证当下拉或上拉的高度超过PULL_HEIGHT*2时,最大值为PULL_HEIGHT*2
                y = Math.min(pull_height * 2, Math.abs(y));
                //减速插值器,下拉或上拉时,速度先快后慢
                int refreshViewHeight = (int) (new DecelerateInterpolator(10).getInterpolation(y / pull_height / 2) * y / 2);
                float moveDistance = PULL_MODE == PULL_DOWN_MODE ? refreshViewHeight : -refreshViewHeight;
                if (childView != null) {
                    if (PULL_MODE == PULL_DOWN_MODE && !isChildViewCanScrollDown()) {
                        childView.setTranslationY(moveDistance);
                        yHeaderView.getLayoutParams().height = refreshViewHeight;
                        yHeaderView.isShowDown=true; //显示"下拉刷新"
                        yHeaderView.requestLayout();
                    } else if (PULL_MODE == PULL_UP_MODE && !isChildViewCanScrollUp()) {
                        childView.setTranslationY(moveDistance);
                        yFooterView.getLayoutParams().height = refreshViewHeight;
                        yFooterView.isShowUp=true;//显示"上拉加载更多"
                        yFooterView.requestLayout();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (childView != null) {
                    float height = Math.abs(childView.getTranslationY());
                    if (height > header_height) {
                        switch (PULL_MODE) {
                            case PULL_DOWN_MODE:
                                startRefresh(height);
                                break;

                            case PULL_UP_MODE:
                                startLoading(height);
                                break;

                            default:
                                break;
                        }
                    } else {
                        goBack();
                    }
                }

                break;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 回到初始位置
     */
    private void goBack() {
        valueAnimator = ValueAnimator.ofFloat(childView.getTranslationY(), 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                childView.setTranslationY(value);
                if (PULL_MODE == PULL_DOWN_MODE) {
                    yHeaderView.getLayoutParams().height = (int) value;
                    yHeaderView.requestLayout();
                } else if (PULL_MODE == PULL_UP_MODE) {
                    yFooterView.getLayoutParams().height = (int) -value;
                    yFooterView.requestLayout();
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    /**
     * 加载更多
     *
     * @param height
     */
    private void startLoading(float height) {
        isRefreshing = true;
        height = -height;
        valueAnimator = ValueAnimator.ofFloat(height, -header_height);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                childView.setTranslationY(value);
                yFooterView.getLayoutParams().height = (int) -value;
                yFooterView.requestLayout();
                if (value == -header_height) {
                    yFooterView.onStartLoading();
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoading();
                    }
                }

            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    /**
     * 刷新
     *
     * @param height
     */
    private void startRefresh(float height) {
        isRefreshing = true;
        valueAnimator = ValueAnimator.ofFloat(height, header_height);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                childView.setTranslationY(value);
                yHeaderView.getLayoutParams().height = (int) value;
                yHeaderView.requestLayout();
                if (value == header_height) {
                    yHeaderView.onStartRefresh();
                    if (onRefreshListener != null) {
                        onRefreshListener.onRefreshing();
                    }
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }


    private boolean isChildViewCanScrollDown() {
        return childView != null && ViewCompat.canScrollVertically(childView, -1);
    }

    //上滑
    private boolean isChildViewCanScrollUp() {
        return childView != null && ViewCompat.canScrollVertically(childView, 1);
    }

    public void finishRefresh(){
        switch (PULL_MODE){
            case PULL_DOWN_MODE:
                yHeaderView.onResetStatus(false);
                break;
            case PULL_UP_MODE:
                yFooterView.onResetStatus(false);
                break;
        }
        isRefreshing=false;
        goBack();
    }

    private boolean isOpenRefresh=true; //是否开启自动刷新

    /**
     * 根据参数判断是否开启自动刷新功能 ,默认开通
     * @param isOpen
     */
    public void onOpenAutoRefresh(boolean isOpen){
        this.isOpenRefresh=isOpen;
    }

    /**
     * 自动刷新
     */
    private void autoRefresh(){
        isRefreshing=true;
        if (childView!=null){
            childView.setTranslationY(header_height);
            yHeaderView.getLayoutParams().height= (int) header_height;
            yHeaderView.requestLayout();
            yHeaderView.onStartRefresh();
            if (onRefreshListener!=null){
                onRefreshListener.onRefreshing();
            }
        }
    }

}
