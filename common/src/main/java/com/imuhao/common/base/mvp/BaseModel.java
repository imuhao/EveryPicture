package com.imuhao.common.base.mvp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by dafan on 2016/6/16 0016.
 */
public abstract class BaseModel {
	public List<Call> mCalls;

	/**
	 * 添加一个请求
	 *
	 * @param call
	 * @param <T>
	 * @return
	 */
	public <T> Call<T> addCall(Call<T> call) {
		if (call == null)
			return null;
		if (mCalls == null)
			mCalls = new ArrayList<>();
		mCalls.add(call);
		return call;
	}

	/**
	 * 获取所有请求
	 *
	 * @return
	 */
	public List<Call> getCalls() {
		return mCalls;
	}

	/**
	 * 取消所有请求
	 */
	public void cancelCalls() {
		if (mCalls == null || mCalls.isEmpty())
			return;
		for (Call call : mCalls) {
			if (call.isCanceled()) {
			} else {
				call.cancel();
			}
		}
	}
}
