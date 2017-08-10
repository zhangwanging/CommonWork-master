package com.hzu.jpg.commonwork.adapter.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.service.ReplyVo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by cimcitech on 2017/5/31.
 */

public class ReplyAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ArrayList<ReplyVo.Reply> replies;

    public ReplyAdapter(Context context, ArrayList<ReplyVo.Reply> replies) {
        this.replies = replies;
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
        return replies.size();
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
        ReplyVo.Reply reply = replies.get(i);
        if (view == null) {
            holderView = new HolderView();
            view = inflater.inflate(R.layout.reply_item_view, null);
            holderView.nickname = (TextView) view.findViewById(R.id.nickname);
            holderView.content = (TextView) view.findViewById(R.id.content);
            view.setTag(holderView);
        } else {
            holderView = (HolderView) view.getTag();
        }
        holderView.nickname.setText(reply.getNickname() + "：");
        holderView.content.setText(reply.getContent());
        return view;
    }

    public class HolderView {
        public TextView nickname, content;
    }
}
