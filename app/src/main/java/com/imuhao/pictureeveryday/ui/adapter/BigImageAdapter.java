package com.imuhao.pictureeveryday.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imuhao.pictureeveryday.utils.DensityUtil;
import com.imuhao.pictureeveryday.ui.view.PinchImageView;

import java.util.List;

/**
 * @author Smile
 * @time 2016/6/22  19:49
 * @desc ${TODD}
 */
public class BigImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mUrls;

    public BigImageAdapter(Context context, List<String> data) {
        mUrls = data;
        mContext = context;
    }

    public int getCount() {
        return mUrls.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        PinchImageView image = new PinchImageView(mContext);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();
        params.width = DensityUtil.getWidth(mContext);
        params.height = DensityUtil.getHeight(mContext);
        image.setLayoutParams(params);
        Glide.with(mContext).load(mUrls.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.2f).into(image);
        container.addView(image);
        return image;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
