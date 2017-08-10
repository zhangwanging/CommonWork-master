package com.hzu.jpg.commonwork.Presenter;

import android.os.Handler;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.SalaryFormActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.SalaryFormBean;
import com.hzu.jpg.commonwork.enity.moudle.SalaryFormModel;
import com.hzu.jpg.commonwork.holder.SalaryFormRvHolder;

import java.util.List;


/**
 * Created by Administrator on 2017/3/27.
 */

public class SalaryFormPresenter {

    SalaryFormActivity activity;
    SalaryFormModel model;
    MyRvAdapter adapter;


    Handler handler;

    public SalaryFormPresenter(SalaryFormActivity activity){
        this.activity=activity;
        model=new SalaryFormModel(activity);
    }

    public void setData(){
        model.getSalaryForms(new SalaryFormModel.OnSalaryFormResultListener() {
            @Override
            public void onSuccess(List<SalaryFormBean> list) {
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_rv_salary_form, SalaryFormRvHolder.class,activity,list);
                    activity.setRv(adapter);
                }else{
                    adapter.update(list);
                }
            }
        });
    }

}
