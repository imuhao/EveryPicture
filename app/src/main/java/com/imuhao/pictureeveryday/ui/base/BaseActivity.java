package com.imuhao.pictureeveryday.ui.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.view.swipeback.SwipeBackActivity;

/**
 * @author Smile
 * @time 2016/6/22  13:13
 * @desc ${TODD}
 */
public abstract class BaseActivity extends SwipeBackActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    initView();
  }

  protected abstract int getLayoutId();

  protected abstract void initView();

  public void initToolBar(Toolbar toolbar, String title, int icon) {
    toolbar.setTitle(title);// 标题的文字需在setSupportActionBar之前，不然会无效
    toolbar.setTitleTextColor(Color.WHITE);
    setSupportActionBar(toolbar);
    ActionBar ab = getSupportActionBar();
    assert ab != null;
    ab.setDisplayHomeAsUpEnabled(true);
    ab.setHomeAsUpIndicator(icon);//设置home键的图片
  }

  /**
   * 设置状态栏颜色
   */
  protected void initStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      // 设置状态栏透明
      this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      // 生成一个状态栏大小的矩形
      View statusView = createStatusView(this, getResources().getColor(R.color.colorPrimary));
      // 添加 statusView 到布局中
      ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
      decorView.addView(statusView);
      // 设置根布局的参数
      ViewGroup rootView =
          (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
      rootView.setFitsSystemWindows(true);
      rootView.setClipToPadding(true);
    }
  }

  /**
   * 生成一个和状态栏大小相同的矩形条
   *
   * @param activity 需要设置的activity
   * @param color 状态栏颜色值
   * @return 状态栏矩形条
   */
  private static View createStatusView(Activity activity, int color) {
    // 获得状态栏高度
    int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
    int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

    // 绘制一个和状态栏一样高的矩形
    View statusView = new View(activity);
    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
    statusView.setLayoutParams(params);
    statusView.setBackgroundColor(color);
    return statusView;
  }
}
