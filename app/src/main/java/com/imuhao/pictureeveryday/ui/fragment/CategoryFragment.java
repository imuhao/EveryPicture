package com.imuhao.pictureeveryday.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
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

  private ViewPager viewPager;
  private CategoryAdapter mAdapter;
  private TabLayout tabLayout;

  @Override protected int getLayoutId() {
    return R.layout.fragment_category;
  }

  public void initView(View view) {
    tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
    viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    mAdapter = new CategoryAdapter(getChildFragmentManager(), Contance.TITLES);
    viewPager.setAdapter(mAdapter);
    viewPager.setOffscreenPageLimit(Contance.TITLES.length);
    tabLayout.setupWithViewPager(viewPager);

    //PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
    //tabs.setViewPager(viewPager);
  }

  public static CategoryFragment getInstance() {
    return new CategoryFragment();
  }
}
