package com.imuhao.common.oss;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.imuhao.common.CommonConfig;
import com.imuhao.common.http.OkCallBack;
import com.imuhao.common.http.Result;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Request;

/**
 * Created by dafan on 2016/8/22 0022.
 */
public class OssConfigApi {
	public static void getConfig(final Context context, final OssConfigListener ossConfigListener) {
		OkHttpUtils.get()
				.url(CommonConfig.instance().getBase_url() + "config/oss_config")
				.build()
				.execute(new OkCallBack<Result<OssConfig>>(new TypeToken<Result<OssConfig>>() {
				}.getType()) {
					@Override
					public void onBefore(Request request, int id) {
						super.onBefore(request, id);
						ossConfigListener.before();
					}

					@Override
					public void onAfter(int id) {
						super.onAfter(id);
						ossConfigListener.after();
					}

					@Override
					public void onError(String errmsg, int id) {
						ossConfigListener.failed(errmsg);
					}

					@Override
					public void onResponse(Result<OssConfig> response, int id) {
						OssConfig ossConfig = response.getData();
						ossConfig.setTime(System.currentTimeMillis());
						OssConfigUtils.saveOssConfig(context, ossConfig);
						ossConfigListener.success(response.getData());
					}
				});
	}

	interface OssConfigListener {
		void before();

		void after();

		void success(OssConfig ossConfig);

		void failed(String errmsg);
	}
}
