package com.imuhao.common.base.mvp;


/**
 * Created by dafan on 2016/6/16 0016.
 */
public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {

	public M model;
	public V view;

	/**
	 * @param m
	 * @param v
	 */
	public void attachModelView(M m, V v) {
		this.view = v;
		this.model = m;
	}

	/**
	 *
	 */
	public void detachView() {
		this.model.cancelCalls();
		this.view = null;
	}
}