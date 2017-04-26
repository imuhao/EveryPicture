package com.imuhao.pictureeveryday.ui.base.mvp;

/**
 * @author Smile
 * @time 2017/4/19  上午9:30
 * @desc ${TODD}
 */
public interface BaseView {

  void showWaitDialog();

  void hideWaitDialog();

  void showToast(String message);
}
