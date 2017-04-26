package com.imuhao.pictureeveryday.ui.mvp;

import com.imuhao.pictureeveryday.bean.ImageBean;
import com.imuhao.pictureeveryday.ui.base.mvp.BasePresenter;
import com.imuhao.pictureeveryday.ui.base.mvp.BaseView;
import java.util.List;

/**
 * @author Smile
 * @time 2017/4/26  下午2:35
 * @desc ${TODD}
 */
public interface ImageListContract {

  interface View extends BaseView {
    void showAllImage(List<ImageBean> results);

    void loadImageError(String error);
  }

  public abstract class Presenter extends BasePresenter<View> {
    public abstract void loadAllImage(int index);
  }
}
