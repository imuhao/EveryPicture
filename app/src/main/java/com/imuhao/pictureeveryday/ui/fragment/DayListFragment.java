package com.imuhao.pictureeveryday.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.activity.MainActivity;
import com.imuhao.pictureeveryday.ui.adapter.DayListAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.Property;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.Provider;
import com.imuhao.pictureeveryday.ui.view.viewpageanimation.ViewPagerAnimator;
import com.imuhao.pictureeveryday.utils.Constant;
import com.imuhao.pictureeveryday.utils.ThemeUtils;

import static com.imuhao.pictureeveryday.utils.Constant.SHOW_DAY_SUM;

/**
 * @author Smile
 * @time 2017/5/8  下午2:42
 * @desc ${TODD}
 */
public class DayListFragment extends BaseFragment {

  @Bind(R.id.viewPager) public ViewPager viewPager;
  private DayListAdapter dayListAdapter;
  private ViewPagerAnimator<Integer> animator;

  public static DayListFragment newInstance() {
    return new DayListFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_daylist;
  }

  @Override protected void initView(View view) {
    ButterKnife.bind(this, view);
    dayListAdapter = new DayListAdapter(getChildFragmentManager());
    viewPager.setAdapter(dayListAdapter);
    viewPager.setOffscreenPageLimit(SHOW_DAY_SUM);

    final MainActivity activity = (MainActivity) getActivity();
    animator = ViewPagerAnimator.ofArgb(viewPager, new Provider<Integer>() {
      @Override public Integer get(int position) {
        return Constant.COLORS[position];
      }
    }, new Property<Integer>() {
      @Override public void set(Integer value) {
        ThemeUtils.setThemeColor(value);
        viewPager.setBackgroundColor(value);
        activity.setToolbarColor(value);
      }
    });
  }

  @Override public void onDestroy() {
    super.onDestroy();
    animator.destroy();
  }
}
