package com.imuhao.pictureeveryday.app;

import android.app.Application;
import android.content.Context;

/**
 * @author Smile
 * @time 2016/6/22  11:24
 * @desc ${TODD}
 */
public class App extends Application {
  public static Context context;

  public void onCreate() {
    super.onCreate();
    context = this;
    InitializeService.lunch(this);
  }

  public static Context getContext() {
    return context;
  }
}
