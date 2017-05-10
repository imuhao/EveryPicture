package com.imuhao.pictureeveryday.ui.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.ItemBean;
import com.imuhao.pictureeveryday.ui.activity.ReviewPictureActivity;
import com.imuhao.pictureeveryday.ui.adapter.ImageListAdapter;
import com.imuhao.pictureeveryday.ui.base.mvp.BaseMvpFragment;
import com.imuhao.pictureeveryday.ui.listener.OnRcvScrollListener;
import com.imuhao.pictureeveryday.ui.listener.OnScrollBottomListener;
import com.imuhao.pictureeveryday.ui.mvp.ImageListContract;
import com.imuhao.pictureeveryday.ui.mvp.ImageListPresenter;
import java.util.List;

/**
 * @author Smile
 * @time 2016/6/22  13:23
 * @desc 图片页面
 */
public class PictureFragment extends BaseMvpFragment<ImageListPresenter>
    implements View.OnClickListener, ImageListContract.View, OnScrollBottomListener,
    SwipeRefreshLayout.OnRefreshListener, ImageListAdapter.OnItemClickListener {

  private static final int INIT_DATA = 0X001;
  private static final int LOAD_ERROR = 0x002;

  private int index = 1;

  private List<ItemBean> mData;
  private ImageListAdapter mAdapter;
  private RecyclerView mRecyclerView;
  private SwipeRefreshLayout swipeRefreshLayout;
  private ImageView loadingImg;
  private RelativeLayout loadingLl;
  private Button mBtnRetryLoad;
  private boolean isLoadMore;

  public static PictureFragment newInstance() {
    return new PictureFragment();
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_navfuli;
  }

  public void onDestroy() {
    super.onDestroy();
    index = 0;
  }

  public void initData() {
  }

  public void initView(View view) {
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
    swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
    swipeRefreshLayout.setOnRefreshListener(this);
    loadingImg = (ImageView) view.findViewById(R.id.loading_image);
    loadingLl = (RelativeLayout) view.findViewById(R.id.loading_ll);
    mBtnRetryLoad = (Button) view.findViewById(R.id.btn_retry_load);
    mBtnRetryLoad.setOnClickListener(this);
    ((AnimationDrawable) loadingImg.getBackground()).start();

    mRecyclerView = (RecyclerView) view.findViewById(R.id.nav_pic_rv);
    mRecyclerView.setLayoutManager(
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mAdapter = new ImageListAdapter(getActivity());
    mAdapter.setOnItemClickListener(this);
    mRecyclerView.setAdapter(mAdapter);

    mRecyclerView.addOnScrollListener(new OnRcvScrollListener(getActivity(), this));

    presenter.loadAllImage(index);
  }

  private void setRecyclerData(List<ItemBean> data) {
    if (isLoadMore) {
      mAdapter.addData(data);
      mAdapter.notifyDataSetChanged();
    } else {
      mData = data;
      mAdapter.setData(data);
      mAdapter.notifyDataSetChanged();
    }
  }

  @Override public void onClick(View view) {
    //加载失败重试
    if (mBtnRetryLoad == view) {
      index = 1;
      presenter.loadAllImage(index);
    }
  }

  @Override protected void onShowToScreen(boolean isFirstLoad) {

  }

  @Override public void showAllImage(List<ItemBean> results) {
    setRecyclerData(results);
    swipeRefreshLayout.setRefreshing(false);
  }

  @Override public void loadImageError(String error) {
    swipeRefreshLayout.setRefreshing(false);
  }

  //滑动到底部,加载更多
  @Override public void onToBottom() {
    isLoadMore = true;
    if (mData != null && !mData.isEmpty()) {
      index++;
      presenter.loadAllImage(index);
    }
  }

  @Override public void onRefresh() {
    index = 1;
    presenter.loadAllImage(index);
  }

  @Override public void onItemClick(View view, List<ItemBean> mData, int position) {
    ReviewPictureActivity.lunch(getActivity(), view, mData, position);
  }
}
