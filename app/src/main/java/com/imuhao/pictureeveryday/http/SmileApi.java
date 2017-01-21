package com.imuhao.pictureeveryday.http;

import com.imuhao.pictureeveryday.bean.EssayBean;
import com.imuhao.pictureeveryday.bean.HttpResult;
import com.imuhao.pictureeveryday.ui.callback.DataCallBack;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Smile
 * @time 2016/6/24  12:04
 * @desc ${TODD}
 */
public class SmileApi {


    public static void getEssatData(String type, final int what, int count, int index, final DataCallBack dataCallBack) {
        ApiService service = BuildApi.getApiService();
        Call<HttpResult<List<EssayBean>>> call = service.getCommonDateNew(type, count, index);

        //type, count, pageIndex
        call.enqueue(new Callback<HttpResult<List<EssayBean>>>() {
            public void onResponse(Response<HttpResult<List<EssayBean>>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (!response.body().isError()) {
                        //成功的回调
                        dataCallBack.onSuccess(what,response.body().getResults());
//                        dataCallBack.onSuccessList(what, response.body().getResults());
                    } else {
                        dataCallBack.onError(what, "获取数据失败" + response.code());
                    }
                } else {
                    dataCallBack.onError(what, "获取数据失败" + response.code());
                }
            }

            public void onFailure(Throwable t) {
                dataCallBack.onError(what, t.getMessage());
            }
        });

    }
}
