package com.ylian.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 软键盘工具类
 * Created by guchenkai on 2015/11/26.
 */
public final class KeyboardUtils {

	/**
	 * 隐藏虚拟键盘
	 *
	 * @param v
	 */
	public static void hideKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
		}
	}

	/**
	 * 显示虚拟键盘
	 *
	 * @param v
	 */
	public static void showKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 通过定时器强制隐藏虚拟键盘
	 *
	 * @param v
	 */
	public static void timerHideKeyboard(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
				}
			}
		}, 300);
	}

	/**
	 * 输入法是否显示着
	 *
	 * @param context
	 * @return
	 */
	public static boolean isShowKeyBoard(Context context) {
		boolean bool = false;
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			bool = true;
		}
		return bool;
	}
}
