package com.hzu.jpg.commonwork.utils;

import android.content.Context;

/**
 * Created by Administrator on 2017/3/4.
 */

public class DimensChange {

    public static int dip2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    public static float sp2px(Context context,float spValue){
        float scale=context.getResources().getDisplayMetrics().scaledDensity;
        return spValue*scale+0.5f;
    }
}
