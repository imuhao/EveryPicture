package com.imuhao.pictureeveryday.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.bean.NavFuliBean;
import com.imuhao.pictureeveryday.ui.activity.ReviewPictureActivity;
import com.imuhao.pictureeveryday.ui.adapter.NavFuliAdapter;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.ui.listener.OnRcvScrollListener;
import com.imuhao.pictureeveryday.ui.listener.OnScrollBottomListener;
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
public class PictureFragment extends BaseFragment implements View.OnClickListener {
  private static final int INIT_DATA = 0X001;
  private static final int LOAD_ERROR = 0x002;

  private int index = 1;
  private int pageSize = 20;

  private static PictureFragment instance;

  private List<NavFuliBean> mData;
  private ArrayList<String> mUrlList;

  private NavFuliAdapter mAdapter;
  private RecyclerView mRecyclerView;
  private MyHandler mHandler = new MyHandler(this);
  private ImageView loadingImg;
  private RelativeLayout loadingLl;
  private Button mBtnRetryLoad;

  private boolean isLoadMore;
  private boolean isLoading;

  @Override protected int getLayoutId() {
    return R.layout.fragment_navfuli;
  }

  public void onDestroy() {
    super.onDestroy();
    index = 0;
  }

  public void initData() {
    isLoading = true;
    loadingLl.setVisibility(View.VISIBLE);
    mBtnRetryLoad.setVisibility(View.GONE);
    String url = Contance.getFuliUrl(pageSize, index);
    index++;
    HttpUtils.getInstance().doGet(url, new HttpRequest() {
      public void onFailure(String error) {
        mHandler.obtainMessage(LOAD_ERROR, error).sendToTarget();
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
    mBtnRetryLoad = (Button) view.findViewById(R.id.btn_retry_load);
    mBtnRetryLoad.setOnClickListener(this);

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
        new OnRcvScrollListener(getActivity(), new OnScrollBottomListener() {

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

  private void setRecyclerData(List<NavFuliBean> data) {
    isLoading = false;
    loadingLl.setVisibility(View.GONE);
    if (isLoadMore) {
      mAdapter.addData(data);
    } else {
      mData = data;
      mAdapter.setData(data);
    }
  }

  private void showErrorMsg(String msg) {
    index--;
    mBtnRetryLoad.setVisibility(View.VISIBLE);
    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    loadingLl.setVisibility(View.GONE);
  }

  @Override public void onClick(View view) {
    //加载失败重试
    if (mBtnRetryLoad == view) {
      initData();
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
        case LOAD_ERROR:
          String error = (String) msg.obj;
          fragment.showErrorMsg(error);
          break;
      }
    }
  }
}
