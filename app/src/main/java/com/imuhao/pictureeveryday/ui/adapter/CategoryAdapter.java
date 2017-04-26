package com.imuhao.pictureeveryday.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.imuhao.pictureeveryday.ui.fragment.PostListFragment;

/**
 * @author Smile
 * @time 2016/6/24  11:13
 * @desc ${TODD}
 */
public class CategoryAdapter extends FragmentStatePagerAdapter {

  private String[] title;

  public CategoryAdapter(FragmentManager fm, String[] title) {
    super(fm);
    this.title = title;
  }

  @Override public Fragment getItem(int position) {
    return PostListFragment.newInstance(title[position]);
  }

  @Override public int getCount() {
    return title.length;
  }

  @Override public CharSequence getPageTitle(int position) {
    return title[position];
  }
}
