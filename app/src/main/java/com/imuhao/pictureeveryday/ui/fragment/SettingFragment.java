package com.imuhao.pictureeveryday.ui.fragment;

import android.view.View;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.utils.T;
import com.leon.lib.settingview.LSettingItem;

/**
 * @author Smile
 * @time 2017/2/14  下午2:13
 * @desc ${TODD}
 */
public class SettingFragment extends BaseFragment {
  private static SettingFragment instance;
  private LSettingItem lSettingItem;

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
    lSettingItem = (LSettingItem) view.findViewById(R.id.item_theme);
    lSettingItem.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
      @Override public void click() {
        T.show(getActivity(), "待添加..", "确定", new View.OnClickListener() {
          @Override public void onClick(View v) {

          }
        });
      }
    });
  }
}
