package com.imuhao.pictureeveryday.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.imuhao.pictureeveryday.rxjava.RxJavaActivity;

/**
 * @author Smile
 * @time 2016/6/22  11:17
 * @desc ${TODD}
 */
public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
