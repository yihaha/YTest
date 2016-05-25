package com.yibh.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        //执行冒泡排序
        findViewById(R.id.bubble_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortTest.bubbleSort();
            }
        });

    }
}
