package com.ylian.common.oss;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ylian.common.utils.StringUtils;

/**
 * Created by dafan on 2016/8/22 0022.
 */
public class OssConfigUtils {
	/**
	 * 保存OssConfig配置信息到缓存中
	 *
	 * @param ossConfig
	 */
	public static void saveOssConfig(Context context, OssConfig ossConfig) {
		String json = new Gson().toJson(ossConfig);
		SharedPreferences pref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		pref.edit().putString("oinfo", json).apply();
	}

	// 从缓存中获取OssConfig配置信息
	public static OssConfig getOssConfig(Context context) {
		SharedPreferences pref = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String json = pref.getString("oinfo", "");
		if (StringUtils.isEmpty(json))
			return null;
		try {
			OssConfig ossConfig = new Gson().fromJson(json, OssConfig.class);
			if (isEffectiveOssConfig(ossConfig))
				return ossConfig;
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 配置OssConfig信息是否过期
	 *
	 * @return
	 */
	protected static boolean isEffectiveOssConfig(OssConfig ossConfig) {
		long saveTime = ossConfig.getTime();
		if (saveTime == 0)// 0说明之前没有获取过配置信息
			return false;

		long nowTime = System.currentTimeMillis();
		long diffTime = nowTime - saveTime;
		return diffTime < 1000 * 60 * 45;
	}
}
