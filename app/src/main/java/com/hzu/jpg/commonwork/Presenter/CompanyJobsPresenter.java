package com.hzu.jpg.commonwork.Presenter;

import android.content.Intent;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.MyJobInfoActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.CompanyJobsListBean;
import com.hzu.jpg.commonwork.enity.moudle.CompanyJobsModel;
import com.hzu.jpg.commonwork.fragment.CompanyJobsFragment;
import com.hzu.jpg.commonwork.holder.CompanyJobsRvHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyJobsPresenter {

    CompanyJobsFragment fragment;
    CompanyJobsModel model;
    int start=0;
    MyRvAdapter adapter;

    public CompanyJobsPresenter( CompanyJobsFragment fragment) {
        this.fragment = fragment;
        model=new CompanyJobsModel();
    }

    public  void setRvData(){
        model.getData(new CompanyJobsModel.OnCompanyJobsReceiveListener() {
            @Override
            public void onCompanyJobsReceive(List<CompanyJobsListBean> list) {
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_company_jobs, CompanyJobsRvHolder.class,fragment.getActivity(),list);
                    fragment.setRecyclerView(adapter);
                    adapter.setListener(new MyRvAdapter.OnRvClickListener() {
                        @Override
                        public void onRvClick(Object obj,int position) {
                            String id= (String) obj;
                            Intent intent=new Intent(fragment.getActivity(), MyJobInfoActivity.class);
                            intent.putExtra("id",id);
                            fragment.startActivity(intent);
                        }
                    });
                }
            }
        },start);
    }
}
