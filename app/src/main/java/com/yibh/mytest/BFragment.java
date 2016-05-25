package com.yibh.mytest;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibh.mytest.view.YRollViewpager;

public class BFragment extends Fragment {

    private YRollViewpager mViwePager;

    //图片
    private int[] imgs = {R.drawable.nv0, R.drawable.nv1, R.drawable.nv2, R.drawable.nv3};
    //内容
    private String[] titles = {"静水流深，沧笙踏歌；三生阴晴圆缺，一朝悲欢离合"
            , "蓄起亘古的情丝，揉碎殷红的相思"
            , "用我三生烟火，换你一世迷离"
            , "任他凡事清浊，为你一笑间轮回甘堕"
    };
    private LinearLayout mLL;
    private TextView mTitle;
    private int mPreviousPoint; //上一个点的位置
    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final String tag = getArguments().getString("TAG");
        if (tag.equals("标题一")) {
            view = inflater.inflate(R.layout.activity_banner, container, false);
            return view;
        }
        return null;

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String tag = getArguments().getString("TAG");
        if (tag.equals("标题一")) {

            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);

            mTitle = (TextView) view.findViewById(R.id.title);
            mLL = (LinearLayout) view.findViewById(R.id.point_ll);
            mViwePager = (YRollViewpager) view.findViewById(R.id.viewpager);

            mViwePager.initData(imgs);
            //mViwePager.setOnPageChangeListener(); 方法过时
            mViwePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //防止下拉刷新,与滑动冲突
                    if (positionOffsetPixels > 0) {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }

                //页面被选中
                @Override
                public void onPageSelected(int position) {
                    int pos = position % imgs.length;
//                  //改变点的颜色
                    mViwePager.mPreviousPoint = pos;
                    mLL.getChildAt(pos).setEnabled(true);
                    mLL.getChildAt(mPreviousPoint).setEnabled(false);

                    if (tag.equals("标题一")) {
                        mPreviousPoint = pos;
                        Log.w("onPageSelected", pos + " " + titles[pos]);
                    }
                    mTitle.setText(titles[pos]);

                    Log.w("onPageSelected", pos + " ");

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    switch (state) {
                        case ViewPager.SCROLL_STATE_IDLE:
                            swipeRefreshLayout.setEnabled(true);
                            break;
                    }
                }
            });

            mViwePager.setOnpageItemClickListener(new YRollViewpager.OnpageItemClickListener() {
                @Override
                public void clickPage(int position) {
                    Snackbar.make(mViwePager, titles[position], Snackbar.LENGTH_SHORT).show();
                }
            });

            mTitle.setText(titles[0]); //设置默认标题
            mLL.removeAllViews();
            //初始化点
            for (int i = 0; i < imgs.length; i++) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.viewpager_selector);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT
                        , LinearLayout.LayoutParams.WRAP_CONTENT);

                if (i != 0) {
                    layoutParams.leftMargin = 9;
                    imageView.setEnabled(false);
                }
                imageView.setLayoutParams(layoutParams);
                mLL.addView(imageView);

            }

            mViwePager.startRollPage();


        }
    }

}
