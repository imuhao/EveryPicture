package com.imuhao.pictureeveryday.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
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

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.current_tv) TextView mCurrentTv;
  @Bind(R.id.viewPager) ViewPager mViewPager;

  private List<String> mUrls;
  private int mPosition;
  private BigImageAdapter mAdapter;

  public static void start(Context context, List<ItemBean> mData, int position) {
    ArrayList<String> urls = new ArrayList<>();
    Intent intent = new Intent(context, ReviewPictureActivity.class);
    intent.putExtra(Contance.BIG_IMG_POSITION, position);

    for (ItemBean itemBean : mData) {
      urls.add(itemBean.getUrl());
    }
    intent.putStringArrayListExtra(Contance.BIG_IMG_LIST, urls);
    context.startActivity(intent);
  }

  @Override protected int getLayoutId() {
    return R.layout.activity_review_picture;
  }

  @Override protected void initView() {
    initIntent();
    initBar();
    initViewPager();
  }

  private void initBar() {
    initToolBar(mToolbar, "每日一图", R.drawable.ic_back);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void initViewPager() {
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
