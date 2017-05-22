package com.imuhao.pictureeveryday.http;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Smile
 * @time 2017/4/26  下午3:10
 * @desc ${TODD}
 */
public abstract class StringCallback<T> implements Callback<T> {

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        onSuccess(response.body());
    }

    @Override
    public void onFailure(Throwable t) {
        onError(t.getMessage());
    }

    public abstract void onSuccess(T t);

    public abstract void onError(String error);
}
