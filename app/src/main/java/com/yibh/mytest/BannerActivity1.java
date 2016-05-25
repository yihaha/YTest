package com.yibh.mytest;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class BannerActivity1 extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewpager;
    private ArrayList<BFragment> bFragments;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner1);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        bFragments = new ArrayList<>();
        titles = new String[]{"标题一", "标题二", "标题三", "标题四"};
        for (int i = 0; i < 4; i++) {
            BFragment bFragment = new BFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TAG",titles[i]);
            bFragment.setArguments(bundle);
            bFragments.add(bFragment);
        }

        MyPageAdapter myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(myPageAdapter);
        mViewpager.setOffscreenPageLimit(4);
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);

    }

    class MyPageAdapter extends FragmentStatePagerAdapter {

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return bFragments.get(position);
        }

        @Override
        public int getCount() {
            return bFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
