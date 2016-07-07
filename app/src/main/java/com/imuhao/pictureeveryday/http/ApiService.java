package com.imuhao.pictureeveryday.http;


import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.bean.HttpResult;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Smile
 * @time 2016/6/24  11:46
 * @desc ${TODD}
 */
public interface ApiService {

    //    //http://gank.io/api/data/Android/10/1
    @GET("data/{type}/{count}/{index}")
    Call<HttpResult<List<EssayBean>>> getCommonDateNew(@Path("type") String type,@Path("count") int count,@Path("index") int index);



}
