package com.yibh.mytest.http;

import com.yibh.mytest.http.api.HotApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yibh on 2016/12/6.
 */

public class HttpRetrofit {
    public static String HOT_URL = "http://news-at.zhihu.com/api/";
    public static Object mObject = new Object();
    public static HotApi mHotApi;


    public static HotApi getHotService() {
        synchronized (mObject) {
            if (mHotApi == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HOT_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                mHotApi = retrofit.create(HotApi.class);
            }
        }
        return mHotApi;
    }
}
