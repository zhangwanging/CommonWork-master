package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.DimensChange;


/**
 * Created by Azusa on 2016/5/5.
 */
public class CircleProgressbar extends RelativeLayout {
    int backgroundColor = Color.parseColor("#3399FF");

    public CircleProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);

    }

    protected void setAttributes(AttributeSet attrs) {
        int minnum = getResources().getDimensionPixelOffset(R.dimen.item_width);
        setMinimumHeight(minnum);
        setMinimumWidth(minnum);

        setBackgroundColor(Color.parseColor("#3399FF"));

        setMinimumHeight(minnum);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAnimation(canvas);
        invalidate();
    }

    int arcD = 1;
    int arcO = 0;
    float degrees = 0;
    int limite = 0;

    private void drawAnimation(Canvas canvas) {
        if (arcO == limite)
            arcD += 7;
        if (arcD >= 290 || arcO > limite) {
            arcO += 7;
            arcD -= 7;
        }
        if (arcO > limite + 290) {
            limite = arcO;
            arcO = limite;
            arcD = 1;
        }
        degrees += 5;
        canvas.save();
        canvas.rotate(degrees, getWidth() / 2, getHeight() / 2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(backgroundColor);
        float width  = DimensChange.dip2px(getContext(),3);//圆弧的宽度
        paint.setStrokeWidth(width);
        canvas.drawArc(new RectF(width, width, getWidth() - width, getHeight() - width), arcO, arcD,
                false, paint);
        canvas.restore();
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(
                android.R.color.transparent));
        this.backgroundColor = color;
    }

}
