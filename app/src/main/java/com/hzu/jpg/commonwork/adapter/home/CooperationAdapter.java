package com.hzu.jpg.commonwork.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.moudle.Enterprise;
import com.hzu.jpg.commonwork.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhutao on 2017/8/8.
 */

public class CooperationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private ArrayList<Enterprise.Data> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;
    private Context context;

    public CooperationAdapter(Context context, ArrayList<Enterprise.Data> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void setNotMoreData(boolean b) {
        this.isNotMoreData = b;
    }

    public boolean isNotMoreData() {
        return isNotMoreData;
    }

    public ArrayList<Enterprise.Data> getAllData() {
        return data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClickListener(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.cooperation_item_view, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.recycler_foot_view, parent, false);
            return new FootViewHolder(view);
        } else if (viewType == TYPE_END) {
            View view = inflater.inflate(R.layout.recycler_end_view, parent, false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                        return false;
                    }
                });
            }
            Enterprise.Data esd = data.get(position);
            ((ItemViewHolder) holder).mTvName.setText(esd.getName());
            ((ItemViewHolder) holder).mTvAddress.setText(esd.getProvince() + esd.getCity() + esd.getRegion() + esd.getDetails());
            ((ItemViewHolder) holder).mTvRegion.setText(esd.getCity() + ("".equals(esd.getCity()) ? "" : "-") + esd.getRegion());
            for (int i = 0; i < 3; i++) {
                ((ItemViewHolder) holder).mTvLabel[i].setText("");
                ((ItemViewHolder) holder).mTvLabel[i].setVisibility(View.INVISIBLE);
            }
            if (!"".equals(esd.getLabel())) {
                String arr[];
                int length;
                if (esd.getLabel().contains("、")) {
                    arr = esd.getLabel().split("、");
                    length = arr.length > 3 ? 3 : arr.length;
                    for (int i = 0; i < length; i++) {
                        ((ItemViewHolder) holder).mTvLabel[i].setText(arr[i]);
                        ((ItemViewHolder) holder).mTvLabel[i].setVisibility(View.VISIBLE);
                    }
                } else if (esd.getLabel().contains("/")) {
                    arr = esd.getLabel().split("/");
                    length = arr.length > 3 ? 3 : arr.length;
                    for (int i = 0; i < length; i++) {
                        ((ItemViewHolder) holder).mTvLabel[i].setText(arr[i]);
                        ((ItemViewHolder) holder).mTvLabel[i].setVisibility(View.VISIBLE);
                    }
                } else {
                    ((ItemViewHolder) holder).mTvLabel[0].setText(esd.getLabel());
                    ((ItemViewHolder) holder).mTvLabel[0].setVisibility(View.VISIBLE);
                }
            }
            Glide.with(context.getApplicationContext())
                    .load(Constants.imageUrl + esd.getIcno())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.image_bg_default)
                    .error(R.mipmap.image_bg_default).into(((ItemViewHolder) holder).iv_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            if (isNotMoreData())
                return TYPE_END;
            else
                return TYPE_FOOTER;
        } else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTvName, mTvAddress ,mTvRegion;
        ImageView iv_image;
        TextView[] mTvLabel = new TextView[3];


        public ItemViewHolder(View view) {
            super(view);
            mTvName = (TextView) view.findViewById(R.id.tv_name);
            mTvAddress = (TextView) view.findViewById(R.id.tv_address);
            mTvRegion = (TextView) view.findViewById(R.id.tv_region);
            mTvLabel[0] = (TextView) view.findViewById(R.id.tv_label_1);
            mTvLabel[1] = (TextView) view.findViewById(R.id.tv_label_2);
            mTvLabel[2] = (TextView) view.findViewById(R.id.tv_label_3);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
