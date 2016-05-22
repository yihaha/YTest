package com.yibh.mytest.refreshview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yibh.mytest.R;

import java.util.ArrayList;

public class RefreshActivity extends AppCompatActivity {

    private YRefreshView refreshView;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        refreshView = (YRefreshView) findViewById(R.id.refreview);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myAdapter = new MyAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        refreshView.onOpenAutoRefresh(false);
        refreshView.setRefreshListener(new YRefreshView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                for (int i = 0; i < 5; i++) {
                    strings.add(0, "下拉刷新数据" + i);
                }
                //模拟延迟
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myAdapter.notifyDataSetChanged();
                        refreshView.finishRefresh();
                    }
                }, 2000);

            }

            @Override
            public void onLoading() {
                for (int i = 0; i < 5; i++) {
                    strings.add("上拉加载数据" + i);
                }

                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.finishRefresh();
                        myAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        strings = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            strings.add(i + "~~ 数据");
        }


    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.refre_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String s = strings.get(position);
            holder.textView.setText(s);
        }

        @Override
        public int getItemCount() {
            return strings.size();
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }

    }


    public static Intent getNewIntetnt(Context context) {
        Intent intent = new Intent(context, RefreshActivity.class);
        return intent;
    }

}
