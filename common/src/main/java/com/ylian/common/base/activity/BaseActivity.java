package com.ylian.common.base.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;

import com.ylian.common.R;
import com.ylian.common.base.fragment.BaseFragment;
import com.ylian.common.base.mvp.BaseView;
import com.ylian.common.statusbar.StatusBarHelper;
import com.ylian.common.swipeback.SwipeBackActivity;
import com.ylian.common.utils.Logger;
import com.ylian.common.utils.ToastUtils;
import com.ylian.common.widget.Dialoghelper;

import java.util.List;

/**
 * Created by lw on 2016/9/8.
 * 基类Activty
 */
public abstract class BaseActivity extends SwipeBackActivity
    implements BaseView, BaseFragment.OnAddFragmentListener {
  private String TAG = getClass().getSimpleName();
  /**
   * 是否支持双击，默认为不支持
   */
  private boolean mDoubleClickEnable = false;
  /**
   * 上一次点击的时间戳
   */
  private long mLastClickTime;
  /**
   * 被判断为重复点击的时间间隔
   */
  private final long MIN_CLICK_DELAY_TIME = 100;

  //布局文件ID
  private int mActivityLayoutId = R.layout.activity_base;

  //布局中Fragment的ID
  private int mFragmentContainer = R.id.fl_container;

  //获取第一个fragment
  protected abstract Fragment initFirstFragment();

  protected ProgressDialog mWaitDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        /*RelativeLayout view = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        view.addView(getLayoutInflater().inflate(getLayoutId(), null));
		setContentView(view);*/
    setContentView(getLayoutId());
    onTintStatusBar();

    if (null != getIntent()) {
      handleIntent(getIntent());
    }

    if (savedInstanceState == null) {
      //避免重复添加Fragment
      Fragment firstFragment = initFirstFragment();
      if (null != firstFragment) {
        addFirstFragment(firstFragment);
      }
    }

    initView(savedInstanceState);
  }

  /**
   *
   */
  public void onTintStatusBar() {
    StatusBarHelper helper = new StatusBarHelper(this, StatusBarHelper.LEVEL_19_TRANSLUCENT,
        StatusBarHelper.LEVEL_21_VIEW);
    helper.setColor(getResources().getColor(R.color.colorAccent));
  }

  /**
   * @return
   */
  protected int getLayoutId() {
    return mActivityLayoutId;
  }

  /**
   * @param savedInstanceState
   */
  protected abstract void initView(Bundle savedInstanceState);

  /**
   * @param enterAnim
   * @param exitAnim
   */
  @Override public void overridePendingTransition(int enterAnim, int exitAnim) {
    super.overridePendingTransition(R.anim.page_right_in, R.anim.page_right_out);
  }

  /**
   * 处理返回操作
   */
  public boolean onActivityBackPressed() {
    int count = getSupportFragmentManager().getBackStackEntryCount();
    if (count == 0 || count == 1) {
      onActivityFinish();
      return true;
    } else {
      removeFragment();
    }
    return false;
  }

  /**
   *
   */
  protected void onActivityFinish() {
    finish();
  }

  /**
   * @param toFragment
   */
  private void addFirstFragment(Fragment toFragment) {
    getSupportFragmentManager().beginTransaction()
        .add(mFragmentContainer, toFragment, toFragment.getClass().getSimpleName())
        .addToBackStack(toFragment.getClass().getSimpleName())
        .commit();
  }

  /**
   * @param toFragment
   */
  public void addFragment(Fragment toFragment) {
    if (toFragment == null) return;

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setCustomAnimations(R.anim.page_right_in, R.anim.page_stay, R.anim.page_stay,
        R.anim.page_right_out)
        .add(mFragmentContainer, toFragment, toFragment.getClass().getSimpleName())
        .addToBackStack(toFragment.getClass().getSimpleName())
        .commitAllowingStateLoss();
  }

  /**
   * 替换fragment
   */
  public void replaceFragment(Fragment fragment) {
    if (fragment == null) return;
    FragmentManager fm = getSupportFragmentManager();
    int count = fm.getBackStackEntryCount();
    for (int i = 0; i < count; i++) {
      fm.popBackStack();
    }
    FragmentTransaction ft = fm.beginTransaction();
    ft.add(mFragmentContainer, fragment, fragment.getClass().getSimpleName())
        .addToBackStack(fragment.getClass().getSimpleName())
        .commitAllowingStateLoss();
  }

  /**
   * 移除fragment
   */
  public void removeFragment() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
      getSupportFragmentManager().popBackStack();
    } else {
      finish();
    }
  }

  /**
   * 获取当前显示的第一个Fragment
   */
  public Fragment getTopFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    List<Fragment> fragments = fragmentManager.getFragments();
    if (fragments == null || fragments.isEmpty()) return null;
    return fragmentManager.getFragments().get(fragments.size() - 1);
  }

  /**
   * 过滤doubleClick
   */
  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_UP) {
      if (isDoubleClick()) {
        Logger.d(TAG, "重复点击");
        return true;
      }
    }
    return super.dispatchTouchEvent(ev);
  }

  /**
   * 检测双击
   */
  public boolean isDoubleClick() {
    if (mDoubleClickEnable) return false;
    long time = System.currentTimeMillis();
    if (time - mLastClickTime > MIN_CLICK_DELAY_TIME) {
      mLastClickTime = time;
      return false;
    } else {
      return true;
    }
  }

  /**
   * 是否支持双击
   */
  protected void setDoubleClickEnable(boolean isDoubleClickEnable) {
    mDoubleClickEnable = isDoubleClickEnable;
  }

  /**
   * 获取Intent
   */
  protected void handleIntent(Intent intent) {
  }

  @Override public void onAddFragment(Fragment toFragment) {
    addFragment(toFragment);
  }

  /**
   * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
   *
   * @return true: Activity可以滑动退出, 并且总是优先;  false: Activity不允许滑动退出
   */
  @Override public boolean swipeBackPriority() {
    return super.swipeBackPriority();
  }

  /**
   * 返回键返回事件
   * 如果Activity里只有一个Fragment的时候直接销毁Activity
   */
  @Override public void onBackPressed() {
    onActivityBackPressed();
  }

  /**
   *
   */
  public void showWaitDialog() {
    if (mWaitDialog == null) {
      mWaitDialog = Dialoghelper.getWaitDialogNotCannel(this, "精彩值得等待……");
    }
    mWaitDialog.show();
  }

  /**
   *
   */
  public void hideWaitDialog() {
    if (mWaitDialog != null && mWaitDialog.isShowing()) {
      mWaitDialog.dismiss();
    }
  }

  /**
   * @param msg
   */
  public void showToast(String msg) {
    ToastUtils.showToastShort(this, msg);
  }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getTopFragment();
        if (fragment != null)
            fragment.onActivityResult(requestCode, resultCode, data);
    }*/
}
