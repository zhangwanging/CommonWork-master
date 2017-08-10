package com.hzu.jpg.commonwork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzu.jpg.commonwork.Presenter.MyOneKeyJobPresenter;
import com.hzu.jpg.commonwork.R;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MyOneKeyJobFragment extends Fragment {

    RecyclerView recyclerView;
    MyOneKeyJobPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_one_key_job, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_my_one_key_job);
        if(presenter==null){
            presenter=new MyOneKeyJobPresenter(this);
            presenter.setData();
        }
        return rootView;
    }

    public void setRecyclerView(RecyclerView.Adapter adapter){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
