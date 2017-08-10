package com.hzu.jpg.commonwork.adapter.goods;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.activity.goods.GoodsDetailActivity;
import com.hzu.jpg.commonwork.enity.goods.GoodsVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cimcitech on 2017/5/27.
 */

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<GoodsVo> data;
    private LayoutInflater inflater;
    private static final int TYPE_END = 2;
    private boolean isNotMoreData = false;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private int point;
    private Context context;

    public GoodsAdapter(Context context, List<GoodsVo> data, int point) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.point = point;
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

    public List<GoodsVo> getAll() {
        return data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClickListener(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = inflater.inflate(R.layout.goods_list_view, parent, false);
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
            final GoodsVo detail = data.get(position);
            ((ItemViewHolder) holder).tv_name.setText(detail.getName());
            ((ItemViewHolder) holder).tv_point.setText(detail.getPoint() + "积分");
            ((ItemViewHolder) holder).tv_price.setText("市场参考价" + detail.getPrice() + "元");
            ((ItemViewHolder) holder).tv_describes.setText(detail.getDescribes());
            ((ItemViewHolder) holder).tv_saleamount.setText("已销售" + detail.getSaleamount() + "件");
//            imageLoader.displayImage(Constants.imageUrl +
//                    detail.getPicture(), ((ItemViewHolder) holder).iv_image, options);
            Glide.with(context.getApplicationContext())
                    .load(Constants.imageUrl + detail.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.image_bg_default)
                    .error(R.mipmap.image_bg_default).into(((ItemViewHolder) holder).iv_image);
            ((ItemViewHolder) holder).bt_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (point < Integer.parseInt(detail.getPoint())) {
                        ToastUtil.showToast("您的积分不足");
                    } else {
                        Intent intent = new Intent(inflater.getContext(), GoodsDetailActivity.class);
                        intent.putExtra("detail", detail);
                        inflater.getContext().startActivity(intent);
                    }

                }
            });
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

        TextView tv_name, tv_point, tv_price, tv_describes, tv_saleamount;
        ImageView iv_image;
        Button bt_buy;


        public ItemViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_point = (TextView) view.findViewById(R.id.tv_point);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_describes = (TextView) view.findViewById(R.id.tv_describes);
            tv_saleamount = (TextView) view.findViewById(R.id.tv_saleamount);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
            bt_buy = (Button) view.findViewById(R.id.bt_buy);

        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
