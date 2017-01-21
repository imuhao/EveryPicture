package com.ylian.common.utils;

/**
 * Created by dafan on 2016/8/19 0019.
 */
public class ThreadUtils {
	/**
	 * 线程休眠
	 *
	 * @param time
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}
}
