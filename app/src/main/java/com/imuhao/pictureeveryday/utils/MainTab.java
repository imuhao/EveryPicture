package com.imuhao.pictureeveryday.utils;

/**
 * @author Smile
 * @time 2017/2/14  下午2:48
 * @desc ${TODD}
 */
public enum MainTab {

  PICTURE("图片"), ABOUT("关于"), CATEGORY("文章"), SETTING("设置");

  private String name;

  MainTab(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
