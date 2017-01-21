package com.ylian.common.base.mvp;

/**
 * Created by dafan on 2016/6/16 0016.
 */
public interface BaseView {

	void showWaitDialog();

	void hideWaitDialog();

	void showToast(String msg);
}
