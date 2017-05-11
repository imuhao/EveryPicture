package com.imuhao.pictureeveryday.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.ui.fragment.AboutActivity;
import com.imuhao.pictureeveryday.ui.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.ui.fragment.DayListFragment;
import com.imuhao.pictureeveryday.ui.fragment.PictureFragment;
import com.imuhao.pictureeveryday.ui.fragment.SettingFragment;
import com.imuhao.pictureeveryday.ui.listener.AbstractDrawerListener;
import com.imuhao.pictureeveryday.utils.GlideCircleTransform;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import com.imuhao.pictureeveryday.utils.MainTab;
import com.imuhao.pictureeveryday.utils.T;
import com.imuhao.pictureeveryday.utils.ThemeUtils;
import java.util.List;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private long exit_Time;

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.navigationView) NavigationView mNavigationView;
  @Bind(R.id.drawerLayout) DrawerLayout mDrawerLayout;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.iv_open_menu) ImageView ivOpenMenu;

  private PictureFragment mPictureFragment;
  private CategoryFragment mCategoryFragment;
  private SettingFragment mSettingFragment;
  private DayListFragment mDayListFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void initView() {
    setSwipeBackEnable(false);
    initNavigationView();
    setMenuSelection(MainTab.TODAY);
  }

  private void initNavigationView() {
    mNavigationView.getHeaderView(0).setBackgroundColor(ThemeUtils.getThemeColor());
    mNavigationView.setItemIconTintList(null);
    mNavigationView.setNavigationItemSelectedListener(this);
    mDrawerLayout.addDrawerListener(new AbstractDrawerListener() {
      @Override public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        mNavigationView.getHeaderView(0).setBackgroundColor(ThemeUtils.getThemeColor());
      }
    });
    ImageView imageView = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.image);
    Glide.with(imageView.getContext())
        .load(R.drawable.bbb)
        .transform(new GlideCircleTransform(imageView.getContext()))
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate()
        .into(imageView);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
      mDrawerLayout.closeDrawers();
      return;
    }
    if (MainTab.TODAY.getFragment().isHidden()) {
      title.setText(getString(R.string.app_name));
      setMenuSelection(MainTab.TODAY);
      mNavigationView.getMenu().findItem(R.id.menu_today).setChecked(true);
      return;
    }
    long currentTime = System.currentTimeMillis();
    if (currentTime - exit_Time > 2000) {
      exit_Time = currentTime;
      T.show(MainActivity.this, "再按一次退出应用");
      return;
    }
    super.onBackPressed();
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    mDrawerLayout.closeDrawers();//关闭导航条
    switch (item.getItemId()) {
      case R.id.nav_fuli://图片
        setMenuSelection(MainTab.PICTURE);
        title.setText(MainTab.PICTURE.getName());
        break;
      case R.id.menu_category: //分类
        title.setText(MainTab.CATEGORY.getName());
        setMenuSelection(MainTab.CATEGORY);
        break;
      case R.id.menu_exit://退出
        finish();
        break;
      case R.id.menu_setting://设置
        title.setText(MainTab.SETTING.getName());
        setMenuSelection(MainTab.SETTING);
        break;
      case R.id.menu_share://分享
        IntentUtils.startAppShareText(this);
        return false;
      case R.id.menu_about://关于
        AboutActivity.start(MainActivity.this);
        return false;
      case R.id.menu_today://今日
        title.setText(getString(R.string.app_name));
        setMenuSelection(MainTab.TODAY);
        break;
    }
    return true;
  }

  @OnClick({ R.id.iv_open_menu }) public void onClick(View view) {
    if (view == ivOpenMenu) {
      mDrawerLayout.openDrawer(Gravity.START);
    }
  }

  private void setMenuSelection(MainTab tab) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    hideAllFragment(transaction);
    Fragment fragment = tab.getFragment();
    if (!fragment.isAdded()) {
      transaction.add(R.id.fl_content, fragment);
    } else {
      transaction.show(fragment);
    }
    transaction.commit();
  }

  /**
   * 隐藏所有的Fragment
   */
  @SuppressWarnings("all") private void hideAllFragment(FragmentTransaction transaction) {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments == null || fragments.isEmpty()) return;
    for (Fragment fragment : fragments) {
      if (fragment.isVisible()) transaction.hide(fragment);
    }
  }

  public void setToolbarColor(Integer color) {
    mToolbar.setBackgroundColor(color);
  }
}
