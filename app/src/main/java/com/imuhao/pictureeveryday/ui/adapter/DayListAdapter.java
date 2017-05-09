package com.imuhao.pictureeveryday.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.imuhao.pictureeveryday.ui.fragment.TodayFragment;
import java.util.Calendar;

import static com.imuhao.pictureeveryday.utils.Constant.SHOW_DAY_SUM;

/**
 * @author Smile
 * @time 2017/5/8  下午2:45
 * @desc ${TODD}
 */
public class DayListAdapter extends FragmentPagerAdapter {

  public DayListAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, -position);
    return TodayFragment.newInstance(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH));
  }

  @Override public int getCount() {
    return SHOW_DAY_SUM;
  }
}
