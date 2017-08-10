package com.hzu.jpg.commonwork.widgit;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.DimensChange;

/**
 * Created by Administrator on 2017/3/28.
 */

public class MyEditLayout extends LinearLayout {

    int normalSize=16;
    EditText et;

    public MyEditLayout(Context context) {
        super(context);
    }

    public MyEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        setGravity(Gravity.CENTER_VERTICAL);
        int padding = DimensChange.dip2px(context, 12);
        setPadding(padding, padding, padding, padding);
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.colorWhite));

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyEditLayout);
        String title = typedArray.getString(R.styleable.MyEditLayout_my_info_title);
        String content=typedArray.getString(R.styleable.MyEditLayout_my_info_content);
        boolean numberType=typedArray.getBoolean(R.styleable.MyEditLayout_my_info_number_type,false);
        TextView tv=new TextView(context);
        LayoutParams lpTitle = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv.setTextSize(normalSize);
        tv.setText(title);
        addView(tv, lpTitle);

        et=new EditText(context);
        LayoutParams lpEdit = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        et.setTextSize(normalSize);
        et.setMaxLines(1);
        et.setText(content);
        et.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        if(numberType)
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
        lpEdit.width = 0;
        lpEdit.weight = 1;
        addView(et, lpEdit);

        typedArray.recycle();
    }

    public MyEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setText(String s){
        if(s==null){
            et.setText("");
            return ;
        }
        et.setText(s.trim());
    }

    public String getText(){
        return et.getText().toString().trim();
    }
}
