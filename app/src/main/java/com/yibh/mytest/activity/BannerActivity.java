package com.yibh.mytest.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yibh.mytest.R;
import com.yibh.mytest.view.YRollViewpager;

public class BannerActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        mTitle = (TextView) findViewById(R.id.title);
        mLL = (LinearLayout) findViewById(R.id.point_ll);
        mViwePager = (YRollViewpager) findViewById(R.id.viewpager);
        mViwePager.initData(imgs);
        //mViwePager.setOnPageChangeListener(); 方法过时
        mViwePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //页面被选中
            @Override
            public void onPageSelected(int position) {
                int pos = position % imgs.length;
                //改变点的颜色
                mLL.getChildAt(pos).setEnabled(true);
                mLL.getChildAt(mPreviousPoint).setEnabled(false);
                mPreviousPoint = pos;

                mTitle.setText(titles[pos]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            ImageView imageView = new ImageView(this);
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
