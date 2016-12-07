package com.yibh.mytest.app;

import android.app.Application;
import android.content.Context;

import com.yibh.mytest.greendaotest.javabean.DaoMaster;
import com.yibh.mytest.greendaotest.javabean.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by yibh on 2016/12/6.
 */

public class MApp extends Application {
    public static Context mContext;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=this;
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "hot-db");
        Database db = devOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();

    }

    public static Context getmContext(){
        return mContext;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
