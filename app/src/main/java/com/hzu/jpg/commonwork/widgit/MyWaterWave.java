package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Azusa on 2016/9/17.
 */
public class MyWaterWave extends View {
    private NextFrameAction nextFrameAction;
    private Paint paint1, paint2;
    private Path path1, path2;
    private int width;
    private int w = 0;  //速度
    private int waveAmplitude;  //水纹高度
    private float defaultT = 1;  //波纹周期
    private int highLevel;
    private int defaultType = 0;  //0为底部水波纹，1为顶部水波纹
    private int defaultColor1 = 0xffffffff;
    private int defaultColor2 = 0x60508eff;

    public MyWaterWave(Context context) {
        super(context);
    }

    public MyWaterWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWaterWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        highLevel = (int) (getMeasuredHeight() * (0.5) + waveAmplitude);
        nextFrameAction = new NextFrameAction();
        width = getMeasuredWidth();
        paint1 = new Paint();
        paint2 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(defaultColor1);
        paint2.setAntiAlias(true);
        paint2.setColor(defaultColor2);

        path1 = new Path();
        path2 = new Path();
        waveAmplitude = 7;
    }

    /**
     * 设置为顶部水波纹
     *
     * @param hight 波纹高度
     */
    public void setTopType(int hight, float T) {
        defaultColor1 = 0x20ffffff;
        defaultColor2 = 0x30ffffff;
        defaultType = 1;
        defaultT = T;
        waveAmplitude = hight;
        highLevel = 100;
    }


    protected class NextFrameAction implements Runnable {

        @Override
        public void run() {
            path1.reset();
            path2.reset();
            w += 5;
            //将开始坐标移到左上角和左下角，先画左边的线
            path1.moveTo(0, getBottom());
            if (defaultType == 0) {
                path2.moveTo(0, 0);
            } else {
                path2.moveTo(0, getBottom());
            }
            for (int i = 0; i < width; i++) {
                float y = (float) (highLevel + waveAmplitude * Math.cos((float) (i + w) / (float) (width) * defaultT * Math.PI));
                path1.lineTo(i, y);
                float y2 = (float) (highLevel- waveAmplitude* Math.cos((float) (i + w) / (float) (width) * defaultT * Math.PI));
                path2.lineTo(i, y2);
            }

            //将开始坐标移到右上角和右下角，画右边的线，补全前面画不足的地方
            path1.moveTo(width, getBottom());
            if (defaultType == 0) {
                path2.moveTo(width, 0);
            } else {
                path2.moveTo(width, getBottom());
            }
            for (int i = 0; i < width; i++) {
                float y = (float) (highLevel + waveAmplitude * Math.cos((float) (i + w) / (float) (width) * defaultT * Math.PI));
                path1.lineTo(i, y);
                float y2 = (float) (highLevel- waveAmplitude * Math.cos((float) (i + w) / (float) (width) * defaultT * Math.PI));
                path2.lineTo(i, y2);
            }
            path1.close();
            path2.close();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint2);
        canvas.restore();
        postDelayed(nextFrameAction, 4);
    }
}