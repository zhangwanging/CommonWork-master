package com.hzu.jpg.commonwork.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.PhotoViewActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.base.BaseRvAdapter;
import com.hzu.jpg.commonwork.base.BaseViewHolder;
import com.hzu.jpg.commonwork.enity.Comment;
import com.hzu.jpg.commonwork.enity.SharedMsg;

import java.util.ArrayList;
import java.util.List;

import me.fangx.common.util.DensityUtils;

/**
 * Created by Administrator on 2017/1/21.
 */

public class SharedMsgRvAdapter extends BaseRvAdapter<SharedMsg>{
    private static final String TAG = "SharedMsgRvAdapter";
    private int imageId[] = {R.id.img_0,R.id.img_1,R.id.img_2,R.id.img_3,R.id.img_4,R.id.img_5,R.id.img_6,R.id.img_7,R.id.img_8};
    private int commentNameId[] = {R.id.tv_name1, R.id.tv_name2, R.id.tv_name3};
    private int commentContentId[] = {R.id.tv_content1, R.id.tv_content2, R.id.tv_content3};
    private int commentLayout[] = {R.id.linearLayout_1, R.id.linearLayout_2, R.id.linearLayout_3};

    public SharedMsgRvAdapter(List<SharedMsg> sharedMsg) {
        super(Config.CONTEXT,R.layout.item_shared_msg,sharedMsg);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SharedMsg item) {

        helper.setText(R.id.tv_userContent,item.getMsg());
        helper.setText(R.id.tv_userName,item.getUserName());
        helper.setText(R.id.tv_praiseNum,item.getPraiseNum()+"");
        helper.setText(R.id.tv_commentsNum,item.getCommentsNum()+"");
        helper.setImageUrl(R.id.img_userHead,item.getUserImgHead());
        if(item.isPraise()){
            helper.setImageResource(R.id.img_praise, R.mipmap.ic_praise_press);
            helper.getView(R.id.img_praise).setOnClickListener(null);
        }
        else{
            helper.setImageResource(R.id.img_praise, R.mipmap.ic_praise);
            helper.setOnClickListener(R.id.img_praise, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(TAG, "onClick: " + R.id.img_praise, null);
                    item.setPraise(true);
                    helper.setText(R.id.tv_praiseNum,(item.getPraiseNum()+1)+"");
                    item.setPraiseNum(item.getPraiseNum()+1);
                    helper.setImageResource(R.id.img_praise,R.mipmap.ic_praise_press);
                    helper.getView(R.id.img_praise).setOnClickListener(null);
                }
            });
        }

        //评论区
        List<Comment> comments = item.getComments();
        if(comments != null && comments.size() > 0){
            helper.setVisible(R.id.layout_comment,true);
            helper.setVisible(R.id.linearLayout_1,false);
            helper.setVisible(R.id.linearLayout_2,false);
            helper.setVisible(R.id.linearLayout_3,false);
            int j = comments.size() > 3 ? 3 : comments.size();
            for (int i = 0; i < j; i++) {
                helper.setVisible(commentLayout[i],true);
                helper.setText(commentNameId[i],comments.get(i).getName());
                helper.setText(commentContentId[i],comments.get(i).getComment());
            }
            if (item.getCommentsNum() > 3)
                helper.setVisible(R.id.tv_showAllComments, true);
            else
                helper.setVisible(R.id.tv_showAllComments,false);
        }

        //图片
        GridLayout gridView = helper.getView(R.id.gridview);
        ImageView imgSingleShowing = helper.getView(R.id.img_singleShowing);
        gridView.setVisibility(View.GONE);
        imgSingleShowing.setVisibility(View.GONE);
        if (item.getImgUrls().size() == 1){
            imgSingleShowing.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            helper.setImageUrl(R.id.img_singleShowing,item.getImgUrls().get(0));
            helper.setOnClickListener(R.id.img_singleShowing, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent photo = new Intent(mContext, PhotoViewActivity.class);
                    photo.putExtra("photo_count", 1);
                    photo.putExtra("photo_position", 0);
                    ArrayList<String> temp = new ArrayList<>();
                    temp.addAll(item.getImgUrls());
                    photo.putStringArrayListExtra("photoUrl", temp);
                    photo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(photo);
                }
            });
        }else if(item.getImgUrls().size() > 1){
            imgSingleShowing.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            int a = item.getImgUrls().size()/3;
            int b = item.getImgUrls().size()%3;
            if (b > 0){
                a++;
            }
            float width = (DensityUtils.getDisplayWidth(Config.CONTEXT) - DensityUtils.dip2px(Config.CONTEXT,80) - DensityUtils.dip2px(Config.CONTEXT,2))/3;
            gridView.getLayoutParams().height = (int)(a*width);

            for (int i = 0 ; i < 9 ; i++){
                helper.getView(imageId[i]).setVisibility(View.GONE);
            }

            for (int i = 0 ; i < item.getImgUrls().size(); i++){
                helper.getView(imageId[i]).setVisibility(View.VISIBLE);
                helper.getView(imageId[i]).getLayoutParams().width = (int)width;
                helper.getView(imageId[i]).getLayoutParams().height = (int)width;
                helper.setImageUrl(imageId[i],item.getImgUrls().get(i));
                final int finalI = i;
                helper.setOnClickListener(imageId[i], new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photo = new Intent(mContext, PhotoViewActivity.class);
                        photo.putExtra("photo_count", item.getImgUrls().size());
                        photo.putExtra("photo_position", finalI);
                        ArrayList<String> temp = new ArrayList<>();
                        temp.addAll(item.getImgUrls());
                        photo.putStringArrayListExtra("photoUrl", temp);
                        photo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(photo);
                    }
                });
            }
        }
    }
}
