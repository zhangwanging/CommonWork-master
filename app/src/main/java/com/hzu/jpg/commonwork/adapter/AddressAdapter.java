package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.Address;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhutao on 2017/8/2 0002.
 */

public class AddressAdapter extends BaseAdapter {

    private List<Address> data;
    private LayoutInflater inflater;

    public AddressAdapter(Context context, List<Address> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public List<Address> getAll() {
        return data;
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
        Address item = data.get(position);
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = inflater.inflate(R.layout.my_address_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvAddress.setText(item.getAddress());
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.tv_address)
        TextView mTvAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
