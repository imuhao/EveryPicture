package com.ylian.common.http;

import android.accounts.NetworkErrorException;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dafan on 2016/7/27 0027.
 */
public abstract class OkCallBack<T> extends Callback<T> {
	private Type mType;
	private Class<T> mTClass;

	public OkCallBack(Class<T> tClass) {
		mTClass = tClass;
	}

	public OkCallBack(Type type) {
		mType = type;
	}

	@Override
	public T parseNetworkResponse(Response response, int i) throws Exception {
		String result = response.body().string();
		if (TextUtils.isEmpty(result)) {
			handleError("获取数据为空", i);
			return null;
		}

		JSONObject jo = new JSONObject(result);
		int err_code = jo.optInt("error", 0);
		if (err_code != 0) {
			String err_msg = jo.optString("msg", "错误信息NULL");
			handleError(err_msg, i);
			return null;
		}

		if (mTClass == String.class)
			return (T) result;

		try {
			if (mTClass != null)
				return new Gson().fromJson(result, mTClass);
			if (mType != null)
				return new Gson().fromJson(result, mType);
		} catch (Exception e) {
			handleError("数据解析异常", i);
		}
		return null;
	}

	/**
	 * 回到错误处理
	 *
	 * @param errmsg 错误信息
	 * @param id
	 */
	private void handleError(final String errmsg, final int id) {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				onError(errmsg, id);
			}
		});
	}

	public abstract void onError(String errmsg, int id);

	@Override
	public void onError(Call call, Exception e, int id) {
		if (e instanceof ConnectException ||
				e instanceof NetworkErrorException ||
				e instanceof SocketTimeoutException ||
				e instanceof UnknownHostException) {
			onError("网络连接失败", id);
		} else {
			String msg = e.getMessage();
			try {
				JSONObject jsonObject = new JSONObject(msg);
				String errmsg = jsonObject.optString("msg", "未知异常");
				onError(errmsg, id);
			} catch (Exception e1) {
				onError("解析错误信息异常", id);
			}
		}
	}
}
