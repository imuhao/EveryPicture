package com.ylian.common.http;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.ProgressDialog;

import com.google.gson.JsonParseException;
import com.ylian.common.widget.Dialoghelper;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by smile on 16-12-24.
 */

public abstract class RtDialogCallback<T> implements Callback<Result<T>> {

    private ProgressDialog mWaitDialog;

    public RtDialogCallback(Activity activity) {
        mWaitDialog = Dialoghelper.getWaitDialog(activity, "精彩值得等待……");
        mWaitDialog.show();
    }

    public RtDialogCallback() {

    }

    @Override
    public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
        Result<T> result = response.body();

        // 请求成功
        if (result.getError() == 0) {
            onRtResponse(result.getData());
        } else {
            onRtFailure(result.getMsg());
        }

        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
            mWaitDialog = null;
        }

        onRtCommon();
    }

    @Override
    public void onFailure(Call<Result<T>> call, Throwable t) {

        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
            mWaitDialog = null;
        }

        // 执行被取消
        if (call != null && call.isCanceled())
            return;

        // 网络问题
        if (t instanceof ConnectException ||
                t instanceof NetworkErrorException ||
                t instanceof SocketTimeoutException ||
                t instanceof UnknownHostException) {
            onRtFailure("网络连接异常");
        }

        // 数据解析失败
        else if (t instanceof JsonParseException ||
                t instanceof JSONException ||
                t instanceof ParseException ||
                t instanceof ClassCastException ||
                t instanceof IllegalStateException) {
            onRtFailure("数据解析失败\n" + t.getMessage());
        }

        // 其他暂时未知的错误
        else {
            onRtFailure("未知错误\n" + t.getMessage());
        }
        onRtCommon();
    }

    public abstract void onRtResponse(T data);

    public abstract void onRtFailure(String error);

    public void onRtCommon() {

    }
}
