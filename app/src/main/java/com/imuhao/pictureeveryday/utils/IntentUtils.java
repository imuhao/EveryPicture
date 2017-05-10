package com.imuhao.pictureeveryday.utils;

import android.content.Context;
import android.content.Intent;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.ui.activity.WebActivity;

/**
 * @author Smile
 * @time 2016/6/22  11:29
 * @desc ${TODD}
 */
public class IntentUtils {

  //分享应用
  public static void startAppShareText(Context context) {
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.setType("text/plain"); // 纯文本
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
    shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_content));
    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.app_name)));
  }

  public static void startToWebActivity(Context context, EssayBean data) {
    WebActivity.start(context, data.getDesc(), data.getUrl());
  }
}
