package com.hzu.jpg.commonwork.Presenter;


import android.util.Log;

import com.hzu.jpg.commonwork.activity.CompanyInfoActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.moudle.CompanyInfoModel;
import com.hzu.jpg.commonwork.enity.moudle.User;

/**
 * Created by Administrator on 2017/4/9.
 */

public class CompanyInfoPresenter {

    CompanyInfoActivity activity;
    CompanyInfoModel model;

    public CompanyInfoPresenter(CompanyInfoActivity activity) {
        this.activity = activity;
        if(model==null)
            model=new CompanyInfoModel(Config.CONTEXT);
    }

    public void setData(){
        model.getData(new CompanyInfoModel.OnCompanyInfoReceiveListener() {
            @Override
            public void onCompanyInfoReceive(User user) {
                if(user==null){
                    Log.e("user","user is null");
                    return ;

                }
                activity.setLinkMan(user.getLink_man());
                activity.setDescribes(user.getDescribes());
                activity.setDetails(user.getDetails());
                activity.setEmail(user.getEmail());
                activity.setLabel(user.getLabel());
                activity.setLinkPhone(user.getLink_phone());
                activity.setName(user.getName());
                activity.setTelephone(user.getTelephone());
                StringBuilder sb=new StringBuilder();
                sb.append(user.getProvince()).append("-").append(user.getCity()).append("-").append(user.getRegion());
                activity.setLocation(sb.toString());
            }
        });
    }
}
