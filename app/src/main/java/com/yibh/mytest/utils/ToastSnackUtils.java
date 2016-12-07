package com.yibh.mytest.utils;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.yibh.mytest.app.MApp;

/**
 * Created by yibh on 2016/12/7.
 */

public class ToastSnackUtils {

    public static void toastShort(String msg) {
        Toast.makeText(MApp.getmContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void toastLong(String msg) {
        Toast.makeText(MApp.getmContext(), msg, Toast.LENGTH_LONG);
    }

    public static void snackShort(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
    }

    public static void snackLong(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
    }

    public static void snackIndefinite(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
    }

}
