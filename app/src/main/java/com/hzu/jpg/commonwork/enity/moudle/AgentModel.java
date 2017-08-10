package com.hzu.jpg.commonwork.enity.moudle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.*;
import com.hzu.jpg.commonwork.utils.NetworkErrorListener;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/5/15.
 */

public class AgentModel {


    public void getRvData(final OnAgentDataReceiveListener listener){
        User user=MyApplication.user;
        if (user==null|| user.getAgent_apply().equals("0")||StringUtils.isEmpty(user.getAgent_id())||user.getOwnAgentId()<=0){
            ToastUtil.showToast("获取数据失败!!");
            return;
        }
        String url= Config.IP+"/HRM/agent/getAgentStu.html?method=android&agentId="+user.getOwnAgentId();
        RequestQueue queue=MyApplication.getQueue();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String data= null;
                try {
                    data = new String(s.getBytes("ISO-8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                listener.onAgentDataReceive(data);
            }
        },new NetworkErrorListener(Config.CONTEXT));
        queue.add(request);
    }

    public interface OnAgentDataReceiveListener{
        void onAgentDataReceive(String s);
    }

}
