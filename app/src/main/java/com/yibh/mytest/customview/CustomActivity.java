package com.yibh.mytest.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yibh.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity {

    private RoundView mRoundView;
    private TestView mTestView;
    private CheckView mCheckView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        mRoundView = (RoundView) findViewById(R.id.roundview);
        mTestView = (TestView) findViewById(R.id.testview);
        mCheckView = (CheckView) findViewById(R.id.checkview);
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

    public void startCheckView(View view) {
        mCheckView.startCheck();
    }

    public void stopCheckView(View view) {
        mCheckView.stopCheck();
    }

}
