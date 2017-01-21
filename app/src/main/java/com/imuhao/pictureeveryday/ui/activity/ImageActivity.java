package com.imuhao.pictureeveryday.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.adapter.BigImageAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.utils.IntentValues;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile
 * @time 2016/6/22  19:31
 * @desc ${TODD}
 */
public class ImageActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.current_tv)
    TextView mCurrentTv;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    private List<String> mUrls;
    private int mPosition;
    private BigImageAdapter mAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        ButterKnife.bind(this);
        initIntent();
        initBar();
        initViewPager();
    }

    private void initBar() {
        initToolBar(mToolbar, "每日一图", R.drawable.ic_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        mUrls = getIntent().getStringArrayListExtra(IntentValues.BIG_IMG_LIST);
        mPosition = getIntent().getIntExtra(IntentValues.BIG_IMG_POSITION, 0);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    public void onPageSelected(int position) {
        mCurrentTv.setText(position + 1 + "/" + mUrls.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
