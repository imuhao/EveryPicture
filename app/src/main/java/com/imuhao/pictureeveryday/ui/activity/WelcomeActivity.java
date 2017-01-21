package com.imuhao.pictureeveryday.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.app.MyApplication;
import com.imuhao.pictureeveryday.utils.IntentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Smile
 * @time 2016/6/22  11:17
 * @desc ${TODD}
 */
public class WelcomeActivity extends Activity {
    @Bind(R.id.tv_app_version)
    TextView mTvAppVersion;
    @Bind(R.id.root_rl)
    RelativeLayout rootRl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        MyApplication.getHandler().postDelayed(new Runnable() {
            public void run() {
                IntentUtils.startActivityFromFinish(WelcomeActivity.this, MainActivity.class);
            }
        }, 2000);
        initAnimation();
    }

    private void initAnimation() {
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
        aa.setDuration(2000);
        rootRl.setAnimation(aa);
    }
}
