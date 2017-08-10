package com.hzu.jpg.commonwork.Presenter;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.BorrowRecordActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.BorrowBean;
import com.hzu.jpg.commonwork.enity.moudle.BorrowRecordModel;
import com.hzu.jpg.commonwork.holder.BorrowRvHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class BorrowRecordPresenter {

    private BorrowRecordActivity activity;
    private BorrowRecordModel model;
    private MyRvAdapter adapter;

    public BorrowRecordPresenter(BorrowRecordActivity activity){
        this.activity=activity;
        model=new BorrowRecordModel(activity);
    }

    public void setData(){
        model.getRecord(new BorrowRecordModel.OnBorrowRecordReceiveListener() {
            @Override
            public void onBorrowRecordReceive(List<BorrowBean> list) {
                if(list==null){
                    return;
                }
                if(list.size()>0&&list.get(list.size()-1).getLoanstatus().equals("已借款")){
                    activity.setBorrowMoney(list.get(list.size()-1).getLoanamount());
                }else{
                    activity.setBorrowMoney("0.00");
                }
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_rv_borrow, BorrowRvHolder.class,activity,list);
                    activity.setRvData(adapter);
                }else{
                    adapter.update(list);
                }
            }
        });
    }

    public void onBorrowApplySuccess(BorrowBean borrowBean){

    }

}
