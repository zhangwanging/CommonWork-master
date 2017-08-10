package com.hzu.jpg.commonwork.adapter.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by cimcitech on 2017/5/31.
 */

public class PostsImageAdapter extends BaseAdapter {

    private String str[];
    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public PostsImageAdapter(Context context, String str[]) {
        this.str = str;
        inflater = LayoutInflater.from(context);
        options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.image_bg_default)
                .showImageForEmptyUri(R.mipmap.image_bg_default).showImageOnFail(R.mipmap.image_bg_default)
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.ALPHA_8)
                .build();
    }

    @Override
    public int getCount() {
        return str.length;
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
        HolderView holderView;
        if (view == null) {
            holderView = new HolderView();
            view = inflater.inflate(R.layout.image_item_view, null);
            holderView.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }

        Glide.with(view.getContext().getApplicationContext())
                .load(Constants.imageUrl +
                        str[i])
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.image_bg_default)
                .error(R.mipmap.image_bg_default).into(holderView.imageView);


//        imageLoader.displayImage(Constants.imageUrl + str[i], holderView.imageView, options);
        return view;
    }

    public class HolderView {
        public ImageView imageView;
    }
}
