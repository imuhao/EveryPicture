package com.imuhao.pictureeveryday.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * @author Smile
 * @time 2017/5/8  下午2:32
 * @desc ${TODD}
 */
public class T {

  public static void show(Context context, String message) {
    if (context instanceof Activity) {
      View view = ((Activity) context).getWindow().getDecorView();
      Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    } else {
      throw new IllegalArgumentException("Context not instance Activity");
    }
  }

  public static void show(Context context, String message, String action,
      View.OnClickListener onClickListener) {
    if (context instanceof Activity) {
      View view = ((Activity) context).getWindow().getDecorView();
      Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
          .setAction(action, onClickListener).
          show();
    } else {
      throw new IllegalArgumentException("Context not instance Activity");
    }
  }
}
