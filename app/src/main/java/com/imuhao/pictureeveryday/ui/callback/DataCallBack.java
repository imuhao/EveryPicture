package com.imuhao.pictureeveryday.ui.callback;

/**
 * @author Smile
 * @time 2016/6/24  11:38
 * @desc ${TODD}
 */
public interface DataCallBack<T> {

    /**
     * 成功的回调
     */
    void onSuccess(int what, T t);

    /**
     * 失败的回调
     */
    void onError(int width, String error);
}
