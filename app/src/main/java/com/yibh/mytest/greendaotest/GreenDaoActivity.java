package com.yibh.mytest.greendaotest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.yibh.mytest.R;
import com.yibh.mytest.app.MApp;
import com.yibh.mytest.greendaotest.javabean.HotBean;
import com.yibh.mytest.greendaotest.javabean.HotBeanDao;
import com.yibh.mytest.greendaotest.javabean.ParBean;
import com.yibh.mytest.http.HttpRetrofit;
import com.yibh.mytest.utils.ToastSnackUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GreenDaoActivity extends AppCompatActivity {

    private HotBeanDao mHotBeanDao;
    private ProgressBar mProgress;
    private RecyclerView mRecyclerView;
    private List<HotBean> mHotBeanList = new ArrayList<>();
    private HotAdapter mHotAdapter;
    private Button mShowDataBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        initView();
        mHotBeanDao = ((MApp) getApplication()).getDaoSession().getHotBeanDao();


        HttpRetrofit.getHotService().getData("hot").enqueue(new Callback<ParBean>() {
            @Override
            public void onResponse(Call<ParBean> call, Response<ParBean> response) {
                List<HotBean> hotBeanList = response.body().mHotBeanList;
                Log.w("onResponse", "");
                if (hotBeanList != null && hotBeanList.size() > 0) {
                    for (int i = 0; i < hotBeanList.size(); i++) {
                        mHotBeanDao.insertOrReplace(hotBeanList.get(i));
                    }
                    mShowDataBut.setEnabled(true);
                }
                ToastSnackUtils.snackShort(mRecyclerView, "数据加载完成!");
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ParBean> call, Throwable t) {
                Log.w("onFailure", t.toString());
                ToastSnackUtils.snackShort(mRecyclerView, "数据加载失败!");
                mProgress.setVisibility(View.GONE);
                mShowDataBut.setText("失败!");
            }
        });


    }

    private void initView() {
        mShowDataBut = (Button) findViewById(R.id.showdata_id);
        mProgress = (ProgressBar) findViewById(R.id.loadview);
        mRecyclerView = (RecyclerView) findViewById(R.id.hot_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mHotAdapter = new HotAdapter(mHotBeanList, this);
        mRecyclerView.setAdapter(mHotAdapter);

        mShowDataBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShowDataBut.setVisibility(View.GONE);
                mProgress.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                List<HotBean> list = mHotBeanDao.queryBuilder().orderAsc(HotBeanDao.Properties.MNewsId).build().list();
                mHotAdapter.setRefresh(list);
            }
        });
    }
}
