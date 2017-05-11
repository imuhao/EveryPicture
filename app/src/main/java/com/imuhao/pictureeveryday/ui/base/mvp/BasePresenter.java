package com.imuhao.pictureeveryday.ui.base.mvp;

/**
 * @author Smile
 * @time 2017/4/19  上午9:30
 * @desc ${TODD}
 */
public abstract class BasePresenter<V extends BaseView> {

  public V view;

  protected void start() {
  }

  void attachModelView(V v) {
    this.view = v;
    start();
  }

  void detachView() {
    this.view = null;
  }

}