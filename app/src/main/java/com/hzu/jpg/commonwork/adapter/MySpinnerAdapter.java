package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */

public class MySpinnerAdapter<T> extends ArrayAdapter<T> {

    public MySpinnerAdapter(Context context, int resource, int textViewResourceId, List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public MySpinnerAdapter(Context context, int resource, int textViewResourceId, T[] t) {
        super(context, resource, textViewResourceId, t);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return setCentered(super.getView(position, convertView, parent));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return setCentered(super.getDropDownView(position, convertView, parent));
    }

    private View setCentered(View view)
    {
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setGravity(Gravity.CENTER);
        return view;
    }
}
