package com.ylian.common.http;

/**
 * Created by dafan on 2016/8/17 0017.
 */
public class Result<T> {
	private int error;
	private String msg;
	private T data;

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}