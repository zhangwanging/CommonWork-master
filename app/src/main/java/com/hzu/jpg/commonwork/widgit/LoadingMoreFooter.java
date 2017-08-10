package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;

import me.fangx.common.widget.loading.ProgressView;


/**
 * Created by Azusa on 2016/5/9.
 */
public class LoadingMoreFooter extends LinearLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    private ProgressView progressCon;
    private View view;


    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public void initView(){
        view = View.inflate(getContext(), R.layout.footer_lodingmore,null);
        mText = (TextView) view.findViewById(R.id.pull_to_refresh_loadmore_text);
//        progressCon = (CircleProgressbar) view.findViewById(R.id.pull_to_refresh_load_progress);
        progressCon = (ProgressView) view.findViewById(R.id.pull_to_refresh_load_progress);
        progressCon.setIndicatorColor(Color.BLUE);
        progressCon.setIndicatorId(ProgressView.BallClipRotateMultiple);
        addView(view);
        mText.setText("正在加载...");
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
                mText.setText("正在加载...");
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText("没有更多数据了");
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText("没有更多数据了");
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }

    }
}
