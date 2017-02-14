package com.imuhao.common.oss;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.imuhao.common.CommonConfig;
import com.imuhao.common.utils.Logger;
import com.imuhao.common.utils.StringUtils;
import com.imuhao.common.utils.ToastUtils;
import com.imuhao.common.widget.Dialoghelper;

import java.io.File;

/**
 * Created by dafan on 2016/6/1 0001.
 */
public class OssHelper {

	private OSS mOSS;
	private Context mContext;

	private String mFileName;
	private String mFilePath;

	public OssHelper(Context context) {
		mContext = context;
	}

	/**
	 * 设置需要上传的文件的名字：最好唯一
	 *
	 * @param name 文件名字
	 * @return
	 */
	public OssHelper name(String name) {
		if (TextUtils.isEmpty(name))
			throw new NullPointerException("file name isn't allow null!");

		this.mFileName = name;
		return this;
	}

	/**
	 * 设置需要上传的文件的路径<br>
	 *
	 * @param path 文件路径
	 * @return
	 */
	public OssHelper path(String path) {
		if (TextUtils.isEmpty(path))
			throw new NullPointerException("file path isn't allow null!");

		File file = new File(path);
		if (!file.exists() || !file.isFile())
			throw new IllegalArgumentException("file isn't exists or it's not file!");

		this.mFilePath = path;
		return this;
	}

	/**
	 * 异步上传
	 *
	 * @param listener
	 * @return
	 */
	public void asyncUpload(final UploadListener listener) {
		if (listener == null)
			throw new IllegalArgumentException("Upload OssConfigListener isn't allow null");

		// 获取缓存配置
		OssConfig ossConfig = OssConfigUtils.getOssConfig(mContext);
		// 没有缓存配置或者缓存配置过期
		if (ossConfig != null) {
			upload(ossConfig, listener);
		} else {
			final Dialog dialog = Dialoghelper.getWaitDialog(mContext, "正在获取配置信息，请稍等...");
			OssConfigApi.getConfig(mContext, new OssConfigApi.OssConfigListener() {
				@Override
				public void before() {
					dialog.show();
				}

				@Override
				public void after() {
					dialog.cancel();
				}

				@Override
				public void success(OssConfig ossConfig) {
					upload(ossConfig, listener);
				}

				@Override
				public void failed(String errmsg) {
					ToastUtils.showToastLong(mContext, errmsg);
				}
			});
		}
	}

	private OSSAsyncTask upload(OssConfig ossConfig, final UploadListener listener) {

		OSSStsTokenCredentialProvider credentialProvider =
				new OSSStsTokenCredentialProvider(
						ossConfig.getAccessKeyId(),
						ossConfig.getAccessKeySecret(),
						ossConfig.getSecurityToken());

		ClientConfiguration clientConfiguration = new ClientConfiguration();
		clientConfiguration.setConnectionTimeout(150 * 1000); // 连接超时，默认15秒
		clientConfiguration.setSocketTimeout(150 * 1000); // socket超时，默认15秒
		clientConfiguration.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
		clientConfiguration.setMaxErrorRetry(3); // 失败后最大重试次数，默认3次

		mOSS = new OSSClient(mContext.getApplicationContext(),
				ossConfig.getEndpoint(),
				credentialProvider,
				clientConfiguration);

		PutObjectRequest put = new PutObjectRequest(ossConfig.getBucketName(),
				ossConfig.getUploadFilePath() + mFileName, mFilePath);

		// 异步上传时可以设置进度回调
		put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
			@Override
			public void onProgress(final PutObjectRequest request, final long currentSize, final long totalSize) {
				Logger.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
				final int progress = (int) (currentSize * 1.0f / totalSize * 100);
				new Handler(mContext.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						listener.onProgress(request, progress);
					}
				});
			}
		});

		OSSAsyncTask task = mOSS.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
			@Override
			public void onSuccess(final PutObjectRequest request,
			                      final PutObjectResult result) {
				new Handler(mContext.getMainLooper()).post(new Runnable() {
					@Override
					public void run() {
						listener.onSuccess(request, result);
					}
				});
			}

			@Override
			public void onFailure(final PutObjectRequest request,
			                      final ClientException clientExcepion,
			                      final ServiceException serviceException) {
				// 本地异常如网络异常等
				if (clientExcepion != null) {
					new Handler(mContext.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							listener.onFailure();
						}
					});
				}

				// 服务异常
				if (serviceException != null) {
					Logger.e("ErrorCode", serviceException.getErrorCode());
					Logger.e("RequestId", serviceException.getRequestId());
					Logger.e("HostId", serviceException.getHostId());
					Logger.e("RawMessage", serviceException.getRawMessage());
					new Handler(mContext.getMainLooper()).post(new Runnable() {
						@Override
						public void run() {
							listener.onFailure();
						}
					});
				}
			}
		});

		return task;
	}

	public interface UploadListener {
		/**
		 * 上传进度回调，主线程调用执行
		 *
		 * @param request 回调信息
		 */
		void onProgress(PutObjectRequest request, int progress);

		/**
		 * 上传成功回调，主线程调用执行
		 *
		 * @param request
		 * @param result
		 */
		void onSuccess(PutObjectRequest request, PutObjectResult result);

		/**
		 * 上传失败的回调
		 */
		void onFailure();
	}

	/**
	 * 生成一个图片上传时候所用的名字
	 *
	 * @return
	 */
	public static String getUploadImgName() {
		return CommonConfig.instance().getClientid() + "-" + System.currentTimeMillis() + "-" + StringUtils.getRandomString(10);
	}
}
