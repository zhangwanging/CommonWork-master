package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.holder.StatisticsAddRvHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Administrator on 2017/3/9.
 */

public class StatisticsAddAdapter extends RecyclerView.Adapter {

    LinkedHashMap<String,Boolean> mapNames;
    Context context;
    List<String> listNames;

    public StatisticsAddAdapter(Context context, LinkedHashMap<String,Boolean> mapNames) {
        this.context = context;
        this.mapNames = mapNames;
        listNames=new ArrayList<>(mapNames.keySet());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_statistics_add,null,false);
        RecyclerView.ViewHolder holder=new StatisticsAddRvHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("size",Integer.toString(getItemCount()));
        StatisticsAddRvHolder statisticsAddRvHolder_ = (StatisticsAddRvHolder) holder;
        CheckBox checkBox= statisticsAddRvHolder_.getCheckBox();
        String name=listNames.get(position);
        checkBox.setText(name);
        if (mapNames.get(name)){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String name=   buttonView.getText().toString();
                    mapNames.put(name,true);
                }else{
                    String name=   buttonView.getText().toString();
                    mapNames.put(name,false);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return mapNames.size();
    }
}
