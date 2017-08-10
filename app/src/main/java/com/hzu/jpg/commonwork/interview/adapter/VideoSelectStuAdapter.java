package com.hzu.jpg.commonwork.interview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.interview.bean.VideoStudentBean;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 2017/6/23.
 */

public class VideoSelectStuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<VideoStudentBean> list;

    public VideoSelectStuAdapter(Context context, ArrayList<VideoStudentBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.item_video_studentlist,viewGroup,false);
        TextView textView= (TextView) view.findViewById(R.id.tv_video_stu_name);
        TextView textView1= (TextView) view.findViewById(R.id.tv_video_online);
        textView.setText(list.get(i).getName());
        textView1.setText(list.get(i).getOnlineStatus());
        return view;
    }
}
