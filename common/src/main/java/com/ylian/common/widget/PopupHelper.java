package com.ylian.common.widget;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by dafan on 2015/11/19 0019.
 */
public class PopupHelper {
	/**
	 * <p>创建一个PopupWindow</p>
	 * <p>自带进入退出动画</p>
	 * <p>按返回键和空白区域退出</p>
	 * <p>背景为透明</p>
	 * <p>自适应软键盘高度</p>
	 * <p>宽度是全屏，高度是包含内容</p>
	 *
	 * @param view 引入的Layout布局
	 * @return
	 */
	public static final PopupWindow createPop(final View view, int w, int h) {
		PopupWindow pop = new PopupWindow(view, w, h);
		// 设置进入和退出的动画
		/*pop.setAnimationStyle(R.style.ViewEnterAndExitAnim);*/
		// 以下三行是必须要设置的-->点击对外消失
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		// 下面两行-->对软键盘弹出时，Pop上移位置
		pop.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		// 退出时恢复屏幕亮度
		/*pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha((Activity) view.getContext(), 1f);
			}
		});*/

		return pop;
	}

	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha
	 */
	public static void backgroundAlpha(Activity activity, float bgAlpha) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		activity.getWindow().setAttributes(lp);
	}
}
