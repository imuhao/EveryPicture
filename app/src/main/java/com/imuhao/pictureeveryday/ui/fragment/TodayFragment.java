package com.imuhao.pictureeveryday.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

  public static TodayFragment newInstance() {
    return new TodayFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_today;
  }

  @Override protected void initView(View view) {
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
            todayAdapter.setData(gankBean);
            Log.d("smile", gankBean.category.toString());
          }

          @Override public void onError(String error) {
            swipeRefreshLayout.setRefreshing(false);
            Log.d("smile", error);
          }
        });
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
