package com.hzu.jpg.commonwork.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;

/**
 * Created by cimcitech on 2017/6/5.
 */

public class MainGridAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private Integer[] Images = {
            R.mipmap.home_gridview_image_01,
            R.mipmap.home_gridview_image_02,
            R.mipmap.home_gridview_image_03,
            R.mipmap.home_gridview_image_04,
            R.mipmap.home_gridview_image_05,
            R.mipmap.home_gridview_image_06,
            R.mipmap.home_gridview_image_07,
            R.mipmap.home_gridview_image_08,
    };

    private String[] texts = {
            "假期工",
            "临时工",
            "兼职",
            "长期工",
            "企业招聘",
            "合作企业",
            "视频面试",
            "便民服务",
    };


    public String[] getAll() {
        return texts;
    }

    public MainGridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return Images.length;
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
        ImgTextWrapper wrapper;
        if (convertView == null) {
            wrapper = new ImgTextWrapper();
            convertView = inflater.inflate(R.layout.home_grid_item, null);
            wrapper.imageButton = (ImageView) convertView.findViewById(R.id.logoButton);
            wrapper.textView = (TextView) convertView.findViewById(R.id.tv_logo);
            convertView.setTag(wrapper);
            convertView.setPadding(5, 10, 5, 10);
        } else {
            wrapper = (ImgTextWrapper) convertView.getTag();
        }
        wrapper.imageButton.setBackgroundResource(Images[position]);
        wrapper.textView.setText(texts[position]);
        return convertView;
    }

    class ImgTextWrapper {
        ImageView imageButton;
        TextView textView;

    }
}
