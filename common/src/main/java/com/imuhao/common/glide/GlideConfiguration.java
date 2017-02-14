package com.imuhao.common.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

/**
 * Created by dafan on 2016/10/18 0018.
 * <p>
 * http://www.cnblogs.com/whoislcj/p/5565012.html
 */

public class GlideConfiguration implements GlideModule {

	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		// Apply options to the builder here.
		builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);

		// 指定的是数据的缓存地址
		File cacheDir = context.getExternalCacheDir();
		// 最多可以缓存多少字节的数据
		int diskCacheSize = 1024 * 1024 * 1024;
		// 设置磁盘缓存大小
		builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "lryic", diskCacheSize));
	}

	@Override
	public void registerComponents(Context context, Glide glide) {
		// register ModelLoaders here.
	}
}
