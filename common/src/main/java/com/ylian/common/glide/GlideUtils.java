package com.ylian.common.glide;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ylian.common.CommonConfig;
import com.ylian.common.R;

/**
 * Created by dafan on 16-8-28.
 */

public class GlideUtils {

	/**
	 * 清除图片磁盘缓存
	 */
	public static void clearImageDiskCache() {
		try {
			if (Looper.myLooper() == Looper.getMainLooper()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Glide.get(CommonConfig.instance().getAppCxt()).clearDiskCache();
					}
				});
			} else {
				Glide.get(CommonConfig.instance().getAppCxt()).clearDiskCache();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 清除图片内存缓存
	 */
	public static void clearImageMemoryCache() {
		try {
			if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
				Glide.get(CommonConfig.instance().getAppCxt()).clearMemory();
			}
		} catch (Exception e) {
		}
	}


	/**
	 * 清除图片所有缓存
	 */
	public static void clearImageAllCache() {
		clearImageDiskCache();
		clearImageMemoryCache();
	}

	/**
	 * @param context
	 * @param iv
	 * @param url
	 */
	public static void loadImage(Context context, ImageView iv, String url) {
		Glide.with(context)
				.load(url)
				.error(R.drawable.ic_placeholder)
				.placeholder(R.drawable.ic_placeholder)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.dontAnimate()
				.into(iv);
	}

	/**
	 * @param context
	 * @param iv
	 * @param url
	 */
	public static void loadImage2(Context context, ImageView iv, String url) {
		Glide.with(context)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.dontAnimate()
				.into(iv);
	}

	/**
	 * @param context
	 * @param iv
	 * @param url
	 */
	public static void loadCircleImage(Context context, ImageView iv, String url) {
		Glide.with(context)
				.load(url)
				.transform(new GlideCircleTransform(context))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.dontAnimate()
				.into(iv);
	}

	/**
	 * @param context
	 * @param iv
	 * @param url
	 */
	public static void loadRoundImage(Context context, ImageView iv, String url) {
		Glide.with(context)
				.load(url)
				.transform(new GlideRoundTransform(context))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.dontAnimate()
				.into(iv);
	}

	/**
	 * @param context
	 * @param iv
	 * @param url
	 * @param corners
	 */
	public static void loadRoundImage(Context context, ImageView iv, String url, int corners) {
		Glide.with(context)
				.load(url)
				.transform(new GlideRoundTransform(context, corners))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.dontAnimate()
				.into(iv);
	}

	/**
	 *
	 * @param context
	 * @param transformation
	 * @param iv
	 * @param url
	 */
	public static void loadRoundImage(Context context, Transformation transformation, ImageView iv, String url) {
		Glide.with(context)
				.load(url)
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.transform(transformation)
				.dontAnimate()
				.into(iv);
	}
}
