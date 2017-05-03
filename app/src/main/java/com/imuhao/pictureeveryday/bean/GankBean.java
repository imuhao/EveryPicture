package com.imuhao.pictureeveryday.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author Smile
 * @time 2017/5/3  下午3:27
 * @desc ${TODD}
 */
public class GankBean {

  public boolean error;
  public Result results;
  public List<String> category;

  public static class Result {
    @SerializedName("Android") public List<ItemBean> androidList;
    @SerializedName("休息视频") public List<ItemBean> 休息视频List;
    @SerializedName("iOS") public List<ItemBean> iOSList;
    @SerializedName("福利") public List<ItemBean> 妹纸List;
    @SerializedName("拓展资源") public List<ItemBean> 拓展资源List;
    @SerializedName("瞎推荐") public List<ItemBean> 瞎推荐List;
    @SerializedName("App") public List<ItemBean> appList;
  }
}
