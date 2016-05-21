package com.yibh.mytest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
    }


    public static Intent getNewIntetnt(Context context) {
        Intent intent = new Intent(context, MyViewActivity.class);
        return intent;
    }

}
