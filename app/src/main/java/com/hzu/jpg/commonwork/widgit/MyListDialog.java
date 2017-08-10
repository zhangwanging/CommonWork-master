package com.hzu.jpg.commonwork.widgit;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.DialogRvListAdapter;


/**
 * Created by Administrator on 2017/3/12.
 */

public class MyListDialog extends Dialog {

    public MyListDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public MyListDialog(Context context, int themeResId) {
        super(context,R.style.MyDialog);
    }

    public static class Builder{
        String[] names;
        String title;
        View contentView;
        Context context;
        DialogRvListAdapter adapter;
        Dialog dialog;
        int width;
        int height;


        public Builder(Context context){
            this.context=context;
        }

        public void setNames(String[] names) {
            this.names = names;
        }

        public void setTitle(String title){
            this.title=title;
        }

        public void setDisplay(Display display){
            width=((int) (display.getWidth()*0.9));
            height=((int) (display.getHeight()*0.5));
        }
        public void create(){
            dialog=new MyListDialog(context);
            contentView= LayoutInflater.from(context).inflate(R.layout.dialog_list,null);
            RecyclerView rv= (RecyclerView) contentView.findViewById(R.id.rv_dialog_add_record);
            TextView tv= (TextView) contentView.findViewById(R.id.tv_add_record_dialog_list_title);
            adapter=new DialogRvListAdapter(names,context);
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(context));
            tv.setText(title);
            Window window=dialog.getWindow();
            window.setContentView(contentView);
            dialog.show();
            window.setLayout(width,height);
        }
        public void setOnItemClickListener(DialogRvListAdapter.onItemClickListener listener){
            adapter.setOnItemClickListener(listener,dialog);
        }
    }

}
