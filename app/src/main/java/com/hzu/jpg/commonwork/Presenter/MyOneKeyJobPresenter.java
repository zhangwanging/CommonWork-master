package com.hzu.jpg.commonwork.Presenter;


import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.OneKeyJobBean;
import com.hzu.jpg.commonwork.enity.moudle.MyOneKeyJobModel;
import com.hzu.jpg.commonwork.fragment.MyOneKeyJobFragment;
import com.hzu.jpg.commonwork.holder.OneKeyJobRvHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MyOneKeyJobPresenter {

    MyOneKeyJobFragment fragment;
    MyOneKeyJobModel model;
    MyRvAdapter adapter;

    public MyOneKeyJobPresenter(MyOneKeyJobFragment fragment){
        this.fragment=fragment;
        model=new MyOneKeyJobModel(fragment.getActivity());
    }

    public void setData(){
        model.getData(new MyOneKeyJobModel.OnMyOneKeyJobReceiveListener() {
            @Override
            public void onMyOneKeyJobReceive(List<OneKeyJobBean> list) {
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_one_key_job, OneKeyJobRvHolder.class,fragment.getContext(),list);
                    fragment.setRecyclerView(adapter);
                }else{
                    adapter.update(list);
                }
            }
        });
    }

}
