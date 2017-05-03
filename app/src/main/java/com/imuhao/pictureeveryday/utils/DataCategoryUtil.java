package com.imuhao.pictureeveryday.utils;

import com.imuhao.pictureeveryday.bean.GankBean;
import com.imuhao.pictureeveryday.bean.ItemBean;
import com.imuhao.pictureeveryday.bean.ItemViewBean;
import java.util.List;

/**
 * @author Smile
 * @time 2017/5/3  下午4:40
 * @desc ${TODD}
 */
public class DataCategoryUtil {
  private static final int TITLE = 0X001;
  private static final int CATEGORY = 0x002;
  private static final int IMAGE = 0x003;

  public static void category(List<ItemViewBean> data, GankBean gankBean) {
    data.clear();
    GankBean.Result results = gankBean.results;

    if (results.妹纸List != null && !results.妹纸List.isEmpty()) {
      ItemBean itemBean = results.妹纸List.get(0);
      data.add(new ItemViewBean(IMAGE, "", itemBean.getUrl()));
    }
    //Android
    if (results.androidList != null && !results.androidList.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "Android", ""));
      for (ItemBean itemBean : results.androidList) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }

    if (results.iOSList != null && !results.iOSList.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "iOS", ""));
      for (ItemBean itemBean : results.iOSList) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }

    if (results.拓展资源List != null && !results.拓展资源List.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "拓展资料", ""));
      for (ItemBean itemBean : results.拓展资源List) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }

    if (results.瞎推荐List != null && !results.瞎推荐List.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "瞎推荐", ""));
      for (ItemBean itemBean : results.瞎推荐List) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }

    if (results.appList != null && !results.appList.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "App", ""));
      for (ItemBean itemBean : results.appList) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }

    if (results.休息视频List != null && !results.休息视频List.isEmpty()) {
      data.add(new ItemViewBean(CATEGORY, "休息视频", ""));
      for (ItemBean itemBean : results.休息视频List) {
        data.add(new ItemViewBean(TITLE, itemBean.getDesc(), itemBean.getUrl()));
      }
    }
  }
}
