package com.ylian.common.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dafan on 2016/10/13 0013.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

	private int space;

	public SpacesItemDecoration(int space) {
		this.space = space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.left = space;
		outRect.right = space;
		outRect.bottom = space;
		outRect.top = space;
	}
}
