package com.imuhao.pictureeveryday.callback;

/**
 * @author Smile
 * @time 2016/6/24  11:38
 * @desc ${TODD}
 */
public interface MyCallBack<T> {

    /**
     * 成功的回调
     */
    void onSuccess(int what, T t);

    /**
     * 成功的回调集合
     */
//    void onSuccessList(int what, List<T> results);

    /**
     * 失败的回调
     */
    void onError(int width, String error);
}
