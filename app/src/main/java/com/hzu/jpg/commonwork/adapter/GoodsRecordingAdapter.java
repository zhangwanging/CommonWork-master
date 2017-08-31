package com.hzu.jpg.commonwork.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.goods.GoodsAdapter;
import com.hzu.jpg.commonwork.enity.GoodsRecordingVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cimcitech on 2017/8/25.
 */

public class GoodsRecordingAdapter extends BaseAdapter {

    private List<GoodsRecordingVo.OrderListBean> data;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private Context context;

    public GoodsRecordingAdapter(Context context, List<GoodsRecordingVo.OrderListBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public List<GoodsRecordingVo.OrderListBean> getAll() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        GoodsRecordingVo.OrderListBean detail = data.get(i);
        if (viewHolder == null) {
            view = inflater.inflate(R.layout.goods_recording_list_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvName.setText(detail.getShopInfo().getName());
        viewHolder.tvPoint.setText(detail.getStatus() == 0 ? "未发货" : "已发货");
        viewHolder.tvPrice.setText("收货人账号：" + detail.getAccount());
        viewHolder.tvDescribes.setText("联系方式：" + detail.getLink_phone());
        viewHolder.tvSaleamount.setText("地址：" + detail.getOrderaddress());
//            imageLoader.displayImage(Constants.imageUrl +
//                    detail.getPicture(), ((ItemViewHolder) holder).iv_image, options);
        Glide.with(context.getApplicationContext())
                .load(Constants.imageUrl + detail.getShopInfo().getPrice())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.image_bg_default)
                .error(R.mipmap.image_bg_default).into(viewHolder.ivImage);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.iv_image)
        ImageView ivImage;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_point)
        TextView tvPoint;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_describes)
        TextView tvDescribes;
        @Bind(R.id.tv_saleamount)
        TextView tvSaleamount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
