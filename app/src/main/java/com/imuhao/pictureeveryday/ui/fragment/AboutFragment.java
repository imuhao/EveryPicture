package com.imuhao.pictureeveryday.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;

/**
 * @author Smile
 * @time 2016/6/22  13:32
 * @desc ${TODD}
 */
public class AboutFragment extends BaseFragment {
    private static AboutFragment instance;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, null);
        return view;
    }
    public static AboutFragment newInstance() {
        if (instance == null) {
            instance = new AboutFragment();
        }
        return instance;
    }
}
