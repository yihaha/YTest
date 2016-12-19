package com.yibh.mytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yibh.mytest.view.CDView;

public class CDTestActivity extends AppCompatActivity {

    private CDView mCdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdtest);
        mCdView = (CDView) findViewById(R.id.cdview);
    }

    public void start(View view) {
        mCdView.start();
    }

    public void pause(View view) {
        mCdView.pause();
    }

    public void setCdView(View view){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fj);
        mCdView.setCoverBitmap(bitmap);
        mCdView.start();
    }

}
