package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.DimensChange;


/**
 * Created by Administrator on 2017/4/2.
 */

public class MyFlowLayout extends ViewGroup {


    public MyFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public MyFlowLayout(Context context) {
        super(context);
    }
    @Override
    protected void onLayout(boolean changed, final int l, final int t, int r, int b) {
        int left=l;
        int top=0;

        int remainWidth=r-l;

        int maxTopMargin=0;
        int maxBottomMargin=0;

        for (int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=lp.leftMargin+child.getMeasuredWidth()+lp.rightMargin;
            if(remainWidth<childWidth&&l!=left){
                left=l;
                top+=maxTopMargin+maxBottomMargin+child.getMeasuredHeight();
                remainWidth=r-l;
            }
            left+=lp.leftMargin;
            child.layout(left,top+lp.topMargin,left+child.getMeasuredWidth(),top+lp.topMargin+child.getMeasuredHeight());
            left+=child.getMeasuredWidth()+lp.rightMargin;
            remainWidth-=childWidth;

            if(lp.topMargin>maxTopMargin){
                maxTopMargin=lp.topMargin;
            }
            if(lp.bottomMargin>maxBottomMargin){
                maxBottomMargin=lp.bottomMargin;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int height=0;
        final int width=MeasureSpec.getSize(widthMeasureSpec);
        int remainWidth=width;

        int maxTopMargin=0;
        int maxBottomMargin=0;

        for(int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams lp= (MarginLayoutParams) child.getLayoutParams();
            int childWidth=child.getMeasuredWidth()+lp.rightMargin+lp.leftMargin;
            if(remainWidth<childWidth){
                if(remainWidth!=width){
                    height+=lp.topMargin+lp.bottomMargin+child.getMeasuredHeight();
                    maxTopMargin=lp.topMargin;
                    maxBottomMargin=lp.bottomMargin;
                    remainWidth=width;
                }
                continue;
            }else{
                remainWidth-=childWidth;
            }
            if(height==0){
                height+=child.getMeasuredHeight();
            }
            if(lp.topMargin>maxTopMargin){
                height=height-maxTopMargin+lp.topMargin;
                maxTopMargin=lp.topMargin;
            }
            if(lp.bottomMargin>maxBottomMargin){
                height=height-maxBottomMargin+lp.bottomMargin;
                maxBottomMargin=lp.bottomMargin;
            }
        }
        setMeasuredDimension(width, height);
    }


    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void addView(String text){
        TextView tv=new TextView(getContext());
        tv.setText(text);
        tv.setTextSize(14);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv.setBackgroundResource(R.drawable.empty_corner);
        MarginLayoutParams lp =new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        int fivePx= DimensChange.dip2px(getContext(),4);
        lp.bottomMargin=fivePx;
        lp.topMargin=fivePx;
        lp.leftMargin=fivePx;
        lp.rightMargin=fivePx;
        int sevenPx=DimensChange.dip2px(getContext(),7);
        tv.setPadding(sevenPx,fivePx,sevenPx,fivePx);
        tv.setLayoutParams(lp);
        addView(tv);
    }

}
