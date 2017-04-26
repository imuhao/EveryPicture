package com.imuhao.pictureeveryday.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.ui.fragment.AboutActivity;
import com.imuhao.pictureeveryday.ui.fragment.AboutFragment;
import com.imuhao.pictureeveryday.ui.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.ui.fragment.PictureFragment;
import com.imuhao.pictureeveryday.ui.fragment.SettingFragment;
import com.imuhao.pictureeveryday.utils.ImageUtils;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import com.imuhao.pictureeveryday.utils.MainTab;
import java.util.List;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private long exit_Time;

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.navigationView) NavigationView mNavigationView;
  @Bind(R.id.drawerLayout) DrawerLayout mDrawerLayout;
  private PictureFragment mPictureFragment;
  private AboutFragment mAboutFragment;
  private CategoryFragment mCategoryFragment;
  private SettingFragment mSettingFragment;

  @Override protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override protected void initView() {
    setSwipeBackEnable(false);

    initToolBar(mToolbar, getString(R.string.app_name), R.drawable.icon_menu2);
    initNavigationView();
    setMenuSelection(MainTab.CATEGORY);
  }

  private void initNavigationView() {
    mNavigationView.setItemIconTintList(null);
    mNavigationView.setNavigationItemSelectedListener(this);
    //画一个圆形的Bitmap图片
    ImageView imageView = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.image);
    Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bbb);
    ImageUtils.setCircleUtils(imageView, srcBitmap);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  //根据菜单状态来显示不同的Fragment
  private void setMenuSelection(MainTab tab) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    hideAllFragment(transaction);
    //图片
    if (MainTab.PICTURE.getName().equals(tab.getName())) {
      if (mPictureFragment == null) {
        mPictureFragment = PictureFragment.newInstance();
        transaction.add(R.id.fl_content, mPictureFragment);
      } else {
        transaction.show(mPictureFragment);
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

  @Override public void onBackPressed() {
    if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
      mDrawerLayout.closeDrawers();
      return;
    }
    //如果图片Fragment是隐藏的,就显示
    if (mCategoryFragment.isHidden()) {
      mToolbar.setTitle("文章");
      setMenuSelection(MainTab.CATEGORY);
      mNavigationView.getMenu().findItem(R.id.nav_fuli).setChecked(true);
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
        initToolBar(mToolbar, MainTab.PICTURE.getName(), R.drawable.icon_menu2);
        break;
      case R.id.menu_category:
        initToolBar(mToolbar, MainTab.CATEGORY.getName(), R.drawable.icon_menu2);
        setMenuSelection(MainTab.CATEGORY);
        break;
      case R.id.menu_exit://退出
        finish();
        break;
      case R.id.menu_setup://设置
        initToolBar(mToolbar, MainTab.SETTING.getName(), R.drawable.icon_menu2);
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
    }
    return true;
  }
}
