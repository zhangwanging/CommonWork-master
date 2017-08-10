package com.hzu.jpg.commonwork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzu.jpg.commonwork.Presenter.MyListJobPresenter;
import com.hzu.jpg.commonwork.R;


/**
 * Created by Administrator on 2017/3/1.
 */

public class MyListJobFragment extends Fragment {

    RecyclerView recyclerView;
    MyListJobPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_list_job, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_my_find_job);
        presenter=new MyListJobPresenter(this);
        presenter.setRvData();
        return rootView;
    }

    public void setRv(RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
}
