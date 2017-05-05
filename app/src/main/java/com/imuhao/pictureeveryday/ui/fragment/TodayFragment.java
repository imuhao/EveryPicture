package com.imuhao.pictureeveryday.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.GankBean;
import com.imuhao.pictureeveryday.http.Retrofits;
import com.imuhao.pictureeveryday.http.SmileCallback;
import com.imuhao.pictureeveryday.ui.adapter.TodayAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Smile
 * @time 2017/5/3  下午2:49
 * @desc 今日干货
 */
public class TodayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

  int mYear, mMonth, mDay;

  private SwipeRefreshLayout swipeRefreshLayout;
  private RecyclerView recyclerView;
  private TodayAdapter todayAdapter;
  private ViewStub vsEmpty;
  private View vsView;

  public static TodayFragment newInstance() {
    return new TodayFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_today;
  }

  @Override protected void initView(View view) {
    vsEmpty = (ViewStub) view.findViewById(R.id.vs_empty);
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    todayAdapter = new TodayAdapter();
    recyclerView.setAdapter(todayAdapter);
    calculateTime();
    loadData();
  }

  private void loadData() {
    swipeRefreshLayout.setRefreshing(true);
    Retrofits.getApiService()
        .getGankData(mYear, mMonth, mDay)
        .enqueue(new SmileCallback<GankBean>() {
          @Override public void onSuccess(GankBean gankBean) {
            swipeRefreshLayout.setRefreshing(false);
            if (gankBean.category.size() != 0) {
              todayAdapter.setData(gankBean);
            } else {
              showEmptyView();
            }
          }

          @Override public void onError(String error) {
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(recyclerView, "加载数据失败!", Snackbar.LENGTH_SHORT).show();
          }
        });
  }

  /**
   * 显示空数据界面
   */
  private void showEmptyView() {
    if (vsView == null) {
      vsView = vsEmpty.inflate();
    }
  }

  private void calculateTime() {
    Date today = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(today);
    mYear = calendar.get(Calendar.YEAR);
    mMonth = calendar.get(Calendar.MONTH) + 1;
    mDay = calendar.get(Calendar.DAY_OF_MONTH) + 1;
  }

  @Override public void onRefresh() {
    loadData();
  }
}
