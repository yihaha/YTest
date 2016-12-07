package com.yibh.mytest.greendaotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yibh.mytest.R;
import com.yibh.mytest.greendaotest.javabean.HotBean;

import java.util.List;

/**
 * Created by yibh on 2016/12/6.
 */

public class HotAdapter extends RecyclerView.Adapter<HotAdapter.HotViewHolder> {
    private Context mContext;
    private List<HotBean> mHotBeanList;

    public HotAdapter(List<HotBean> list, Context context) {
        mHotBeanList = list;
        mContext = context;
    }


    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.greendao_recycle_item, parent, false);
        return new HotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotViewHolder holder, int position) {
        HotBean hotBean = mHotBeanList.get(position);
        holder.mTitle.setText(hotBean.mTitle);
        holder.mNewId.setText(hotBean.mNewsId + "");
        holder.mUrlId.setText(hotBean.mUrl);
        Glide.with(mContext).load(hotBean.mThumbnail).placeholder(R.mipmap.ic_launcher).into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        return mHotBeanList.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private TextView mNewId;
        private ImageView mImg;
        private TextView mUrlId;

        public HotViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title_id);
            mNewId = (TextView) itemView.findViewById(R.id.news_id);
            mImg = (ImageView) itemView.findViewById(R.id.img);
            mUrlId = (TextView) itemView.findViewById(R.id.url_id);
        }
    }

    public void setRefresh(List<HotBean> list) {
        mHotBeanList = list;
        notifyDataSetChanged();
    }

}
