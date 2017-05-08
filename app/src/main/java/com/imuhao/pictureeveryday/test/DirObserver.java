package com.imuhao.pictureeveryday.test;

import android.os.FileObserver;
import android.util.Log;

/**
 * @author Smile
 * @time 2017/5/8  下午4:51
 * @desc ${TODD}
 */
public class DirObserver extends FileObserver {

  public DirObserver(String path) {
    super(path);
  }

  @Override public void onEvent(int event, String path) {
    Log.d("smile", "修改:" + path);
  }
}
