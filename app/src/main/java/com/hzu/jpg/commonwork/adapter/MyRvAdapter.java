package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzu.jpg.commonwork.holder.MyRvHolder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MyRvAdapter extends RecyclerView.Adapter {

    public List<? extends Object> list;
    private Context context;
    private int layoutId;
    private Class<? extends MyRvHolder> clazz;
    private OnRvClickListener listener;

    public MyRvAdapter(int layoutId, Class<? extends MyRvHolder> clazz, Context context, List<? extends Object> list) {
        this.layoutId = layoutId;
        this.clazz = clazz;
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        MyRvHolder holder=null;
        try {
            Constructor<? extends MyRvHolder> constructor=clazz.getDeclaredConstructor(View.class);
            holder=constructor.newInstance(itemView);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyRvHolder mHolder= (MyRvHolder) holder;
        final int mPosition=position;
        if(listener!=null){
            mHolder.setOnClick(listener,mHolder.getOnClickId(list.get(mPosition)),position);
        }
        mHolder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        if(list==null)return 0;
        return list.size();
    }

    public void setListener(OnRvClickListener listener) {
        this.listener = listener;
    }

    public interface OnRvClickListener{
        void onRvClick(Object obj, int position);
    }

    public void update(List<? extends Object> list){
        if(list!=null)
            this.list=list;
        notifyDataSetChanged();
    }
}
