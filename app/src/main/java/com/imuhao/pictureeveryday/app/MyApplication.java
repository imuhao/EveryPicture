package com.imuhao.pictureeveryday.app;

import android.app.Application;
import android.os.Handler;



/**
 * @author Smile
 * @time 2016/6/22  11:24
 * @desc ${TODD}
 */
public class MyApplication extends Application {
    private static Handler mHandler;
    private static MyApplication application;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }




}
