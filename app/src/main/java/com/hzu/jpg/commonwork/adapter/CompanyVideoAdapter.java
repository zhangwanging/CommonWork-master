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
import com.hzu.jpg.commonwork.enity.CompanyVideoVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by cimcitech on 2017/8/30.
 */

public class CompanyVideoAdapter extends BaseAdapter {

    private List<CompanyVideoVo.DataBean> data;
    private LayoutInflater inflater;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;
    private Context context;

    public CompanyVideoAdapter(Context context, List<CompanyVideoVo.DataBean> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    public List<CompanyVideoVo.DataBean> getAll() {
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
        CompanyVideoVo.DataBean detail = data.get(i);
        if (viewHolder == null) {
            view = inflater.inflate(R.layout.company_video_item_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(context.getApplicationContext())
                .load(Constants.imageUrl + detail.getVideoImg())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.image_bg_default)
                .error(R.mipmap.image_bg_default).into(viewHolder.videoImg);
        viewHolder.videoTitle.setText(detail.getVideoTitle());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.video_img)
        ImageView videoImg;
        @Bind(R.id.video_title)
        TextView videoTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
