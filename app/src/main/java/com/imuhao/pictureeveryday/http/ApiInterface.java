package com.imuhao.pictureeveryday.http;

import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.bean.GankBean;
import com.imuhao.pictureeveryday.bean.HttpResult;
import com.imuhao.pictureeveryday.bean.ItemBean;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author Smile
 * @time 2016/6/24  11:46
 * @desc ${TODD}
 */
public interface ApiInterface {

    @GET("data/{type}/{count}/{index}")
    Call<HttpResult<List<EssayBean>>> getCommonDateNew(@Path("type") String type, @Path("count") int count, @Path("index") int index);

    @GET("data/福利/{pageSize}/{index}")
    Call<HttpResult<List<ItemBean>>> getImageList(@Path("pageSize") int pageSize, @Path("index") int index);

    @GET("day/{year}/{month}/{day}")
    Call<GankBean> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
