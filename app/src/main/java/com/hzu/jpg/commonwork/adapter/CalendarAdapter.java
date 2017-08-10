package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;


/**
 * Created by Administrator on 2017/3/25.
 */

public class CalendarAdapter extends RecyclerView.Adapter {

    int[] days;
    double[] hours;
    Context context;

    OnDayChangeListener listener;

    public CalendarAdapter(int[] days, double[] hours, Context context) {
        this.days = days;
        this.hours = hours;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(context).inflate(R.layout.item_calendar,parent,false);
        CalendarHolder holder=new CalendarHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CalendarHolder calendarHolder= (CalendarHolder) holder;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day=days[position];
                if(day<10){
                    listener.onDayChange("0"+Integer.toString(days[position]));
                }else{
                    listener.onDayChange(Integer.toString(days[position]));
                }
            }
        });
        calendarHolder.setData(days[position],hours[position]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }

    class CalendarHolder extends RecyclerView.ViewHolder{

        TextView tv_day;
        TextView tv_hours;

        public CalendarHolder(View itemView) {
            super(itemView);
            tv_day= (TextView) itemView.findViewById(R.id.tv_item_calendar_day);
            tv_hours= (TextView) itemView.findViewById(R.id.tv_item_calendar_hours);
        }

        public void setData(int day,double hours){
            if(day<=0){
                tv_day.setText("");
                return ;
            }
            tv_day.setText(day+"");
            if(hours<=0)
                tv_hours.setVisibility(View.INVISIBLE);
            else
                tv_hours.setVisibility(View.VISIBLE);
            tv_hours.setText(hours+"h");
        }
    }

    public void update(int[] days,double[] hours){
        this.days=days;
        this.hours=hours;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnDayChangeListener listener){
        this.listener=listener;
    }
    public interface OnDayChangeListener{
        void onDayChange(String day);
    }
}
