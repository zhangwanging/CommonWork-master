package com.hzu.jpg.commonwork.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;


/**
 * Created by Administrator on 2017/3/12.
 */

public class DialogRvListAdapter extends RecyclerView.Adapter {

    String[] names;
    Context context;
    onItemClickListener listener;
    Dialog dialog;

    public DialogRvListAdapter(String[] names, Context context) {
        this.names=names;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_dialog_rv_add_record,parent,false);
        DialogRvHolder holder=new DialogRvHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String data=names[position];
        DialogRvHolder dHolder= (DialogRvHolder) holder;
        dHolder.tv.setText(data);
        dHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(names[position]);
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class DialogRvHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public DialogRvHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.tv_item_dialog_add_record);
        }
    }
    public void setOnItemClickListener(onItemClickListener listener, Dialog dialog){
        this.listener=listener;
        this.dialog=dialog;
    }

    public interface onItemClickListener{
        public void onItemClick(String data);
    }


}
