package com.ylian.common.base.activity;

import android.os.Bundle;

import com.ylian.common.base.mvp.BaseModel;
import com.ylian.common.base.mvp.BasePresenter;
import com.ylian.common.utils.ClassUtils;

/**
 * 基于BaseActivity的MVP模式的基类Activity
 *
 * @param <M>
 * @param <P>
 */
public abstract class BaseMvpActivity<M extends BaseModel, P extends BasePresenter>
		extends BaseActivity {

	private M model;
	public P presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initMvp();
		super.onCreate(savedInstanceState);
	}

	protected void initMvp() {
		model = ClassUtils.getT(this, 0);
		presenter = ClassUtils.getT(this, 1);
		presenter.attachModelView(model, this);
	}

	@Override
	protected void onDestroy() {
		if (presenter != null) {
			presenter.detachView();
		}
		super.onDestroy();
	}
}
