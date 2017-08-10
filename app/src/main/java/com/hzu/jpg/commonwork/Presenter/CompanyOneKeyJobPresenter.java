package com.hzu.jpg.commonwork.Presenter;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.CompanyOneKeyJobBean;
import com.hzu.jpg.commonwork.enity.moudle.CompanyOneKeyJobModel;
import com.hzu.jpg.commonwork.fragment.CompanyOneKeyJobFragment;
import com.hzu.jpg.commonwork.holder.CompanyOneKeyJobHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyOneKeyJobPresenter {


    CompanyOneKeyJobFragment fragment;
    CompanyOneKeyJobModel model;
    MyRvAdapter adapter;

    int i;

    public CompanyOneKeyJobPresenter(CompanyOneKeyJobFragment fragment){
        this.fragment=fragment;
        model=new CompanyOneKeyJobModel(fragment.getActivity());
    }

    public void setRvData(){
        i=0;
        model.getData(new CompanyOneKeyJobModel.OnCompanyOneKeyJobReceiveListener() {
            @Override
            public void onCompanyOneKeyJobReceive(List<CompanyOneKeyJobBean> list){
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_company_one_key_job, CompanyOneKeyJobHolder.class,fragment.getActivity(),list);
                    fragment.setRecyclerView(adapter);
                    adapter.setListener(new MyRvAdapter.OnRvClickListener() {
                        @Override
                        public void onRvClick(Object obj,final int position) {
                            String id= (String) obj;
                            model.Delete(id, new CompanyOneKeyJobModel.OnCompanyOneKeyJobDeleteListener() {
                                @Override
                                public void onCompanyOneKeyJobDelete(String id) {
                                    adapter.list.remove(position);
                                    adapter.update(adapter.list);
                                }
                            });
                        }
                    });
                }else{
                    adapter.update(list);
                }
            }
        },i);
    }
}
