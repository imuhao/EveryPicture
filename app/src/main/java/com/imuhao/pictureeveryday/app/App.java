package com.imuhao.pictureeveryday.app;

import android.app.Application;
import com.github.moduth.blockcanary.BlockCanary;

/**
 * @author Smile
 * @time 2016/6/22  11:24
 * @desc ${TODD}
 */
public class App extends Application {

  public void onCreate() {
    super.onCreate();
    BlockCanary.install(this, new AppBlockCanaryContext()).start();
  }
}
