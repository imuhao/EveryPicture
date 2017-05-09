package com.imuhao.pictureeveryday.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import com.imuhao.pictureeveryday.app.App;

/**
 * Created by smile on 16-11-1.
 * 设置主题的颜色
 */

public class ThemeUtils {

  private static int defaultColor = Color.parseColor("#54aee6");

  //设置主题的颜色
  public static void setThemeColor(int color) {
    SharedPreferences sp = App.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
    sp.edit().putInt("ThemeColor", color).apply();
  }

  //获取主题的颜色
  public static int getThemeColor() {
    SharedPreferences sp = App.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
    return sp.getInt("ThemeColor", defaultColor);
  }
}
