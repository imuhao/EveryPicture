package com.imuhao.pictureeveryday.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


/**
 * @author Smile
 * @time 2016/6/22  16:19
 * @desc ${TODD}
 */
public class HttpUtils {

    private HttpUtils() {
    }

    private static HttpUtils instance;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public String doGet(String url, final HttpRequest mRequest) {

        final String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (mRequest != null) {
                    mRequest.onFailure("请求网络失败");
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (mRequest != null) {
                        mRequest.onFailure("请求网络失败");
                    }
                } else {
                    if (mRequest != null) {
                        //成功的回调
                        mRequest.onResponse(response.body().string());
                    }
                }
            }


        });
        return result;
    }
}
