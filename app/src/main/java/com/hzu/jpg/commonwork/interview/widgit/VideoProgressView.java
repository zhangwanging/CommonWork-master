package com.hzu.jpg.commonwork.interview.widgit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.widgit.CircleProgressbar;

/**
 * Created by ThinkPad on 2017/5/9.
 */

public class VideoProgressView extends LinearLayout {
    private CircleProgressbar circleProgressbar;
    private TextView textView;
    public VideoProgressView(Context context) {
        super(context);
    }

    public VideoProgressView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        LayoutInflater.from(context).inflate(R.layout.dialog_progress,this,true);
        textView= (TextView)findViewById(R.id.tv_tip);
        textView.setTextColor(Color.WHITE);
        circleProgressbar= (CircleProgressbar)findViewById(R.id.pb_video);
    }

    public VideoProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String text){
        textView.setText(text);
        textView.invalidate();
    }

    public void setTextColor(int textColor){
        textView.setTextColor(textColor);
        textView.invalidate();
    }

    public void setVisible(int visible){
        if(visible==View.VISIBLE){
            textView.setVisibility(visible);
        }else if(visible==View.INVISIBLE){
            textView.setVisibility(visible);
        }
        circleProgressbar.setVisibility(View.GONE);
        textView.invalidate();
        circleProgressbar.invalidate();
    }

}
