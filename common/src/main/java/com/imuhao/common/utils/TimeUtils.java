package com.imuhao.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dafan on 2016/10/17 0017.
 */

public class TimeUtils {
	public static String convertTime(int second) {
		int hour = second / 60 / 60;
		int minute = (second / 60) % 60;

		if (hour == 0) {
			if (String.valueOf(minute).length() == 1)
				return "00:0" + minute;
			else
				return "00:" + minute;
		}

		// hour不为0
		else {
			// hour是一位
			if (String.valueOf(hour).length() == 1) {
				if (String.valueOf(minute).length() == 1)
					return "0" + hour + ":0" + minute;
				else
					return "0" + hour + ":" + minute;
			}
			// hour是两位
			else {
				if (String.valueOf(minute).length() == 1)
					return hour + ":0" + minute;
				else
					return hour + ":" + minute;
			}
		}
	}

	public static String convertTime2(int second) {
		int hour = second / 60 / 60;
		int minute = (second / 60) % 60;

		if (hour == 0) {
			return minute + "分钟";
		}
		return hour + "小时" + minute + "分钟";
	}

	/**
	 * 获取日期格式为yyyy-MM-dd的时间戳
	 *
	 * @param day yyyy-MM-dd
	 * @return
	 */
	public static int getDayTime(String day) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(day);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return (int) (cal.getTimeInMillis() / 1000);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取日期格式为yyyy-MM-dd的时间戳
	 *
	 * @param time yyyy-MM-dd HH:mm
	 * @return
	 */
	public static int getTime(String time) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = df.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return (int) (cal.getTimeInMillis() / 1000);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取当前日期，格式为yyyy-MM-dd
	 *
	 * @return yyyy-MM-dd
	 */
	public static String getDay() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(new Date());
	}

	/**
	 * 获取此时此刻距离当日开始走过的秒数
	 *
	 * @return
	 */
	public static int getDayStartSecond() {
		long now = System.currentTimeMillis() / 1000;
		long day = getDayTime(getDay());
		int diff = (int) (now - day);
		return diff;
	}

	/**
	 * 当前秒级时间戳
	 *
	 * @return
	 */
	public static int currentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 时间戳转换成日期格式字符串
	 *
	 * @param seconds 精确到秒的字符串
	 * @param format
	 * @return
	 */
	public static String timeStamp2Date(String seconds, String format) {
		if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
			return "";
		}
		if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds + "000")));
	}
}
