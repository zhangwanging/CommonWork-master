package com.hzu.jpg.commonwork.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.utils.StringUtils;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AgentRvHolder extends MyRvHolder {

    private TextView tvName;
    private TextView tvSex;
    private TextView tvPhone;
    private TextView tvLocation;
    private ImageView ivHead;


    public AgentRvHolder(View itemView) {
        super(itemView);
        tvLocation= (TextView) itemView.findViewById(R.id.tv_item_agent_location);
        tvName= (TextView) itemView.findViewById(R.id.tv_item_agent_name);
        tvPhone= (TextView) itemView.findViewById(R.id.tv_item_agent_phone);
        tvSex= (TextView) itemView.findViewById(R.id.tv_item_agent_sex);
        ivHead= (ImageView) itemView.findViewById(R.id.iv_item_agent_head);
    }

    @Override
    public void setData(Object obj) {
        User user= (User) obj;
        if(user!=null){
            if (StringUtils.isEmpty(user.getRealname())){
                tvName.setText(user.getUsername());
            }else{
                tvName.setText(user.getRealname());
            }
            tvSex.setText("性别："+user.getSex());
            tvPhone.setText("联系方式："+user.getLink_phone());
            tvLocation.setText(user.getProvince()+user.getCity()+user.getRegion());
            Glide.with(itemView.getContext())
                    .load(Config.IP+user.getIcno())
                    .error(R.mipmap.ic_head_default)
                    .into(ivHead);
        }
    }

    @Override
    public void setOnClick(MyRvAdapter.OnRvClickListener listener, Object obj, int position) {

    }

    @Override
    public Object getOnClickId(Object obj) {
        return null;
    }
}
