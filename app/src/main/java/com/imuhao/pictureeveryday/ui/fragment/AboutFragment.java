package com.imuhao.pictureeveryday.ui.fragment;

import android.view.View;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;

/**
 * @author Smile
 * @time 2016/6/22  13:32
 * @desc ${TODD}
 */
public class AboutFragment extends BaseFragment {
  private static AboutFragment instance;

  @Override protected int getLayoutId() {
    return R.layout.fragment_about;
  }

  public static AboutFragment newInstance() {
    if (instance == null) {
      instance = new AboutFragment();
    }
    return instance;
  }

  @Override protected void initView(View view) {

  }
}
