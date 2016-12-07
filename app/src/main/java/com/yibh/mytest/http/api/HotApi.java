package com.yibh.mytest.http.api;

import com.yibh.mytest.greendaotest.javabean.ParBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yibh on 2016/12/5.
 */

public interface HotApi {
    @GET("3/news/{hot}")
    Call<ParBean> getData(@Path("hot")String hot);
}
