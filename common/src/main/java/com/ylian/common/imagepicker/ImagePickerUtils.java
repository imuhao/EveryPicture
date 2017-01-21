package com.ylian.common.imagepicker;

import android.content.Context;

import com.lzy.imagepicker.ImagePicker;
import com.ylian.common.utils.DensityUtil;

/**
 * Created by smile on 16-9-19.
 */
public class ImagePickerUtils {


    /**
     * 初始化公共的参数
     */
    public static void init() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoad());
        imagePicker.setShowCamera(true); //显示拍照按钮
    }

    /**
     * 初始化选择头像的图片加载器
     *
     * @param context
     */
    public static void initSelectHeadImg(Context context) {
        int width = DensityUtil.getDeviceWidth(context);
        int margin = DensityUtil.dp2px(context, 25);

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setCrop(true);        //允许裁剪
        imagePicker.setMultiMode(false);   //单选
        imagePicker.setFocusWidth(width - margin);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(width - margin);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
    }

    /**
     * 初始化多选图片
     */
    public static void initMoreImg() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(false);
        imagePicker.setShowCamera(false);
    }

    /**
     * 初始化多选图片
     */
    public static void initSingleImg() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
        imagePicker.setShowCamera(false);
    }

}

