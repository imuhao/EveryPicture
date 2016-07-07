package com.imuhao.pictureeveryday.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.base.BaseActivity;
import com.imuhao.pictureeveryday.fragment.AboutFragment;
import com.imuhao.pictureeveryday.fragment.CategoryFragment;
import com.imuhao.pictureeveryday.fragment.NavFuliFragment;
import com.imuhao.pictureeveryday.utils.IntentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private Context mContext;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.navigationView)
    NavigationView mNavigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    long exit_Time;

    private NavFuliFragment mNavFuliFragment;
    private AboutFragment mAboutFragment;
    private CategoryFragment mCategoryFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);
        initToolBar(mToolbar, "每日一图", R.drawable.icon_menu2);
        initNavigationView();
        setMenuSelection(0);
        // initStatusBar();//设置浸入式状态栏
    }

    /**
     * 设置状态栏颜色
     */
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(this, getResources().getColor(R.color.colorPrimary));
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) this.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }

    private void initNavigationView() {
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);//选中点击的item
                setTitle(item.getTitle());//改变标题
                mDrawerLayout.closeDrawers();//关闭导航条
                switch (item.getItemId()) {
                    case R.id.nav_fuli://图片
                        mToolbar.setTitle(item.getTitle());
                        initToolBar(mToolbar, "每日一图", R.drawable.icon_menu2);
                        setMenuSelection(0);
                        break;
                    case R.id.menu_category:
                        mToolbar.setTitle(item.getTitle());
                        initToolBar(mToolbar, "文章", R.drawable.icon_menu2);
                        setMenuSelection(2);
                        break;
                    case R.id.menu_collect://收藏
                        mToolbar.setTitle(item.getTitle());
                        Toast.makeText(MainActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_video:
                        mToolbar.setTitle(item.getTitle());
                        Toast.makeText(MainActivity.this, "视频", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_exit://退出
                        finish();
                        break;
                    case R.id.menu_setup://设置
                        item.setChecked(false);
                        Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_share:
                        item.setChecked(false);
                        IntentUtils.startAppShareText(mContext, "每日一图", "每日一图Android客户端,每天更新一张精美的照片.下载地址:fir.im/pictureEveryDay");
                        break;
                    case R.id.menu_about://关于
                        item.setChecked(false);
                        mToolbar.setTitle(item.getTitle());
                        setMenuSelection(1);
                        break;
                }
                return true;
            }
        });
        //画一个圆形的Bitmap图片
        ImageView image = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.image);
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.bbb);
        int radius = src.getWidth() / 2;
        BitmapShader bitmapShader = new BitmapShader(src, Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(dest);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        c.drawCircle(radius, radius, radius, paint);
        image.setImageBitmap(dest);

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

    //根据菜单状态来显示不同的Fragment
    private void setMenuSelection(int flag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //首先将所有的Fragment全部隐藏掉
        hideAllFragment(transaction);
        switch (flag) {
            case 0:
                /**
                 * 显示的时候首先判断Fragment是否为空,为空就代表是第一次创建
                 * 就创建实例,并且添加到容器中
                 * 不为空就显示出来
                 */
                if (mNavFuliFragment == null) {
                    mNavFuliFragment = NavFuliFragment.newInstance();
                    transaction.add(R.id.fl_content, mNavFuliFragment);
                } else {
                    transaction.show(mNavFuliFragment);
                }
                break;
            case 1:
                if (mAboutFragment == null) {
                    mAboutFragment = AboutFragment.newInstance();
                    transaction.add(R.id.fl_content, mAboutFragment);
                } else {
                    transaction.show(mAboutFragment);
                }
                break;
            case 2:

                if (mCategoryFragment == null) {
                    mCategoryFragment = CategoryFragment.getInstance();
                    transaction.add(R.id.fl_content, mCategoryFragment);
                } else {
                    transaction.show(mCategoryFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (mNavFuliFragment != null) {
            transaction.hide(mNavFuliFragment);
        }
        if (mAboutFragment != null) {
            transaction.hide(mAboutFragment);
        }

        if (mCategoryFragment != null) {
            transaction.hide(mCategoryFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        //如果图片Fragment是隐藏的,就显示
        if (mNavFuliFragment.isHidden()) {
            mToolbar.setTitle("每日一图");
            setMenuSelection(0);
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
}
