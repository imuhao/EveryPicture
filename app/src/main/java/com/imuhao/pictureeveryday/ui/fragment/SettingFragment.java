package com.imuhao.pictureeveryday.ui.fragment;

import android.view.View;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;

/**
 * @author Smile
 * @time 2017/2/14  下午2:13
 * @desc ${TODD}
 */
public class SettingFragment extends BaseFragment {
  private static SettingFragment instance;

  public static SettingFragment newInstance() {
    if (instance == null) {
      instance = new SettingFragment();
    }
    return instance;
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_setting;
  }

  @Override protected void initView(View view) {

  }
}
