package com.imuhao.pictureeveryday.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.widget.ImageView;

/**
 * @author Smile
 * @time 2017/2/14  下午4:25
 * @desc ${TODD}
 */
public class ImageUtils {

  /**
   * 设置圆形Bitmap
   */
  public static void setCircleUtils(ImageView imageView,Bitmap src) {
    int radius = src.getWidth() / 2;
    BitmapShader bitmapShader =
        new BitmapShader(src, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
    Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(dest);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(bitmapShader);
    c.drawCircle(radius, radius, radius, paint);
    imageView.setImageBitmap(dest);
  }
}
