package com.imuhao.pictureeveryday.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import com.bumptech.glide.Glide;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.ItemBean;
import com.imuhao.pictureeveryday.ui.adapter.BigImageAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.utils.Contance;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @time 2016/6/22  19:31
 * @desc ${TODD} 图片预览
 */
public class ReviewPictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

  private static String TRANSIT_PIC = "transit";

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.current_tv) TextView mCurrentTv;
  @Bind(R.id.viewPager) ViewPager mViewPager;
  @Bind(R.id.image) ImageView image;
  private List<String> mUrls;
  private int mPosition;
  private BigImageAdapter mAdapter;

  public static void lunch(Activity activity, View view, List<ItemBean> mData, int position) {

    ArrayList<String> urls = new ArrayList<>();
    Intent intent = new Intent(activity, ReviewPictureActivity.class);
    intent.putExtra(Contance.BIG_IMG_POSITION, position);

    for (ItemBean itemBean : mData) {
      urls.add(itemBean.getUrl());
    }
    intent.putStringArrayListExtra(Contance.BIG_IMG_LIST, urls);

    ActivityOptionsCompat optionsCompat =
        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view,
            ReviewPictureActivity.TRANSIT_PIC);

    try {
      ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      activity.startActivity(intent);
    }
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_review_picture;
  }

  @Override protected void initView() {
    ViewCompat.setTransitionName(image, TRANSIT_PIC);
    initIntent();
    initBar();
    initViewPager();
  }

  private void initBar() {
    initToolBar(mToolbar, "预览", R.drawable.ic_back);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initViewPager() {
    Glide.with(this).load(mUrls.get(mPosition)).into(image);
    mCurrentTv.setText(mPosition + 1 + "/" + mUrls.size());
    mAdapter = new BigImageAdapter(this, mUrls);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setCurrentItem(mPosition);
    mViewPager.addOnPageChangeListener(this);
  }

  private void initIntent() {
    mUrls = getIntent().getStringArrayListExtra(Contance.BIG_IMG_LIST);
    mPosition = getIntent().getIntExtra(Contance.BIG_IMG_POSITION, 0);
  }

  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  public void onPageSelected(int position) {
    mCurrentTv.setText(position + 1 + "/" + mUrls.size());
  }

  @Override public void onPageScrollStateChanged(int state) {

  }
}
