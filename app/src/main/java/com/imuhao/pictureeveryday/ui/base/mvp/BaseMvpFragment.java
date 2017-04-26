package com.imuhao.pictureeveryday.ui.base.mvp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
import com.imuhao.pictureeveryday.ui.base.BaseLazyFragment;
import com.imuhao.pictureeveryday.utils.ClassUtils;

/**
 * @author Smile
 * @time 2017/4/19  上午9:38
 * @desc ${TODD}
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseLazyFragment
    implements BaseView {

  public P presenter;
  protected ProgressDialog waitDialog;

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    presenter = ClassUtils.getT(this, 0);
    presenter.attachModelView(this);
    super.onViewCreated(view, savedInstanceState);
  }

  protected abstract int getLayoutId();

  protected abstract void initView(View view);

  public void showWaitDialog() {
    if (!Thread.currentThread().getName().equals("main")) {
      new Handler(Looper.getMainLooper()).post(new DialogRunnable());
    } else {
      new DialogRunnable().run();
    }
  }

  private class DialogRunnable implements Runnable {
    @Override public void run() {
      if (waitDialog == null) {
        waitDialog = new ProgressDialog(getActivity());
        /*View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_item, null);
        waitDialog.setView(view);*/
        waitDialog.setMessage("加载数据..");
      }
      waitDialog.show();
    }
  }

  public void hideWaitDialog() {
    if (waitDialog != null) {
      waitDialog.dismiss();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (presenter != null) {
      presenter.detachView();
    }
  }

  public void showToast(String message) {
    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
  }
}
