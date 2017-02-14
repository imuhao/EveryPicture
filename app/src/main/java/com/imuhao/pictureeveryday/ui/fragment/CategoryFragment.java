package com.imuhao.pictureeveryday.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import com.astuetz.PagerSlidingTabStrip;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.adapter.CategoryAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.utils.Contance;

/**
 * @author Smile
 * @time 2016/6/24  11:02
 * @desc 文章分类 ViewPager
 */
public class CategoryFragment extends BaseFragment {
  private static CategoryFragment instance;
  private ViewPager viewPager;
  private CategoryAdapter mAdapter;
  public final String[] TITLES = {
      Contance.FlagAndroid, Contance.FlagIOS, Contance.FlagVideo, Contance.FlagJS,
      Contance.FlagExpand, Contance.FlagRecommend, Contance.FlagAPP
  };

  @Override protected int getLayoutId() {
    return R.layout.fragment_category;
  }

  public void initView(View view) {
    viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    mAdapter = new CategoryAdapter(getChildFragmentManager(), TITLES);
    viewPager.setAdapter(mAdapter);
    PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
    tabs.setViewPager(viewPager);
    //设置缓存的数目
    viewPager.setOffscreenPageLimit(0);
    //设置页面之间的界限
    viewPager.setPageMargin(20);

    //tabs.setOnPageChangeListener(null);
  }

  public static CategoryFragment getInstance() {
    if (instance == null) {

      instance = new CategoryFragment();
    }
    return instance;
  }
}
