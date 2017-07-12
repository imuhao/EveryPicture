package com.imuhao.pictureeveryday.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.github.moduth.blockcanary.BlockCanary;
import com.imuhao.pictureeveryday.BuildConfig;

/**
 * @author Smile
 * @time 2017/5/8  上午9:42
 * @desc ${TODD}
 */
public class InitializeService extends IntentService {

    public static final String INITIALIZE_SERVICE = "initialize_service";

    public InitializeService() {
        super("InitializeService");
    }

    public static void lunch(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(INITIALIZE_SERVICE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction().equals(INITIALIZE_SERVICE)) {
            initApplication();
        }
    }

    private void initApplication() {
        if (BuildConfig.DEBUG) {
            BlockCanary.install(getApplicationContext(), new AppBlockCanaryContext()).start();
        }
    }
}
