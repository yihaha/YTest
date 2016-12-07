package com.yibh.mytest.greendaotest.javabean;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yibh on 2016/12/6.
 */

@Entity
public class HotBean {
    //    {
//        "news_id": 9037003,
//            "url": "http://news-at.zhihu.com/api/2/news/9037003",
//            "thumbnail": "http://pic4.zhimg.com/3140cdf6747e1f60626f237eb0a30ae7.jpg",
//            "title": "读读日报 24 小时热门 TOP 5 · iPhone 内置铃声的故事"
//    }
    @Id
    @SerializedName("news_id")
    public Long mNewsId;
    @SerializedName("url")
    public String mUrl;
    @SerializedName("thumbnail")
    public String mThumbnail;
    @SerializedName("title")
    public String mTitle;
    @Generated(hash = 785991777)
    public HotBean(Long mNewsId, String mUrl, String mThumbnail, String mTitle) {
        this.mNewsId = mNewsId;
        this.mUrl = mUrl;
        this.mThumbnail = mThumbnail;
        this.mTitle = mTitle;
    }
    @Generated(hash = 1964254435)
    public HotBean() {
    }
    public Long getMNewsId() {
        return this.mNewsId;
    }
    public void setMNewsId(Long mNewsId) {
        this.mNewsId = mNewsId;
    }
    public String getMUrl() {
        return this.mUrl;
    }
    public void setMUrl(String mUrl) {
        this.mUrl = mUrl;
    }
    public String getMThumbnail() {
        return this.mThumbnail;
    }
    public void setMThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }
    public String getMTitle() {
        return this.mTitle;
    }
    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
