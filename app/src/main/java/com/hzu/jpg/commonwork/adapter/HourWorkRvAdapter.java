package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.HourWork.Bean.HourWorkRecordBean;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhutao on 2017/8/13.
 */

public class HourWorkRvAdapter extends BaseAdapter {

    List<HourWorkRecordBean> data;
    private LayoutInflater inflater;
    private MyClickListener mListener;

    public HourWorkRvAdapter(Context context, List<HourWorkRecordBean> data, MyClickListener mListener) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HourWorkRecordBean item = data.get(position);
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = inflater.inflate(R.layout.item_rv_hour_work, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvDate.setText(item.getDate_ymd().substring(5));
        viewHolder.mTvWeek.setText(item.getWeek());
        int hours = item.getHours();
        int minutes = item.getMinutes();
        viewHolder.mTvOtHours.setText(Double.toString(TimeUtil.Time2Double(hours, minutes)));
        viewHolder.mTvOtSalary.setText(Double.toString(item.getSalary()));
        viewHolder.mItemView.setOnClickListener(mListener);
        viewHolder.mItemView.setTag(position);
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.tv_item_hour_record_date)
        TextView mTvDate;
        @Bind(R.id.tv_item_hour_record_week)
        TextView mTvWeek;
        @Bind(R.id.tv_item_hour_record_ot_hours)
        TextView mTvOtHours;
        @Bind(R.id.tv_item_hour_record_ot_salary)
        TextView mTvOtSalary;
        @Bind(R.id.item_view)
        LinearLayout mItemView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 用于回调的抽象类
     */
    public static abstract class MyClickListener implements View.OnClickListener {
        /**
         * 基类的onClick方法
         */
        @Override
        public void onClick(View v) {
            myOnClick((Integer) v.getTag(), v);
        }

        public abstract void myOnClick(int position, View v);
    }
}
