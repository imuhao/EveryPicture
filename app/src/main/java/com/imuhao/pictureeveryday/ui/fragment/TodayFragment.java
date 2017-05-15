package com.imuhao.pictureeveryday.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.GankBean;
import com.imuhao.pictureeveryday.http.Retrofits;
import com.imuhao.pictureeveryday.http.SmileCallback;
import com.imuhao.pictureeveryday.ui.adapter.TodayAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseLazyFragment;
import com.imuhao.pictureeveryday.utils.ThemeUtils;
import java.util.Calendar;

/**
 * @author Smile
 * @time 2017/5/3  下午2:49
 * @desc 今日干货
 */
public class TodayFragment extends BaseLazyFragment
    implements SwipeRefreshLayout.OnRefreshListener {

  private int mYear, mMonth, mDay;

  private SwipeRefreshLayout swipeRefreshLayout;
  private RecyclerView recyclerView;
  private TodayAdapter todayAdapter;
  private ViewStub vsEmpty;
  private View vsView;

  public static TodayFragment newInstance(int year, int month, int day) {
    TodayFragment todayFragment = new TodayFragment();
    Bundle bundle = new Bundle();
    bundle.putInt("year", year);
    bundle.putInt("month", month);
    bundle.putInt("day", day);
    todayFragment.setArguments(bundle);
    return todayFragment;
  }

  @Override public void handleData(Bundle bundle) {
    mYear = bundle.getInt("year");
    mMonth = bundle.getInt("month");
    mDay = bundle.getInt("day");
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_today;
  }

  @Override protected void initView(View view) {
    vsEmpty = (ViewStub) view.findViewById(R.id.vs_empty);
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeColors(ThemeUtils.getThemeColor());
    todayAdapter = new TodayAdapter();
    recyclerView.setAdapter(todayAdapter);
  }

  private void loadData() {
    swipeRefreshLayout.setRefreshing(true);
    Retrofits.getApiService()
        .getGankData(mYear, mMonth, mDay)
        .enqueue(new SmileCallback<GankBean>() {
          @Override public void onSuccess(GankBean gankBean) {
            swipeRefreshFinish();
            if (gankBean.category.size() != 0) {
              todayAdapter.setData(gankBean);
              if (vsEmpty != null) vsEmpty.setVisibility(View.GONE);
            } else {
              showEmptyView();
            }
          }

          @Override public void onError(String error) {
            swipeRefreshFinish();
            Snackbar.make(recyclerView, "加载数据失败!", Snackbar.LENGTH_SHORT).show();
          }
        });
  }

  //让子弹飞一会~
  private void swipeRefreshFinish() {
    swipeRefreshLayout.postDelayed(new Runnable() {
      @Override public void run() {
        swipeRefreshLayout.setRefreshing(false);
      }
    }, 500);
  }

  /**
   * 显示空数据界面
   */
  private void showEmptyView() {
    if (vsView == null) {
      vsView = vsEmpty.inflate();
    }
  }

  //计算当前的时间
  private void calculateTime() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, 0);
    mYear = calendar.get(Calendar.YEAR);
    mMonth = calendar.get(Calendar.MONTH) + 1;
    mDay = calendar.get(Calendar.DAY_OF_MONTH);
  }

  @Override public void onRefresh() {
    loadData();
  }

  protected void onShowToScreen(boolean isFirstLoad) {
    if (isFirstLoad) loadData();
  }
}
