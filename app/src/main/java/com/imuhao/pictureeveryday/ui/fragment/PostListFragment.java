package com.imuhao.pictureeveryday.ui.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.http.SmileApi;
import com.imuhao.pictureeveryday.ui.activity.WebActivity;
import com.imuhao.pictureeveryday.ui.adapter.PublicAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.ui.callback.DataCallBack;
import com.imuhao.pictureeveryday.ui.listener.OnRcvScrollListener;
import com.imuhao.pictureeveryday.ui.listener.OnItemClickListener;
import com.imuhao.pictureeveryday.ui.listener.OnScrollBottomListener;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @time 2016/6/24  11:15
 * @desc ${TODD}
 */
public class PostListFragment extends BaseFragment
    implements OnItemClickListener, OnScrollBottomListener {
  public static final String FLAG_KEY = "flag";
  private String flagFragment;
  private RecyclerView recyclerview;
  private PublicAdapter mAdapter;
  private int count = 10;
  private int index = 1;
  private List<EssayBean> publicDataResult;
  private boolean loadingData;
  private RelativeLayout loadingLl;
  private ImageView loadingImage;
  private DataCallBack mDataCallBack = new DataCallBack<List<EssayBean>>() {
    public void onSuccess(int what, List<EssayBean> essayBeen) {
      switch (what) {
        case 0x001://第一次初始化数据
          loadingData = false;
          loadingLl.setVisibility(View.GONE);
          if (publicDataResult == null) {
            publicDataResult = new ArrayList<>();
          }
          publicDataResult.addAll(essayBeen);
          initAdapter();
          index++;
          break;
      }
    }

    public void onError(int width, String error) {
      Log.d("imuhao", error);
    }
  };

  //初始化Adapter
  private void initAdapter() {
    //当请求完网络后,将所有的数据都添加到一个集合中,当加载更多的时候直接将adapter中的数据进行赋值
    //通过判断Adapter是否为空 来判断当前是第一次初始化数据还是更新数据
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
    AnimationDrawable ad = (AnimationDrawable) loadingImage.getBackground();
    ad.start();
    recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
    recyclerview.setLayoutManager(
        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerview.setItemAnimator(new DefaultItemAnimator());
    recyclerview.addOnScrollListener(new OnRcvScrollListener(mContext, this));
    //请求网络数据
    loadingLl.setVisibility(View.VISIBLE);
    SmileApi.getEssatData(flagFragment, 0x001, count, index, mDataCallBack);
    loadingData = true;
  }

  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getArguments();
    if (bundle != null) {
      flagFragment = bundle.getString(FLAG_KEY);
    }
  }

  public static PostListFragment newInstance(String flag) {
    PostListFragment fragment = new PostListFragment();
    Bundle bundle = new Bundle();
    bundle.putString(FLAG_KEY, flag);
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
      Log.d("imuhao", "开始加载数据");
      loadingLl.setVisibility(View.VISIBLE);
      SmileApi.getEssatData(flagFragment, 0x001, count, index, mDataCallBack);
      loadingData = true;
    }
  }
}
