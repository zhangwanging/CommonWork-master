package com.hzu.jpg.commonwork.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hzu.jpg.commonwork.adapter.MyRvAdapter;


/**
 * Created by Administrator on 2017/3/31.
 */

public abstract class MyRvHolder extends RecyclerView.ViewHolder{

    public MyRvHolder(View itemView) {
        super(itemView);
    }

    public abstract void setData(Object obj);

    public abstract void setOnClick(MyRvAdapter.OnRvClickListener listener, Object obj, int position);

    public abstract Object getOnClickId(Object obj);


}
