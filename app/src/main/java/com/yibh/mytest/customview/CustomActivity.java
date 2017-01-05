package com.yibh.mytest.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yibh.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    private RoundView mRoundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        mRoundView = (RoundView) findViewById(R.id.roundview);
        setRoundData();
    }

    private void setRoundData() {
        List<RoundViewBean> roundViewList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RoundViewBean roundViewBean = new RoundViewBean();
            roundViewBean.mName = i + "测试";
            roundViewBean.mValue = i + 1 * i * 10;
            roundViewList.add(roundViewBean);
        }
        mRoundView.setData(roundViewList);

    }
}
