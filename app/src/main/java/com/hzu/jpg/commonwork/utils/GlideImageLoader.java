package com.hzu.jpg.commonwork.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.yyydjk.library.BannerLayout;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {

    private OnImageClickListener listener;

    public GlideImageLoader (OnImageClickListener listener){
        this.listener=listener;
    }

    @Override
    public void displayImage(Context context, final String path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(path)
                .placeholder(R.mipmap.bg_default)
                .error(R.mipmap.bg_default)
                .placeholder(R.mipmap.bg_default)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onImageClick(path);
                }
            }
        });

    }

    public interface OnImageClickListener{
        void onImageClick(String path);
    }
}
