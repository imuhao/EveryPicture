package com.ylian.common.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by dafan on 2016/6/17 0017.
 */
public abstract class BaseLazyFragment extends BaseFragment {
	private boolean isVisible = false; // 当前Fragment是否可见
	private boolean isInitView = false; // 是否与View建立起映射关系
	private boolean isFirstLoad = true; // 是否是第一次加载数据
	public boolean isCanLoad = isVisible && isInitView; // 是否可以加载数据

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		isInitView = true;
		lazyLoadData();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser) {
			isVisible = true;
			lazyLoadData();
		} else {
			isVisible = false;
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	private void lazyLoadData() {
		if (!isVisible)
			return;
		if (!isInitView)
			return;
		onShowToScreen(isFirstLoad);
		isFirstLoad = false;
	}

	/**
	 * @param isFirstLoad 是否第一次加载
	 */
	protected abstract void onShowToScreen(boolean isFirstLoad);
}
