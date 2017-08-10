package com.hzu.jpg.commonwork.Presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.MyInfoActivity;
import com.hzu.jpg.commonwork.enity.moudle.MyInfoModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.utils.ToastUtil;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MyInfoPresenter {

    MyInfoActivity activity;
    MyInfoModel model;

    public MyInfoPresenter(MyInfoActivity activity){
        this.activity=activity;
        if(model==null){
            model=new MyInfoModel(activity);
        }
    }


    public void setData(){
        model.setData(new MyInfoModel.OnMyInfoReceiveListener() {
            @Override
            public void onMyInfoReceive(User user) {
                activity.setTelephone(user.getTelephone());
                activity.setBalance(user.getBalance());
                activity.setBank_card(user.getBank_card());
                String birthday=user.getBirthday();
                String[] ss=birthday.split("-");
                int month=Integer.parseInt(ss[1]);
                int day=Integer.parseInt(ss[2]);
                birthday=ss[0]+"年"+month+"月"+day+"日";
                activity.setBirthday(birthday);
                activity.setCity(user.getCity());
                activity.setIdcard(user.getIDcard());
                activity.setMajor(user.getMajor());
                activity.setPoint(user.getPoint());
                activity.setProvince(user.getProvince());
                activity.setRealname(user.getRealname());
                activity.setRegion(user.getRegion());
                activity.setRequire(user.getRequires());
                activity.setSchool(user.getSchool());
                activity.setSex(user.getSex());
                activity.setSign(user.getSign());
                activity.setUsername(user.getUsername());
                activity.setEntryTime(user.getEntry_time());
                activity.setLinkPhone(user.getLink_phone());
            }
        });
    }
}
