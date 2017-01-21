package com.ylian.common.http;

import android.accounts.NetworkErrorException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dafan on 2016/1/21 0021.
 */
public abstract class RtCallback<T> implements Callback<Result<T>> {

	@Override
	public void onResponse(Call<Result<T>> call, Response<Result<T>> response) {
		Result<T> result = response.body();

		// 请求成功
		if (result.getError() == 0) {
			onRtResponse(result.getData());
		} else {
			onRtFailure(result.getMsg());
			onRtFailure(result.getError(), result.getMsg());
		}
		onRtCommon();
	}

	@Override
	public void onFailure(Call<Result<T>> call, Throwable t) {
		// 执行被取消
		if (call != null && call.isCanceled())
			return;

		// 网络问题
		if (t instanceof ConnectException ||
				t instanceof NetworkErrorException ||
				t instanceof SocketTimeoutException ||
				t instanceof UnknownHostException) {
			onRtFailure("网络连接异常");
			onRtFailure(-1, "网络连接异常");
		}

		// 数据解析失败
		else if (t instanceof JsonParseException ||
				t instanceof JSONException ||
				t instanceof ParseException ||
				t instanceof ClassCastException ||
				t instanceof IllegalStateException) {
			onRtFailure("数据解析失败\n" + t.getMessage());
			onRtFailure(-1, "数据解析失败\n" + t.getMessage());
		}

		// 其他暂时未知的错误
		else {
			onRtFailure("未知错误\n" + t.getMessage());
			onRtFailure(-1, "未知错误\n" + t.getMessage());
		}

		onRtCommon();
	}

	public abstract void onRtResponse(T t);

	public abstract void onRtFailure(String errmsg);

	public void onRtFailure(int code, String errmsg) {
	}

	public abstract void onRtCommon();
}
