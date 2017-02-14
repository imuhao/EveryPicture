package com.imuhao.pictureeveryday.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Smile
 * @time 2016/6/22  15:12
 * @desc ${TODD}
 */
public abstract class BaseFragment extends Fragment {
  protected Context mContext;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mContext = getActivity();
    View view = inflater.inflate(getLayoutId(), container, false);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView(view);
    initData();
  }

  protected abstract int getLayoutId();

  protected abstract void initView(View view);

  protected void initData() {
  }
}
