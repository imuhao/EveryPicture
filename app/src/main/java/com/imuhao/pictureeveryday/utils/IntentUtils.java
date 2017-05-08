package com.imuhao.pictureeveryday.utils;

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

  public static void startAppShareText(Context context, String shareTitle, String shareText) {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain"); // 纯文本
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
    context.startActivity(Intent.createChooser(shareIntent, "将每日一图分享到"));
  }

  public static void startToWebActivity(Context context, Class clz, EssayBean data) {
    WebActivity.start(context, data.getDesc(), data.getUrl());
  }
}
