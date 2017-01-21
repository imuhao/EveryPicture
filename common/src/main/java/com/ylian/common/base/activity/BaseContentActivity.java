package com.ylian.common.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ylian.common.CommonConfig;
import com.ylian.common.utils.ToastUtils;

/**
 * @author Smile
 * @time 2016/9/10  18:42
 * @desc 包装Fragment的Activity
 */
public class BaseContentActivity extends BaseActivity {
    private Fragment mFragment;
    private boolean isNeedLogin; //页面是否需要登录

    /**
     * 跳转到Fragment
     */
    public static void startActivity(Context context, boolean isNeedLogin, String fragmentName) {
        Intent intent = new Intent(context, BaseContentActivity.class);
        intent.putExtra("fragment_name", fragmentName);
        intent.putExtra("is_need_login", isNeedLogin);
        context.startActivity(intent);
    }

    /**
     * 跳转到Fragment
     */
    public static void startActivity(Context context, boolean isNeedLogin, String fragmentName, Bundle bundle) {
        Intent intent = new Intent(context, BaseContentActivity.class);
        intent.putExtra("fragment_name", fragmentName);
        intent.putExtra("is_need_login", isNeedLogin);
        intent.putExtra("bundle", bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 跳转到Activity
     */
    public static void startActivityForActivityResult(Activity activity, boolean isNeedLogin, String fragmentName, int requestCode) {
        Intent intent = new Intent(activity, BaseContentActivity.class);
        intent.putExtra("fragment_name", fragmentName);
        intent.putExtra("is_need_login", isNeedLogin);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到Activity
     */
    public static void startActivityForFragmentResult(Fragment fragment, boolean isNeedLogin, String fragmentName, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), BaseContentActivity.class);
        intent.putExtra("fragment_name", fragmentName);
        intent.putExtra("is_need_login", isNeedLogin);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转到Activity
     */
    public static void startActivityForFragmentResult(Fragment fragment, boolean isNeedLogin, String fragmentName, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), BaseContentActivity.class);
        intent.putExtra("fragment_name", fragmentName);
        intent.putExtra("is_need_login", isNeedLogin);
        intent.putExtra("bundle", bundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void handleIntent(Intent intent) {
        isNeedLogin = intent.getBooleanExtra("is_need_login", false);
        if (isNeedLogin) {
            if (!CommonConfig.instance().islogin()) {
                ToastUtils.showToastShort(this, "请先登录后再继续操作!");
                BaseContentActivity.startActivity(this, false, CommonConfig.instance().getLoginFragment());
                finish();
                return;
            }
        }

        String fragmentName = intent.getStringExtra("fragment_name");
        if (!intent.hasExtra("bundle"))
            mFragment = Fragment.instantiate(this, fragmentName);
        else
            mFragment = Fragment.instantiate(this, fragmentName, intent.getBundleExtra("bundle"));
    }

    @Override
    protected Fragment initFirstFragment() {
        return mFragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }
}
