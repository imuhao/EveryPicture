package com.imuhao.pictureeveryday.utils;

import android.graphics.Color;

/**
 * @author Smile
 * @time 2016/6/22  16:08
 * @desc ${TODD}常量
 */
public class Constant {

  public static final String BASE_URL = "http://gank.io/api/";
  //福利页面Url
  private static final String FULI_URL = "http://gank.io/api/data/福利/%s/%i";

  public static String getFuliUrl(int pageSize, int index) {
    return FULI_URL.replaceAll("%s", String.valueOf(pageSize))
        .replaceAll("%i", String.valueOf(index));
  }

  //显示的总天数
  public static final int SHOW_DAY_SUM = 7;

  public static final String BIG_IMG_POSITION = "bigimgposition";
  public static final String BIG_IMG_LIST = "bigimglist";

  //标签
  private static final String FlagAndroid = "Android";
  private static final String FlagIOS = "iOS";
  private static final String FlagVideo = "休息视频";
  private static final String FlagJS = "前端";
  private static final String FlagExpand = "拓展资源";
  private static final String FlagRecommend = "瞎推荐";
  private static final String FlagAPP = "App";

  public static final String[] TITLES = {
      Constant.FlagAndroid, Constant.FlagIOS, Constant.FlagVideo, Constant.FlagJS,
      Constant.FlagExpand, Constant.FlagRecommend, Constant.FlagAPP
  };

  public static final int[] COLORS = {
      Color.parseColor("#54aee6"), Color.parseColor("#34A853"), Color.parseColor("#54aee6"),
      Color.parseColor("#34A853"), Color.parseColor("#54aee6"), Color.parseColor("#34A853"),
      Color.parseColor("#54aee6")
  };
}
