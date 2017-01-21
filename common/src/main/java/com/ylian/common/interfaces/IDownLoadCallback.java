package com.ylian.common.interfaces;

import java.io.File;

import okhttp3.Call;

/**
 * Created by lw on 2016/9/19.
 * 功能描述:
 * 文件下载回调
 */
public interface IDownLoadCallback {
	void onResponse(File response, int id);

	void onFailure(Call call, Exception e, int id);

	void inProgress(float progress, long total, int id);
}
