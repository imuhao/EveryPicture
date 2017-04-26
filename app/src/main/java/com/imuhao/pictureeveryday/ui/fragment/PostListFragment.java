package com.imuhao.pictureeveryday.ui.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.http.SmileApi;
import com.imuhao.pictureeveryday.ui.activity.WebActivity;
import com.imuhao.pictureeveryday.ui.adapter.PublicAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseLazyFragment;
import com.imuhao.pictureeveryday.ui.callback.DataCallBack;
import com.imuhao.pictureeveryday.ui.listener.OnItemClickListener;
import com.imuhao.pictureeveryday.ui.listener.OnRcvScrollListener;
import com.imuhao.pictureeveryday.ui.listener.OnScrollBottomListener;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @time 2016/6/24  11:15
 * @desc ${TODD}
 */
public class PostListFragment extends BaseLazyFragment
    implements OnItemClickListener, OnScrollBottomListener, SwipeRefreshLayout.OnRefreshListener {

  public static final String TITLE = "title";
  private static final int LOAD_DATA = 0x001;

  private int index = 1;
  private static final int COUNT = 10;
  private boolean loadingData;
  private String fragmentTitle;

  private PublicAdapter mAdapter;
  private RecyclerView recyclerview;
  private List<EssayBean> publicDataResult = new ArrayList<>();
  private RelativeLayout loadingLl;
  private ImageView loadingImage;
  private SwipeRefreshLayout swipeRefreshLayout;

  private DataCallBack mDataCallBack = new DataCallBack<List<EssayBean>>() {
    public void onSuccess(int what, List<EssayBean> essayBeen) {
      switch (what) {
        case LOAD_DATA://获取到数据
          loadingData = false;
          loadingLl.setVisibility(View.GONE);
          publicDataResult.addAll(essayBeen);
          index++;
          initAdapter();
          swipeRefreshLayout.setRefreshing(false);
          break;
      }
    }

    public void onError(int width, String error) {
      swipeRefreshLayout.setRefreshing(false);
      Toast.makeText(getActivity(), "数据加载失败!", Toast.LENGTH_SHORT).show();
    }
  };

  private void initAdapter() {
    if (mAdapter == null) {
      mAdapter = new PublicAdapter(getActivity(), publicDataResult);
      mAdapter.setListener(this);
      recyclerview.setAdapter(mAdapter);
    } else {
      //更新数据
      mAdapter.upListData(publicDataResult);
    }
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_public_category;
  }

  public void initView(View view) {
    loadingLl = (RelativeLayout) view.findViewById(R.id.loading_ll);
    loadingImage = (ImageView) view.findViewById(R.id.loading_image);
    ((AnimationDrawable) loadingImage.getBackground()).start();
    recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
    swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.mainColoe));
    recyclerview.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerview.setItemAnimator(new DefaultItemAnimator());
    recyclerview.addOnScrollListener(new OnRcvScrollListener(mContext, this));
  }

  private void loadData(boolean isClear) {
    if (isClear) {
      index = 1;
      publicDataResult.clear();
    }
    loadingLl.setVisibility(View.VISIBLE);
    SmileApi.getEssatData(fragmentTitle, LOAD_DATA, COUNT, index, mDataCallBack);
    loadingData = true;
  }

  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      fragmentTitle = bundle.getString(TITLE);
    }
  }

  public static PostListFragment newInstance(String flag) {
    PostListFragment fragment = new PostListFragment();
    Bundle bundle = new Bundle();
    bundle.putString(TITLE, flag);
    fragment.setArguments(bundle);
    return fragment;
  }

  public void onItemClick(View view) {
    EssayBean bean = (EssayBean) view.getTag();
    IntentUtils.startToWebActivity(mContext, WebActivity.class, bean);
  }

  //滑动到底部
  public void onToBottom() {
    if (!loadingData) {
      loadData(false);
    }
  }

  @Override protected void onShowToScreen(boolean isFirstLoad) {
    if (isFirstLoad) loadData(true);
  }

  @Override public void onRefresh() {
    loadData(true);
  }
}
