package com.imuhao.pictureeveryday.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.activity.MainActivity;
import com.imuhao.pictureeveryday.ui.adapter.CategoryAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.Property;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.Provider;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.ViewPagerAnimator;
import com.imuhao.pictureeveryday.utils.Contance;
import com.imuhao.pictureeveryday.utils.ThemeUtils;

/**
 * @author Smile
 * @time 2016/6/24  11:02
 * @desc 文章分类 ViewPager
 */
public class CategoryFragment extends BaseFragment {

  private ViewPager viewPager;
  private CategoryAdapter mAdapter;
  private TabLayout tabLayout;
  private ViewPagerAnimator<Integer> animator;
  private MainActivity activity;

  public static CategoryFragment getInstance() {
    return new CategoryFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_category;
  }

  public void initView(View view) {
    tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
    viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    mAdapter = new CategoryAdapter(getChildFragmentManager(), Contance.TITLES, Contance.COLORS);
    viewPager.setAdapter(mAdapter);
    viewPager.setOffscreenPageLimit(Contance.TITLES.length);
    tabLayout.setupWithViewPager(viewPager);

    activity = (MainActivity) getActivity();

    Property<Integer> property = new Property<Integer>() {
      @Override public void set(Integer value) {
        ThemeUtils.setThemeColor(value);
        viewPager.setBackgroundColor(value);
        tabLayout.setBackgroundColor(value);
        activity.setToolbarColor(value);
      }
    };

    Provider<Integer> provider = new Provider<Integer>() {
      @Override public Integer get(int position) {
        return mAdapter.getColor(position);
      }
    };
    animator = ViewPagerAnimator.ofArgb(viewPager, provider, property);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    animator.destroy();
    activity.setToolbarColor(getResources().getColor(R.color.colorPrimary));
  }
}
