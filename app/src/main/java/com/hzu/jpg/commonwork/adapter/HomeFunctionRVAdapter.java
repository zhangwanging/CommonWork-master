package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AdviceActivity;
import com.hzu.jpg.commonwork.activity.ApplyJobActivity;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.activity.RecruitmentActivity;
import com.hzu.jpg.commonwork.activity.service.ServiceActivity;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/1/16.
 */

public class HomeFunctionRVAdapter extends RecyclerView.Adapter<HomeFunctionRVAdapter.ViewHolder> {
    private static final String TAG = "HomeFunctionRVAdapter";
    private Context context;
    private String[] titles;
    private List<Integer> iconRes;


    public HomeFunctionRVAdapter(Context context, String[] titles, List<Integer> iconRes) {
        this.context = context;
        this.titles = titles;
        this.iconRes = iconRes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_home_function, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(titles[position]);
        holder.imageView.setImageResource(iconRes.get(position));
        switch (position) {
            case 0:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ApplyJobActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.user == null) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        } else if (MyApplication.role == 0) {
                            ToastUtil.showToast("求职者无法发布招聘信息");
                        } else {
                            Intent intent = new Intent(context, RecruitmentActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
            case 2:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ServiceActivity.class));
                    }
                });
                break;
            case 3:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, AdviceActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case 4:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("功能即将开发，敬请期待。");
                    }
                });
                break;
            case 5:
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.showToast("功能即将开发，敬请期待。");
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.icon_function);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
