package com.hzu.jpg.commonwork.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by zhutao on 2017/8/10.
 */

public class URLDrawable extends BitmapDrawable {
    protected Bitmap bitmap;

    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, getPaint());
        }
    }
}
