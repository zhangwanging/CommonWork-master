package com.hzu.jpg.commonwork.widgit;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;


/**
 * Created by Delete_exe on 2016/5/6.
 */
public class TabItemView extends RelativeLayout {
    private TextView textView;
    private ImageView image;
    private int imgNormalId;
    private int imgPressId;
    private int textColor;
    private int textPressColor;

    public TabItemView(Context context, CharSequence text, int imgNormalId,int imgPressId,
                       int textColor, int textPressColor,int position) {
        super(context);
        View converView = View.inflate(context, R.layout.tabitemview, null);

        textView = (TextView) converView.findViewById(R.id.tab_tx);
        image = (ImageView) converView.findViewById(R.id.img_tab);
        textView.setText(text);

        if(position == 0){
            textView.setTextColor(textPressColor);
            image.setBackgroundResource(imgPressId);
        }else{
            textView.setTextColor(textColor);
            image.setBackgroundResource(imgNormalId);
        }
        this.imgNormalId = imgNormalId;
        this.imgPressId = imgPressId;
        this.textColor = textColor;
        this.textPressColor = textPressColor;
        addView(converView);
    }

    public void toggle(boolean isToSelect) {
        if (isToSelect) {
            image.setBackgroundResource(imgPressId);
            textView.setTextColor(textPressColor);
        } else {
           image.setBackgroundResource(imgNormalId);
            textView.setTextColor(textColor);
        }
    }


}
