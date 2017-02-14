package com.imuhao.pictureeveryday.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.NavFuliBean;
import com.imuhao.pictureeveryday.ui.activity.ReviewPictureActivity;
import com.imuhao.pictureeveryday.ui.adapter.NavFuliAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.ui.listener.OnRcvScrollListener;
import com.imuhao.pictureeveryday.ui.listener.onScrollBottomListener;
import com.imuhao.pictureeveryday.utils.Contance;
import com.imuhao.pictureeveryday.utils.HttpRequest;
import com.imuhao.pictureeveryday.utils.HttpUtils;
import com.imuhao.pictureeveryday.utils.IntentValues;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Smile
 * @time 2016/6/22  13:23
 * @desc 图片页面
 */
public class PictureFragment extends BaseFragment {
  private RecyclerView mRecyclerView;
  private static PictureFragment instance;
  private NavFuliAdapter mAdapter;
  private static final int INIT_DATA = 0X001;
  private int PageSize = 20;
  private int Index = 1;
  private Handler mHandler = new Handler();
  private List<NavFuliBean> mData;

  private ArrayList<String> mUrlList;
  private ImageView loadingImg;
  private RelativeLayout loadingLl;

  private boolean isLoadMore;
  private boolean isLoading;

  @Override protected int getLayoutId() {
    return R.layout.fragment_navfuli;
  }

  public void onDestroy() {
    super.onDestroy();
    Index = 1;
  }

  public void initData() {
    isLoading = true;
    loadingLl.setVisibility(View.VISIBLE);
    String url = Contance.getFuliUrl(PageSize, Index);
    Index++;
    HttpUtils.getInstance().doGet(url, new HttpRequest() {
      public void onFailure(String error) {
      }

      public void onResponse(String result) {
        try {
          Gson gson = new Gson();
          JSONObject jsonObject = new JSONObject(result);
          String jsonStr = jsonObject.getString("results");
          List<NavFuliBean> beans = gson.fromJson(jsonStr, new TypeToken<List<NavFuliBean>>() {
          }.getType());
          mHandler.obtainMessage(INIT_DATA, beans).sendToTarget();
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }

  public void initView(View view) {
    loadingImg = (ImageView) view.findViewById(R.id.loading_image);
    loadingLl = (RelativeLayout) view.findViewById(R.id.loading_ll);
    AnimationDrawable ad = (AnimationDrawable) loadingImg.getBackground();
    ad.start();

    mUrlList = new ArrayList<>();
    mRecyclerView = (RecyclerView) view.findViewById(R.id.nav_pic_rv);
    mRecyclerView.setLayoutManager(
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    mAdapter = new NavFuliAdapter(getActivity());
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addOnScrollListener(
        new OnRcvScrollListener(getActivity(), new onScrollBottomListener() {

          public void onToBottom() {
            //滑动到底部,加载更多
            isLoadMore = true;
            if (mData != null && !mData.isEmpty() && !isLoading) {
              initData();
            }
          }
        }));

    mAdapter.setListener(new NavFuliAdapter.onItemClickListener() {
      @Override public void onItemClick(View view, int position) {
        mUrlList.clear();
        //点击之后取出所有的url封装成一个集合传给展示的页面
        for (int i = 0; i < mData.size(); i++) {
          mUrlList.add(mData.get(i).getUrl());
        }

        Intent intent = new Intent(mContext, ReviewPictureActivity.class);
        intent.putExtra(IntentValues.BIG_IMG_POSITION, position);
        intent.putStringArrayListExtra(IntentValues.BIG_IMG_LIST, mUrlList);
        mContext.startActivity(intent);
      }
    });
  }

  public static PictureFragment newInstance() {
    if (instance == null) {
      instance = new PictureFragment();
    }
    return instance;
  }

  //更新数据
  private void setRecyclerData(List<NavFuliBean> data) {
    isLoading = false;
    loadingLl.setVisibility(View.GONE);
    if (isLoadMore) {
      //加载更多
      // mData.addAll(data);

      mAdapter.addData(data);
    } else {
      //初始化
      mData = data;
      mAdapter.setData(data);
    }
  }

  private static class MyHandler extends Handler {
    WeakReference<PictureFragment> mReference;
    PictureFragment fragment;

    public MyHandler(PictureFragment fragment) {
      mReference = new WeakReference<PictureFragment>(fragment);
    }

    public void handleMessage(Message msg) {
      if (mReference == null || mReference.get() == null) {
        return;
      }
      fragment = mReference.get();
      switch (msg.what) {
        case INIT_DATA:
          fragment.setRecyclerData((List<NavFuliBean>) msg.obj);
          break;
      }
    }
  }
}
