package com.ylian.common.imagepicker;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.ylian.common.R;
import com.ylian.common.glide.GlideUtils;


/**
 * Created by smile on 16-9-19.
 */
public class ImagePickerLoad implements ImageLoader {
	@Override
	public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
		Glide.
				with(activity).
				load(path).
				error(R.mipmap.default_image).
				override(width, height).
				diskCacheStrategy(DiskCacheStrategy.ALL).
				into(imageView);
	}

	@Override
	public void clearMemoryCache() {
		GlideUtils.clearImageAllCache();
	}
}
