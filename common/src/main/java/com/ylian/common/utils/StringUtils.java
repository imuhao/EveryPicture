package com.ylian.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * string处理
 * Created by guchenkai on 2015/11/24.
 */
public final class StringUtils {

	static String regEx = "[\\u4e00-\\u9fa5]"; // unicode编码，判断是否为汉字

	/**
	 * 判断两个字符串是否相同
	 *
	 * @param target1 目标1
	 * @param target2 目标2
	 * @return 是否相同
	 */
	public static boolean equals(String target1, String target2) {
		return TextUtils.equals(target1, target2);
	}

	/**
	 * 获得UUID
	 *
	 * @return UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 验证邮箱
	 *
	 * @param email 电子邮箱地址
	 * @return 是否匹配
	 */
	public static boolean checkEmailFormat(String email) {
		return checkRegex(email,
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
	}

	/**
	 * 匹配正则表达式
	 *
	 * @param text  被匹配文本
	 * @param regex 正则表达式
	 * @return 是否匹配
	 */
	public static boolean checkRegex(final String text, final String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * 验证手机号码
	 *
	 * @param mobile 手机号码
	 * @return 是否匹配
	 */
	public static boolean checkMobileFormat(String mobile) {
		return checkRegex(mobile, "[1][34578]\\d{9}");
	}

	/**
	 * 验证密码(6到16位)
	 *
	 * @param password 密码文本
	 * @return 是否匹配
	 */
	public static boolean checkPasswordFormat(String password) {
		return checkRegex(password, "[A-Z0-9a-z]{6,16}");
	}

	/**
	 * 查字符串是否为数字型字符串
	 *
	 * @param target 目标
	 * @return 是否匹配
	 */
	public static boolean isNumeric(String target) {
		if (StringUtils.isEmpty(target)) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern != null ? pattern.matcher(target) : null;
		return !(isNum != null && !isNum.matches());
	}

	/**
	 * 字符串判空
	 *
	 * @param target 目标
	 * @return 是否为空
	 */
	public static boolean isEmpty(String target) {
		return TextUtils.isEmpty(target) || TextUtils.equals("null", target);
	}

	public static int getChineseCount(String str) {
		int count = 0;
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}

	public static String getCutStringByByteCount(String str, int count, String endString) {
		String cutStringByByteCount = getCutStringByByteCount(str, count);
		if (cutStringByByteCount.equals(str))
			return cutStringByByteCount;
		else return cutStringByByteCount + endString;
	}

	public static String getCutStringByByteCount(String str, int count) {
		String newStr = "";
		int addCount = 0;
		for (int i = 0; i < str.length(); i++) {
			addCount++;
			String substring = str.substring(i, i + 1);
			if (isContainChinese(substring))
				addCount++;
			if (addCount > count)
				break;
			newStr = newStr + substring;
		}
		if (newStr.length() <= str.length())
			return newStr;
		else
			return str;
	}

	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * length表示生成字符串的长度
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 复制文字
	 *
	 * @param context
	 * @param string  被复制的文字内容
	 */
	public static void copyText(Context context, String string) {
		try {
			ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
			ClipData mClipData = ClipData.newPlainText("Label", string);
			cm.setPrimaryClip(mClipData);
			ToastUtils.showToastShort(context, "复制成功");
		} catch (Exception e) {
			ToastUtils.showToastShort(context, "Sorry, 复制失败了...");
		}
	}
}
