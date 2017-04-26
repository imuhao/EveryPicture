package com.imuhao.pictureeveryday.app;

import android.app.Application;
import com.lzy.okgo.OkGo;

/**
 * @author Smile
 * @time 2016/6/22  11:24
 * @desc ${TODD}
 */
public class MyApplication extends Application {

  public void onCreate() {
    super.onCreate();
    OkGo.init(this);

    //OkGo.getInstance();

  }
}
