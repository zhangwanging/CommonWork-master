package com.hzu.jpg.commonwork.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hzu.jpg.commonwork.enity.Comment;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */

public class CommentsLVAdapter extends BaseAdapter{

    private List<Comment> comments;

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
