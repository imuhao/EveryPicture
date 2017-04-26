package com.imuhao.pictureeveryday.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.imuhao.pictureeveryday.R;
import com.mylhyl.circledialog.BaseCircleDialog;

/**
 * @author Smile
 * @time 2017/4/26  下午3:54
 * @desc ${TODD}
 */
public class LoadingDialogView extends BaseCircleDialog {

  public static LoadingDialogView getInstance() {
    LoadingDialogView loadingDialogView = new LoadingDialogView();
    loadingDialogView.setCanceledBack(false);
    loadingDialogView.setCanceledOnTouchOutside(false);
    return loadingDialogView;
  }

  @Override public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
    View view = inflater.inflate(R.layout.loading_item, container, false);
    return view;
  }
}
