package com.imuhao.pictureeveryday.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.Fragments;
import com.imuhao.pictureeveryday.ui.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.ui.fragment.DayListFragment;
import com.imuhao.pictureeveryday.ui.fragment.PictureFragment;
import com.imuhao.pictureeveryday.ui.fragment.SettingFragment;
import java.util.List;

/**
 * @author Smile
 * @time 2017/5/13  上午9:14
 * @desc ${TODD}
 */
public class FragmentUtil {

  private FragmentManager fragmentManager;

  private PictureFragment mPictureFragment;
  private CategoryFragment mCategoryFragment;
  private SettingFragment mSettingFragment;
  private DayListFragment mDayListFragment;

  public FragmentUtil(FragmentManager fragmentManager) {
    this.fragmentManager = fragmentManager;
  }

  /**
   * 显示指定 fragment
   */
  public void show(@Fragments String tab) {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    //图片


    if (Fragments.PICTURE.equals(tab)) {
      if (mPictureFragment == null) {
        mPictureFragment = PictureFragment.newInstance();
        transaction.add(R.id.fl_content, mPictureFragment);
      } else {
        transaction.show(mPictureFragment);
      }
    }
    //分类
    else if (Fragments.CATEGORY.equals(tab)) {
      if (mCategoryFragment == null) {
        mCategoryFragment = CategoryFragment.newInstance();
        transaction.add(R.id.fl_content, mCategoryFragment);
      } else {
        transaction.show(mCategoryFragment);
      }
    }
    //设置
    else if (Fragments.SETTING.equals(tab)) {
      if (mSettingFragment == null) {
        mSettingFragment = SettingFragment.newInstance();
        transaction.add(R.id.fl_content, mSettingFragment);
      } else {
        transaction.show(mSettingFragment);
      }
    }
    //今日
    else if (Fragments.TODAY.equals(tab)) {
      if (mDayListFragment == null) {
        mDayListFragment = DayListFragment.newInstance();
        transaction.add(R.id.fl_content, mDayListFragment);
      } else {
        transaction.show(mDayListFragment);
      }
    }
    transaction.commit();
  }

  /**
   * 隐藏所有 fragment
   */
  @SuppressWarnings("all") public void hide() {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    List<Fragment> fragments = fragmentManager.getFragments();
    if (fragments == null || fragments.isEmpty()) return;
    for (Fragment fragment : fragments) {
      transaction.hide(fragment);
    }
    transaction.commit();
  }
}

