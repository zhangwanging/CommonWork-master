package com.hzu.jpg.commonwork.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.hzu.jpg.commonwork.R;


/**
 * Created by Administrator on 2017/3/9.
 */

public class StatisticsAddRvHolder extends RecyclerView.ViewHolder{

    CheckBox checkBox;

    public StatisticsAddRvHolder(View itemView) {
        super(itemView);
        checkBox= (CheckBox) itemView.findViewById(R.id.cb_item_statistics_add);
    }

    public CheckBox getCheckBox(){
        return checkBox;
    }
}
