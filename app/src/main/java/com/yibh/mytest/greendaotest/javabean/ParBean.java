package com.yibh.mytest.greendaotest.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yibh on 2016/12/6.
 */

public class ParBean {
    @SerializedName("recent")
    public List<HotBean> mHotBeanList;
}
