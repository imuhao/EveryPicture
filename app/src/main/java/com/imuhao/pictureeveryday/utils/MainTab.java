package com.imuhao.pictureeveryday.utils;

import android.support.v4.app.Fragment;
import com.imuhao.pictureeveryday.ui.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.ui.fragment.DayListFragment;
import com.imuhao.pictureeveryday.ui.fragment.PictureFragment;
import com.imuhao.pictureeveryday.ui.fragment.SettingFragment;

/**
 * @author Smile
 * @time 2017/2/14  下午2:48
 * @desc ${TODD}
 */
public enum MainTab {

  PICTURE("图片", PictureFragment.newInstance()), ABOUT("关于", null), CATEGORY("文章",
      CategoryFragment.newInstance()), SETTING("设置", SettingFragment.newInstance()), TODAY("今日",
      DayListFragment.newInstance());

  private String name;
  private Fragment fragment;

  MainTab(String name, Fragment fragment) {
    this.name = name;
    this.fragment = fragment;
  }

  public String getName() {
    return name;
  }

  public Fragment getFragment() {
    return fragment;
  }
}
