package com.hzu.jpg.commonwork.Presenter;

import android.content.Intent;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.MyJobInfoActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.ListJobInfoBean;
import com.hzu.jpg.commonwork.enity.moudle.MyListJobModel;
import com.hzu.jpg.commonwork.fragment.MyListJobFragment;
import com.hzu.jpg.commonwork.holder.MyListJobRvHolder;

import java.util.List;


/**
 * Created by Administrator on 2017/3/2.
 */

public class MyListJobPresenter {

    MyListJobModel model;
    MyListJobFragment fragment;
    MyRvAdapter adapter;

    public MyListJobPresenter(MyListJobFragment fragment) {
        this.fragment = fragment;
        this.model = new MyListJobModel(fragment.getActivity());
    }

    public void setRvData(){
        model.getListJobInfo(new MyListJobModel.OnMyListJobReceiveListener() {
            @Override
            public void onMyListJobReceive(List<ListJobInfoBean> list) {
                if(adapter==null){
                    adapter=new MyRvAdapter(R.layout.item_rv_my_job_info, MyListJobRvHolder.class,fragment.getActivity(),list);
                    adapter.setListener(new MyRvAdapter.OnRvClickListener() {
                        @Override
                        public void onRvClick(Object obj,int position) {
                            String id= (String) obj;
                            Intent intent=new Intent(fragment.getActivity(), MyJobInfoActivity.class);
                            intent.putExtra("id",id);
                            fragment.startActivity(intent);
                        }
                    });
                    fragment.setRv(adapter);
                }else{
                    adapter.update(list);
                }
            }
        });
    }
}
