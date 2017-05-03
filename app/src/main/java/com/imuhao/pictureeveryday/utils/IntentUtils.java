package com.imuhao.pictureeveryday.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.ui.activity.WebActivity;

/**
 * @author Smile
 * @time 2016/6/22  11:29
 * @desc ${TODD}
 */
public class IntentUtils {

  /**
   * 启动一个意图,并且将当前页面关闭掉
   */
  public static void startActivityFromFinish(Context context, Class clzz) {
    Intent intent = new Intent(context.getApplicationContext(), clzz);
    context.startActivity(intent);
    ((Activity) context).finish();
  }

  public static void startAppShareText(Context context, String shareTitle, String shareText) {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain"); // 纯文本
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
    //设置分享列表的标题，并且每次都显示分享列表
    context.startActivity(Intent.createChooser(shareIntent, "将每日一图分享到"));
  }

  public static void startToWebActivity(Context context, Class clz, EssayBean data) {
    WebActivity.start(context, data.getDesc(), data.getUrl());

    /*Intent intent = new Intent(context, clz);
    intent.putExtra("flagTitle", data.getType());
    intent.putExtra("title", data.getDesc());
    intent.putExtra("url", data.getUrl());
    context.startActivity(intent);*/
  }
}
