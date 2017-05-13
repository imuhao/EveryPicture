package com.imuhao.pictureeveryday.ui;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.imuhao.pictureeveryday.ui.Fragments.ABOUT;
import static com.imuhao.pictureeveryday.ui.Fragments.CATEGORY;
import static com.imuhao.pictureeveryday.ui.Fragments.PICTURE;
import static com.imuhao.pictureeveryday.ui.Fragments.SETTING;
import static com.imuhao.pictureeveryday.ui.Fragments.TODAY;

/**
 * @author Smile
 * @time 2017/5/13  上午9:31
 * @desc ${TODD}
 */
@Retention(RetentionPolicy.CLASS)
@StringDef({ TODAY, PICTURE, ABOUT, CATEGORY, SETTING })
public @interface Fragments {
  String TODAY = "今日";
  String PICTURE = "图片";
  String ABOUT = "关于";
  String CATEGORY = "文章";
  String SETTING = "设置";
}
