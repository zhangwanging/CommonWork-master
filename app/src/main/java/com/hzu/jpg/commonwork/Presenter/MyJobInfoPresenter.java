package com.hzu.jpg.commonwork.Presenter;

import android.graphics.Bitmap;

import com.hzu.jpg.commonwork.activity.MyJobInfoActivity;
import com.hzu.jpg.commonwork.enity.Bean.MyJobInfoBean;
import com.hzu.jpg.commonwork.enity.moudle.MyJobInfoModel;


/**
 * Created by Administrator on 2017/4/2.
 */

public class MyJobInfoPresenter {

    private MyJobInfoActivity activity;
    MyJobInfoModel model;

    public MyJobInfoPresenter(MyJobInfoActivity activity){
        this.activity=activity;
        model=new MyJobInfoModel(activity);
    }

    public void setData(String id){
        model.getJobInfo(new MyJobInfoModel.OnJobInfoReceiveListener() {
            @Override
            public void onJobInfoReceive(MyJobInfoBean bean) {
                activity.setDate(bean.getDate());
                activity.setCname(bean.getCname());
                activity.setDescribes(bean.getDescribes());
                activity.setJob(bean.getJob());
                activity.setLinkMan(bean.getLinkMan());
                activity.setLinkPhone(bean.getLinkPhone());
                activity.setSalary(bean.getSalary());
                activity.setMoreSalary(bean.getMoreSalary());
                activity.setIsFinish(bean.getIsFinish());
                String unit=bean.getUnit();
                if(unit.equals("2")){
                    activity.setUnit("小时");
                }else{
                    activity.setUnit("月");
                }
                String Location=bean.getProvince()+bean.getCity()+bean.getRegion()+bean.getDetails();
                activity.setLocation(Location);
                activity.setNumber(bean.getNumber());
                activity.setRequired(bean.getRequired());
                activity.setFlowLayoutData(bean.getJobLabel());
                activity.cancelProgress();
                model.getIcon(new MyJobInfoModel.OnJobInfoIconReceiveListener() {
                    @Override
                    public void onJobInfoIconReceive(Bitmap bitmap) {
                        activity.setIcon(bitmap);
                    }

                    @Override
                    public void onJobInfoIconFail() {
                        activity.setIcon(null);
                    }
                },bean.getCover());
            }
        },id);
    }
}
