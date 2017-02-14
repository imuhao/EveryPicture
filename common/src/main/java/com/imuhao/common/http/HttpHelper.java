package com.imuhao.common.http;

import android.content.Context;
import android.os.Build;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.imuhao.common.BuildConfig;
import com.imuhao.common.CommonConfig;
import com.imuhao.common.utils.AppUtils;
import com.imuhao.common.utils.DeviceId;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dafan on 2015/11/12 0012.
 */
public class HttpHelper {

	/**
	 * 初始化网络框架
	 *
	 * @param context
	 */
	public void init(final Context context) {
		final String pn = AppUtils.getAppName(context);
		final String channel = pn.equals("懒人游") ? "lanrenyou" : "dingweixiugai";

		Interceptor headerInter = new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request newRequest = chain.request().newBuilder()
						.addHeader("User-Agent", getUserAgent(context))
						.addHeader("channel", channel)
						.addHeader("version", AppUtils.getVersionName(context))
						.addHeader("device_id", CommonConfig.instance().getClientid())
						.build();
				okhttp3.Response response = chain.proceed(newRequest);
				return response;
			}
		};

		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		if (BuildConfig.DEBUG) {
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		} else {
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
		}

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(20, TimeUnit.SECONDS)
				.readTimeout(20, TimeUnit.SECONDS)

				.addInterceptor(headerInter)
				.addInterceptor(loggingInterceptor)
				.build();

		OkHttpUtils.initClient(okHttpClient);

		// Glide网络层使用Okhttp3
		Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
	}

	/**
	 * User-Agent
	 *
	 * @param context
	 * @return
	 */
	public static String getUserAgent(Context context) {
		StringBuilder ua = new StringBuilder(CommonConfig.instance().getBase_url());
		ua.append('/' + AppUtils.getVersionName(context) + '_' + AppUtils.getVersionCode(context));
		ua.append("/Android");
		ua.append("/" + Build.VERSION.RELEASE);
		ua.append("/" + Build.MODEL);
		ua.append("/" + DeviceId.id(context));// 客户端唯一标识
		return ua.toString();
	}
}
