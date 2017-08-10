package com.hzu.jpg.commonwork.adapter.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.goods.GoodsAdapter;
import com.hzu.jpg.commonwork.enity.service.NewsVo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cimcitech on 2017/5/31.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<NewsVo.Data> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Context context;

    public NewsAdapter(Context context, List<NewsVo.Data> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
        options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.image_bg_default)
                .showImageForEmptyUri(R.mipmap.image_bg_default).showImageOnFail(R.mipmap.image_bg_default)
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .build();
    }

    public void setNotMoreData(boolean b) {
        this.isNotMoreData = b;
    }

    public boolean isNotMoreData() {
        return isNotMoreData;
    }

    public List<NewsVo.Data> getAll() {
        return data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClickListener(View view, int position);
    }

    private GoodsAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(GoodsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.new_item_view, parent, false);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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
            final NewsVo.Data detail = data.get(position);
            ((ItemViewHolder) holder).title.setText(detail.getTitle());
            ((ItemViewHolder) holder).newsType.setText(detail.getNewsType());
            ((ItemViewHolder) holder).newsDate.setText(detail.getNewsDate());
            /*imageLoader.displayImage(Constants.imageUrl +
                    detail.getImgs(), ((ItemViewHolder) holder).mImgNews, options);*/

            Glide.with(context.getApplicationContext())
                    .load(detail.getImgs())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.image_bg_default)
                    .error(R.mipmap.image_bg_default).into(((ItemViewHolder) holder).mImgNews);
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

    public int getTypeItem(int position) {
        return super.getItemViewType(position);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView title, newsDate, newsType;
        ImageView mImgNews;


        public ItemViewHolder(View view) {
            super(view);
            newsDate = (TextView) view.findViewById(R.id.newsDate);
            newsType = (TextView) view.findViewById(R.id.newsType);
            title = (TextView) view.findViewById(R.id.title);
            mImgNews = (ImageView) view.findViewById(R.id.img_news);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
