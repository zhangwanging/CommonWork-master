package com.hzu.jpg.commonwork.Presenter;

import android.content.Intent;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AddOverTimeRecordActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordBean;
import com.hzu.jpg.commonwork.enity.Bean.OverTimeRecordMonthBean;
import com.hzu.jpg.commonwork.enity.moudle.OverTimeRecordMonthModel;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.holder.OverTimeRecordRvHolder;
import com.hzu.jpg.commonwork.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/5.
 */

public class OverTimeRecordPresenter {

    private OverTimeRecordFragment fragment;
    OverTimeRecordMonthModel model;
    MyRvAdapter adapter;
    private List<OverTimeRecordBean> list;


    public OverTimeRecordPresenter(OverTimeRecordFragment fragment) {

        this.fragment = fragment;
        model = new OverTimeRecordMonthModel(fragment.getContext());
    }

    public void init() {
        OverTimeRecordMonthBean bean = model.getOTRecordMonthSalary(TimeUtil.getDateYM());
        if (bean == null) {
            fragment.setOT_Salary("0");
            fragment.setOT_Hours("0");
            fragment.setSalary(Double.toString(model.getBasicSalary(TimeUtil.getDateYM())));
            fragment.setDate(TimeUtil.getDateYM());
            return;
        }
        if (bean.getHour_work()==0) {
            model.updateMonthBean(bean);
        }
        fragment.setSalary(Double.toString(bean.getSalary()));
        fragment.setOT_Hours(Double.toString(bean.getOt_hours()));
        fragment.setOT_Salary(Double.toString(bean.getOt_salary()));
        fragment.setDate(TimeUtil.getDateYM());
        setRvData(bean.getDate_ym());
    }

    public void onUpdate() {
        init();
    }

    private void setRvData(String date) {
        if (adapter == null) {
            list = new ArrayList<>();
            model.getRvData(date, list, 10);
            adapter = new MyRvAdapter(R.layout.item_rv_ot_record, OverTimeRecordRvHolder.class, fragment.getContext(), list);
            fragment.setRv(adapter);
            adapter.setListener(new MyRvAdapter.OnRvClickListener() {
                @Override
                public void onRvClick(Object obj, int position) {
                    String date = (String) obj;
                    Intent intent = new Intent(fragment.getActivity(), AddOverTimeRecordActivity.class);
                    intent.putExtra("date", date);
                    intent.putExtra("type", AddOverTimeRecordActivity.FROM_CALENDAR);
                    fragment.getActivity().startActivityForResult(intent, 0);
                }
            });
        } else {
            list.clear();
            model.getRvData(date, list, 10);
            adapter.notifyDataSetChanged();
        }
    }
}
