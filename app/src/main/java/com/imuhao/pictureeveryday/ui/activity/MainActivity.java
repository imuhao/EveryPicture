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
import android.widget.Toast;
import butterknife.Bind;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.ui.fragment.AboutActivity;
import com.imuhao.pictureeveryday.ui.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.ui.fragment.ImageListFragment;
import com.imuhao.pictureeveryday.ui.fragment.SettingFragment;
import com.imuhao.pictureeveryday.ui.fragment.TodayFragment;
import com.imuhao.pictureeveryday.utils.GlideCircleTransform;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import com.imuhao.pictureeveryday.utils.MainTab;
import java.util.List;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private long exit_Time;

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.navigationView) NavigationView mNavigationView;
  @Bind(R.id.drawerLayout) DrawerLayout mDrawerLayout;
  @Bind(R.id.title) TextView title;
  @Bind(R.id.iv_open_menu) ImageView ivOpenMenu;

  private ImageListFragment mImageListFragment;
  private CategoryFragment mCategoryFragment;
  private SettingFragment mSettingFragment;
  private TodayFragment mTodayFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void initView() {
    setSwipeBackEnable(false);
    initNavigationView();
    setMenuSelection(MainTab.TODAY);
  }

  private void initNavigationView() {
    mNavigationView.setItemIconTintList(null);
    mNavigationView.setNavigationItemSelectedListener(this);
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

    if (mTodayFragment.isHidden()) {
      title.setText(getString(R.string.app_name));
      setMenuSelection(MainTab.TODAY);
      mNavigationView.getMenu().findItem(R.id.menu_today).setChecked(true);
      return;
    }
    long currentTime = System.currentTimeMillis();
    if (currentTime - exit_Time > 2000) {
      //说明两次点击的间隔大于2秒
      exit_Time = currentTime;
      Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
      return;
    }
    finish();
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    item.setChecked(true);//选中点击的item
    setTitle(item.getTitle());//改变标题
    mDrawerLayout.closeDrawers();//关闭导航条
    switch (item.getItemId()) {
      case R.id.nav_fuli://图片
        setMenuSelection(MainTab.PICTURE);
        title.setText(MainTab.PICTURE.getName());
        break;
      case R.id.menu_category:
        title.setText(MainTab.CATEGORY.getName());
        setMenuSelection(MainTab.CATEGORY);
        break;
      case R.id.menu_exit://退出
        finish();
        break;
      case R.id.menu_setup://设置
        title.setText(MainTab.SETTING.getName());
        setMenuSelection(MainTab.SETTING);
        break;
      case R.id.menu_share:
        item.setChecked(false);
        IntentUtils.startAppShareText(this, getString(R.string.app_name),
            getString(R.string.share_content));
        break;
      case R.id.menu_about://关于
        item.setChecked(false);
        AboutActivity.start(MainActivity.this);
        break;
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

  //根据菜单状态来显示不同的Fragment
  private void setMenuSelection(MainTab tab) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    hideAllFragment(transaction);
    //图片
    if (MainTab.PICTURE.getName().equals(tab.getName())) {
      if (mImageListFragment == null) {
        mImageListFragment = ImageListFragment.newInstance();
        transaction.add(R.id.fl_content, mImageListFragment);
      } else {
        transaction.show(mImageListFragment);
      }
    }
    //分类
    else if (MainTab.CATEGORY.getName().equals(tab.getName())) {
      if (mCategoryFragment == null) {
        mCategoryFragment = CategoryFragment.getInstance();
        transaction.add(R.id.fl_content, mCategoryFragment);
      } else {
        transaction.show(mCategoryFragment);
      }
    }
    //设置
    else if (MainTab.SETTING.getName().equals(tab.getName())) {
      if (mSettingFragment == null) {
        mSettingFragment = SettingFragment.newInstance();
        transaction.add(R.id.fl_content, mSettingFragment);
      } else {
        transaction.show(mSettingFragment);
      }
    }
    //今日干货
    else if (MainTab.TODAY.equals(tab)) {
      if (mTodayFragment == null) {
        mTodayFragment = TodayFragment.newInstance();
        transaction.add(R.id.fl_content, mTodayFragment);
      } else {
        transaction.show(mTodayFragment);
      }
    }
    transaction.commit();
  }

  /**
   * 隐藏所有的Fragment
   */
  private void hideAllFragment(FragmentTransaction transaction) {
    List<Fragment> fragments = getSupportFragmentManager().getFragments();
    if (fragments == null || fragments.isEmpty()) return;
    for (Fragment fragment : fragments) {
      transaction.hide(fragment);
    }
  }
}
