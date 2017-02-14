package com.imuhao.common.widget;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.imuhao.common.R;
import com.imuhao.common.interfaces.InputDialogListener;

public class Dialoghelper {

    /**
     * 生产一个点击外面和按返回键不可退出的{@link ProgressDialog}
     *
     * @param context 上下文对象
     * @param message 信息
     * @return
     */
    public static ProgressDialog getWaitDialogNotCannel(Context context, String message) {
        ProgressDialog waitDialog = getWaitDialog(context, message);
        waitDialog.setCanceledOnTouchOutside(false);
        waitDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return event.getKeyCode() == KeyEvent.KEYCODE_BACK;
            }
        });
        return waitDialog;
    }

    /***
     * 获取一个耗时等待对话框
     *
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    /***
     * 获取一个耗时等待对话框
     * 默认信息：精彩值得等待
     *
     * @param context
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context) {
        return getWaitDialog(context, "精彩值得等待");
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     *
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context,
                                                       String message,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    /**
     * 获取一个输入的对话框,需要自己手动调用show方法显示
     */
    public static AlertDialog.Builder getInputDialog(Context context, String title, String text, final InputDialogListener listener) {
        final AlertDialog.Builder builder = getDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_input_dialog, null);
        builder.setView(view);
        builder.setTitle(title);
        final EditText etInput = (EditText) view.findViewById(R.id.et_common_input);
        etInput.setText(String.valueOf(text));
        etInput.setSelection(etInput.getText().toString().length());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onInputClick(etInput.getText().toString());
            }
        });
        builder.setNegativeButton("取消", null);
        return builder;
    }


    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     *
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context,
                                                       String title,
                                                       String message,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    /***
     * 获取一个dialog
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       String positive,
                                                       DialogInterface.OnClickListener listener1,
                                                       String negative,
                                                       DialogInterface.OnClickListener listener2) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton(positive, listener1);
        builder.setNegativeButton(negative, listener2);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       String positive,
                                                       DialogInterface.OnClickListener listener1,
                                                       String negative,
                                                       DialogInterface.OnClickListener listener2,
                                                       String neutral,
                                                       DialogInterface.OnClickListener listener3) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(Html.fromHtml(message));
        builder.setPositiveButton(positive, listener1);
        builder.setNegativeButton(negative, listener2);
        builder.setNeutralButton(neutral, listener3);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context,
                                                       String message,
                                                       DialogInterface.OnClickListener onOkClickListener,
                                                       DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context,
                                                      String[] arrays,
                                                      DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    public static AlertDialog.Builder getSelectDialog(Context context,
                                                      String title,
                                                      String[] arrays,
                                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context,
                                                            String[] arrays,
                                                            int selectIndex,
                                                            DialogInterface.OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context,
                                                            String title,
                                                            String[] arrays,
                                                            int selectIndex,
                                                            DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setNegativeButton("取消", null);
        return builder;
    }

}

