package com.imuhao.pictureeveryday.ui.mvp;

import com.imuhao.pictureeveryday.bean.HttpResult;
import com.imuhao.pictureeveryday.bean.ImageBean;
import com.imuhao.pictureeveryday.http.Retrofits;
import com.imuhao.pictureeveryday.http.SmileCallback;
import java.util.List;

/**
 * @author Smile
 * @time 2017/4/26  下午2:37
 * @desc ${TODD}
 */
public class ImageListPresenter extends ImageListContract.Presenter {
  private static final int PAGE_SIZE = 20;

  @Override public void loadAllImage(int index) {
    view.showWaitDialog();
    Retrofits.getApiService()
        .getImageList(PAGE_SIZE, index)
        .enqueue(new SmileCallback<HttpResult<List<ImageBean>>>() {
          @Override public void onSuccess(HttpResult<List<ImageBean>> data) {
            view.hideWaitDialog();
            view.showAllImage(data.getResults());
          }

          @Override public void onError(String error) {
            view.loadImageError(error);
            view.showToast("加载图片失败");
            view.hideWaitDialog();
          }
        });
  }
}
