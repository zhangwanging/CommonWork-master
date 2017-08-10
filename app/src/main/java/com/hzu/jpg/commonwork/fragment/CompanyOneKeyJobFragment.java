package com.hzu.jpg.commonwork.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hzu.jpg.commonwork.Presenter.CompanyOneKeyJobPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;


/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyOneKeyJobFragment extends Fragment {

    CompanyOneKeyJobPresenter presenter;
    RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_one_key_job, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.rv_my_one_key_job);
        presenter=new CompanyOneKeyJobPresenter(this);
        presenter.setRvData();
        return rootView;
    }

    public void setRecyclerView(MyRvAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
