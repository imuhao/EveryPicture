package com.imuhao.pictureeveryday.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.Fragments;
import com.imuhao.pictureeveryday.ui.base.BaseActivity;
import com.imuhao.pictureeveryday.ui.listener.AbstractDrawerListener;
import com.imuhao.pictureeveryday.utils.FragmentUtil;
import com.imuhao.pictureeveryday.utils.IntentUtils;
import com.imuhao.pictureeveryday.utils.T;
import com.imuhao.pictureeveryday.utils.ThemeUtils;

import butterknife.Bind;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private long exit_Time;

    public static final String CHANGE_COLOR = "change_color";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.iv_open_menu)
    ImageView ivOpenMenu;

    private FragmentUtil fragmentUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @DebugLog
    @Override
    protected void initView() {
        fragmentUtil = new FragmentUtil(getSupportFragmentManager());

        setSwipeBackEnable(false);
        initNavigationView();
        setMenuSelection(Fragments.TODAY);
    }

    @DebugLog
    private void initNavigationView() {
        mNavigationView.getHeaderView(0).setBackgroundColor(ThemeUtils.getThemeColor());
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout.addDrawerListener(new AbstractDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mNavigationView.getHeaderView(0).setBackgroundColor(ThemeUtils.getThemeColor());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawers();//关闭导航条
        switch (item.getItemId()) {
            case R.id.nav_fuli://图片
                setMenuSelection(Fragments.PICTURE);
                title.setText(Fragments.PICTURE);
                break;
            case R.id.menu_category: //分类
                title.setText(Fragments.CATEGORY);
                setMenuSelection(Fragments.CATEGORY);
                break;
            case R.id.menu_exit://退出
                finish();
                break;
            case R.id.menu_setting://设置
                title.setText(Fragments.SETTING);
                setMenuSelection(Fragments.SETTING);
                break;
            case R.id.menu_share://分享
                IntentUtils.startAppShareText(this);
                return false;
            case R.id.menu_about://关于
                title.setText(Fragments.ABOUT);
                setMenuSelection(Fragments.ABOUT);
                break;
            case R.id.menu_today://今日
                title.setText(getString(R.string.app_name));
                setMenuSelection(Fragments.TODAY);
                break;
        }
        return true;
    }

    @OnClick({R.id.iv_open_menu})
    public void onClick(View view) {
        if (view == ivOpenMenu) {
            mDrawerLayout.openDrawer(Gravity.START);
        }
    }

    @DebugLog
    private void setMenuSelection(@Fragments String tab) {
        fragmentUtil.hide();
        fragmentUtil.show(tab);
    }

    public void setToolbarColor(Integer color) {
        mToolbar.setBackgroundColor(color);
    }
}
