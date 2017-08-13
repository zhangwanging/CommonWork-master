package com.hzu.jpg.commonwork.HourWork.Presenter;

import android.content.Intent;
import android.view.View;

import com.hzu.jpg.commonwork.HourWork.Activity.HourWorkAddRecordActivity;
import com.hzu.jpg.commonwork.HourWork.Bean.HourWorkRecordBean;
import com.hzu.jpg.commonwork.HourWork.Fragment.HourWorkMainFragment;
import com.hzu.jpg.commonwork.HourWork.Model.HourWorkAddRecordModel;
import com.hzu.jpg.commonwork.HourWork.Model.HourWorkMainModel;
import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.adapter.HourWorkRvAdapter;
import com.hzu.jpg.commonwork.adapter.HourWorkRvAdapter.MyClickListener;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class HourWorkMonthPresenter {

    HourWorkMainFragment fragment;
    HourWorkMainModel model;
    HourWorkAddRecordModel rvModel;
    private ArrayList<HourWorkRecordBean> list;
    HourWorkRvAdapter adapter;

    public HourWorkMonthPresenter(HourWorkMainFragment fragment){
        this.fragment=fragment;
        model=new HourWorkMainModel(fragment.getContext());
        rvModel = new HourWorkAddRecordModel(fragment.getContext());
    }

    public void initData(){
        String date=TimeUtil.getDateYM();
        OverTimeRecordMonthBean bean=model.getBean(date);
        if (bean==null){
            bean=model.getDef();
        }else if(bean.getHour_work()==1){
            ToastUtil.showToast("hourwork update month");
            bean=model.updateMonth(bean.getDate_ym());
        }
        fragment.setDate(date);
        fragment.setBasicSalary(String.valueOf(bean.getOt_salary()));
        fragment.setHours(String.valueOf(bean.getOt_hours()));
        fragment.setTotalSalary(String.valueOf(bean.getSalary()));
        setRvData(bean.getDate_ym());
    }

    public void onUpdate() {
        initData();
    }

    private void setRvData(String date) {
        if (adapter == null) {
            list = new ArrayList<>();
            rvModel.getRvData(date, list, 10);
            adapter = new HourWorkRvAdapter(fragment.getContext(), list, mListener);
            fragment.setRv(adapter);
        } else {
            list.clear();
            rvModel.getRvData(date, list, 10);
            adapter.notifyDataSetChanged();
        }
    }

    private MyClickListener mListener = new MyClickListener() {

        @Override
        public void myOnClick(int position, View v) {
            String date = list.get(position).getDate_ymd();
            Intent intent = new Intent(fragment.getActivity(), HourWorkAddRecordActivity.class);
            intent.putExtra("date", date);
            intent.putExtra("type", AddOverTimeRecordActivity.FROM_CALENDAR);
            fragment.getActivity().startActivityForResult(intent, 0);
        }
    };

}
