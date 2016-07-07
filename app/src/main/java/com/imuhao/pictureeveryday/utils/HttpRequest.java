package com.imuhao.pictureeveryday.utils;

/**
 * @author Smile
 * @time 2016/6/22  16:22
 * @desc ${TODD}
 */
public interface HttpRequest {
    //失败
    void  onFailure(String error);
    //成功
    void  onResponse(String jsonStr);

}
