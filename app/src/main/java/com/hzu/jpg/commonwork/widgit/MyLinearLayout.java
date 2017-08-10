package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.DimensChange;
import com.hzu.jpg.commonwork.utils.StringUtils;


/**
 * Created by Administrator on 2017/3/4.
 */

public class MyLinearLayout extends LinearLayout {

    String money = "";
    TextView tv_money;
    String title;
    int normalSize=16;
    String content="";
    TextView tv_title;


    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER_VERTICAL);
        int padding = DimensChange.dip2px(context, 15);
        setPadding(padding, padding, padding, padding);
        setOrientation(LinearLayout.HORIZONTAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyLinearLayout);
        title = typedArray.getString(R.styleable.MyLinearLayout_title);
        Drawable drawable = typedArray.getDrawable(R.styleable.MyLinearLayout_src);
        money = typedArray.getString(R.styleable.MyLinearLayout_money);
        boolean money_layout=typedArray.getBoolean(R.styleable.MyLinearLayout_money_layout,true);
        content =typedArray.getString(R.styleable.MyLinearLayout_content);

        tv_title = new TextView(context);
        LayoutParams lp_title = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_title.width = 0;
        lp_title.weight = 1;
        tv_title.setText(title);
        tv_title.setTextSize(normalSize);
        addView(tv_title, lp_title);

        if(money_layout) {
            tv_money = new TextView(context);
            if (money == null) {
                tv_money.setText("0 元");
            } else {
                tv_money.setText(money + " 元");
            }

            tv_money.setTextSize(normalSize);
            LayoutParams lp_money = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp_money.rightMargin = DimensChange.dip2px(context, 10);
            addView(tv_money, lp_money);
        }else if(content!=null&&!content.equals("")){
            tv_money=new TextView(context);
            tv_money.setText(content);

            tv_money.setTextSize(normalSize);
            LayoutParams lp_money = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp_money.rightMargin = DimensChange.dip2px(context, 10);
            addView(tv_money, lp_money);
        }

        ImageView iv = new ImageView(context);
        iv.setImageDrawable(drawable);
        LayoutParams lp_iv = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(iv, lp_iv);
        typedArray.recycle();
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMoney(String money) {
        this.money = money;
        tv_money.setText(money + " 元");

    }

    public void setContent(String content){
        this.content=content;
        if(StringUtils.isEmpty(content)){
            tv_money.setText("");
            return;
        }
        tv_money.setText(content);
    }

    public String getContent(){
        return  content;
    }

    public String getMoney(){
        return money;
    }

    public String getName(){
        return title;
    }

    public void setName(String title){
        this.title=title;
        tv_title.setText(title);
    }

}
