package com.imuhao.pictureeveryday.ui.activity;

import android.app.Activity;
import android.os.Bundle;


/**
 * @author Smile
 * @time 2016/6/22  11:17
 * @desc ${TODD}
 */
public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.launch(this);
        finish();
    }
}
