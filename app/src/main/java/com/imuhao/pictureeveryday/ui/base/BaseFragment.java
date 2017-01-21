package com.imuhao.pictureeveryday.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * @author Smile
 * @time 2016/6/22  15:12
 * @desc ${TODD}
 */
public class BaseFragment extends Fragment {
    public Context mContext;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }
}
