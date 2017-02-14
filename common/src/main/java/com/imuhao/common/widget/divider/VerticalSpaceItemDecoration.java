package com.imuhao.common.widget.divider;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lv on 2016/9/12.
 * 功能描述：控制RecyclerView的divider的height
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
	private final int mVerticalSpaceHeight;

	public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
		this.mVerticalSpaceHeight = mVerticalSpaceHeight;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
	                           RecyclerView.State state) {
		outRect.bottom = mVerticalSpaceHeight;
		outRect.left = mVerticalSpaceHeight;
		outRect.right = mVerticalSpaceHeight;
		outRect.top = mVerticalSpaceHeight;
	}
}
