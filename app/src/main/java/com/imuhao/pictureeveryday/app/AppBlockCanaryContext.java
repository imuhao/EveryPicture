package com.imuhao.pictureeveryday.app;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * @author Smile
 * @time 2017/5/5  上午9:03
 * @desc ${TODD}
 */
public class AppBlockCanaryContext extends BlockCanaryContext {

  @Override public String provideQualifier() {
    return "smile";
  }

  @Override public String provideUid() {
    return "702068049";
  }

  @Override public String provideNetworkType() {
    return "wifi";
  }

  @Override public int provideMonitorDuration() {
    return 500;
  }

  @Override public int provideBlockThreshold() {
    return 500;
  }
}
