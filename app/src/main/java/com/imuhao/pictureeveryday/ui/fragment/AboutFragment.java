package com.imuhao.pictureeveryday.ui.fragment;

import android.view.View;

import com.imuhao.pictureeveryday.R;
import com.imuhao.pictureeveryday.ui.base.BaseFragment;
import com.imuhao.pictureeveryday.utils.ThemeUtils;

/**
 * @author Smile
 * @time 16/05/2017  16:42
 * @desc ${TODD}
 */
public class AboutFragment extends BaseFragment {

    private View bgView;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView(View view) {
        bgView = view.findViewById(R.id.bg_view);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            int themeColor = ThemeUtils.getThemeColor();
            bgView.setBackgroundColor(themeColor);
        }
    }
}
