/*
 * Copyright 2015 XiNGRZ <chenxingyu92@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ylian.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 一个能保持比例的 ImageView
 * TODO: 暂时只支持维持宽度适应高度
 *
 * @author XiNGRZ
 */
public class RatioImageView extends ImageView {

	private int originalWidth;
	private int originalHeight;
	private float ratio;

	public RatioImageView(Context context) {
		super(context);
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setOriginalSize(int ow, int oh) {
		if (ow > 0 && oh > 0) {
			this.originalWidth = ow;
			this.originalHeight = oh;
			this.ratio = (float) this.originalWidth / (float) this.originalHeight;
		}
	}

	public void setRatio(float ratio) {
		if (ratio > 0)
			this.ratio = ratio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (ratio > 0) {
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = MeasureSpec.getSize(heightMeasureSpec);

			if (width > 0) {
				height = (int) ((float) width / ratio);
			}

			setMeasuredDimension(width, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
