package com.imuhao.common.interfaces;

/**
 * Created by dafan on 2016/9/14 0014.
 */
public abstract class IDataListener<T> {
	public abstract void onSuccess(T t);

	public abstract void onFailure(String errmsg);

	public abstract void onCommon();

	public void inProgress(int progress) {

	}
}
