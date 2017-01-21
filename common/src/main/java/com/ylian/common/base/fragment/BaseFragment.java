package com.ylian.common.base.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylian.common.R;
import com.ylian.common.base.activity.BaseActivity;
import com.ylian.common.base.mvp.BaseView;
import com.ylian.common.glide.GlideUtils;
import com.ylian.common.swipeback.SwipeBackFragment;
import com.ylian.common.utils.ToastUtils;
import com.ylian.common.widget.Dialoghelper;

/**
 * Created by lw on 2016/9/8.
 * 基类Fragment
 */
public abstract class BaseFragment extends SwipeBackFragment implements BaseView {
    protected BaseActivity mActivity;
    /**
     *
     */
    private View mRootView;
    /**
     * 子类fragment内容加载区域
     */
    private View mContentLayoutView;
    /**
     * 网络错误提示显示界面
     */
    private View mLayoutError;
    private ViewStub mVsLayoutError;
    private LinearLayout mBaseView;

    /**
     * 头布局
     */
    private View mHeaderView;
    /**
     *
     */
    private TextView mTvRight;
    /**
     * 不设置Header
     */
    protected final int HEADER_NONE = 0;
    /**
     * 默认Header
     */
    protected final int HEADER_DEFAULT = 1;
    /**
     * 只有title的header
     */
    protected final int HEADER_ONLY_TITLE = 2;
    /**
     * 设置自定义Header
     */
    protected final int HEADER_CUSTOM = 3;
    /**
     * 选择的Header类型
     */
    private int mHeaderType = HEADER_DEFAULT;

    protected ProgressDialog mWaitDialog;

    protected OnAddFragmentListener mAddFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
        if (context instanceof OnAddFragmentListener) {
            mAddFragmentListener = (OnAddFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_base, container, false);

            mContentLayoutView = inflater.inflate(getLayoutId(), container, false);
            mVsLayoutError = (ViewStub) mRootView.findViewById(R.id.vs_network_erro);
            mBaseView = (LinearLayout) mRootView.findViewById(R.id.activity_container);

            setHeaderType(HEADER_DEFAULT);
            addHeaderView(mRootView);
            addContentView();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return attachToSwipeBack(mRootView);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleData(savedInstanceState);
        initView(view, savedInstanceState);
    }

    /**
     * 获取布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected void handleData(@Nullable Bundle savedInstanceState) {

    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 给Header设置标题
     * 注意：Header的xml文件中title控件的id必须要一致，否则会找不到控件
     *
     * @param title
     */
    protected void setHeaderTitle(String title) {
        if (null == mHeaderView)
            return;
        TextView tvHeaderTitle = (TextView) mHeaderView.findViewById(R.id.tv_header_title);
        if (null != tvHeaderTitle)
            tvHeaderTitle.setText(title);
    }

    /**
     * 添加标题头
     */
    private void addHeaderView(View rootLayoutView) {
        // 不是用头部
        if (mHeaderType == HEADER_NONE) {
        }

        // 默认标题
        else if (mHeaderType == HEADER_DEFAULT) {
            mHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.layout_header_default, mBaseView, false);
            mTvRight = (TextView) mHeaderView.findViewById(R.id.tv_header_right);
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHeaderRightTextViewClick();
                }
            });
        }

        // 单独标题
        else if (mHeaderType == HEADER_ONLY_TITLE) {
            mHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.layout_header_only_title, mBaseView, false);

            mTvRight = (TextView) mHeaderView.findViewById(R.id.tv_header_right);
            mTvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHeaderRightTextViewClick();
                }
            });
        }

        // 使用自定义的
        else if (mHeaderType == HEADER_CUSTOM) {
            int custom = getCustomHeaderLayoutId();
            if (custom == -1) return;
            mHeaderView = LayoutInflater.from(mActivity).inflate(custom, mBaseView, false);
        }

        // 添加头布局
        if (null != mHeaderView) {
            mBaseView.addView(mHeaderView, 0);
            onHeaderBackClick(rootLayoutView);
        }
    }

    /**
     * 添加内容布局
     */
    private void addContentView() {
        mBaseView.addView(mContentLayoutView);
    }

    /**
     * 初始化返回按钮
     */
    private void onHeaderBackClick(View rootLayoutView) {
        if (HEADER_DEFAULT != mHeaderType)
            return;

        ImageView ivBack = (ImageView) rootLayoutView.findViewById(R.id.iv_header_back);
        if (null != ivBack) {
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mActivity instanceof BaseActivity) {
                        onHeaderBackPressed();
                    }
                }

            });
        }
    }

    /**
     */
    protected void setHeaderBackVisibility(int visibility) {
        if (mHeaderView == null)
            return;
        ImageView ivBack = (ImageView) mHeaderView.findViewById(R.id.iv_header_back);
        if (ivBack == null)
            return;
        ivBack.setVisibility(visibility);
    }

    /**
     * 设置默认头布局右边TextView的文字
     *
     * @param text
     */
    protected void setHeaderRightText(String text) {
        if (mHeaderType == HEADER_DEFAULT || mHeaderType == HEADER_ONLY_TITLE)
            mTvRight.setText(text);
    }

    protected void onHeaderRightTextViewClick() {

    }

    /**
     * header头部的返回键监听时间
     */
    protected void onHeaderBackPressed() {
        mActivity.onActivityBackPressed();
    }

    /**
     * 在子类的onCreate调用该方法需要在super.onCreate(savedInstanceState);方法之前调用
     *
     * @param type
     */
    protected void setHeaderType(int type) {
        mHeaderType = type;
    }

    /**
     * 获取自定义的标题头布局文件ID
     *
     * @return
     */
    protected int getCustomHeaderLayoutId() {
        return -1;
    }


    public interface OnAddFragmentListener {
        void onAddFragment(Fragment toFragment);
    }

    /**
     * 显示错误界面
     */
    public void showErrorView(String errMsg) {
        ToastUtils.showToastShort(mActivity, errMsg);
        if (null == mLayoutError) {
            mLayoutError = mVsLayoutError.inflate();
            mLayoutError.findViewById(R.id.btn_click_retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onErrorViewClick();
                }
            });
        } else {
            mLayoutError.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 网络重连方法
     */
    protected void onErrorViewClick() {
        mLayoutError.setVisibility(View.GONE);
    }

    /**
     * @return
     */
    public View getHeaderView() {
        return mHeaderView;
    }

    /**
     *
     */
    public void showWaitDialog() {
        if (mWaitDialog == null) {
            mWaitDialog = Dialoghelper.getWaitDialog(mActivity);
            mWaitDialog.setMessage("精彩值得等待……");
        }
        mWaitDialog.show();
    }

    /**
     *
     */
    public void hideWaitDialog() {
        if (mWaitDialog != null && mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
        }
    }

    /**
     * @param msg
     */
    public void showToast(String msg) {
        if (mActivity != null) {
            ToastUtils.showToastShort(mActivity, msg);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        GlideUtils.clearImageMemoryCache();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAddFragmentListener = null;
    }
}
