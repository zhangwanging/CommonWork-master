package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.holder.StatisticsLvHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StatisticsLvAdapter extends BaseAdapter{

    Context context;
    List<String> listNames;
    List<Double> listValues;

    public StatisticsLvAdapter(Context context, Map<String, Double> map) {
        this.context = context;
        listNames=new ArrayList<>(map.keySet());
        listValues=new ArrayList<>(map.values());
    }

    @Override
    public int getCount() {
        return listNames.size();
    }

    @Override
    public Object getItem(final int position) {

        return listNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StatisticsLvHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_statistics,null,false);
            TextView tv_title= (TextView) convertView.findViewById(R.id.tv_item_statistics_title);
            TextView tv_money= (TextView) convertView.findViewById(R.id.tv_item_statistics_money);
            holder=new StatisticsLvHolder();
            holder.setTv_title(tv_title);
            holder.setTv_money(tv_money);
            convertView.setTag(holder);
        }
        holder= (StatisticsLvHolder) convertView.getTag();
        holder.getTv_title().setText(listNames.get(position));
        double money=listValues.get(position);
        if(money!=-1){
            holder.getTv_money().setText(listValues.get(position).toString());
        }else{
            holder.getTv_money().setText("0.0");
        }
        return convertView;
    }
    public void update(Map<String,Double> map){
        listNames.clear();
        listNames.addAll(map.keySet());
        listValues.clear();
        listValues.addAll(map.values());
        notifyDataSetChanged();
    }

    public double update(int position,double d){
        double oldValue=listValues.get(position);
        listValues.set(position,d);
        notifyDataSetChanged();
        return oldValue;
    }
}
