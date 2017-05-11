package com.imuhao.pictureeveryday.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.imuhao.pictureeveryday.utils.T;

/**
 * @author Smile
 * @time 2016/6/22  15:12
 * @desc ${TODD}
 */
public abstract class BaseFragment extends Fragment {

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(getLayoutId(), container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (getArguments() != null) handleData(getArguments());
    initView(view);
    initData();
  }

  protected abstract int getLayoutId();

  public void handleData(Bundle bundle) {

  }

  protected abstract void initView(View view);

  protected void initData() {
  }

  public void showToast(String message) {
    T.show(getActivity(), message);
  }
}
