package com.hzu.jpg.commonwork.Presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AgentActivity;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.moudle.AgentModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.holder.AgentRvHolder;
import com.hzu.jpg.commonwork.utils.JsonUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AgentPresenter {

    AgentActivity activity;
    AgentModel model;
    MyRvAdapter adapter;

    public AgentPresenter(AgentActivity activity){
        this.activity=activity;
        model=new AgentModel();
    }

    public void initData() {
        if (MyApplication.user.getOwnAgentStatus()==1) {
            activity.setInvite(String.valueOf(MyApplication.user.getOwnAgentId()));
            model.getRvData(new AgentModel.OnAgentDataReceiveListener() {
                @Override
                public void onAgentDataReceive(String s) {
                    Log.e("agents",s);
                    Gson gson = new Gson();
                    List<User> list = gson.fromJson(s, new TypeToken<ArrayList<User>>() {
                    }.getType());
                    if (adapter == null) {
                        adapter = new MyRvAdapter(R.layout.item_agent, AgentRvHolder.class, activity, list);
                        activity.setRvData(adapter);
                    } else {
                        adapter.update(list);
                    }
                }
            });
        }else{
            ToastUtil.showToast("非代理不能获取数据");
        }
    }

}
