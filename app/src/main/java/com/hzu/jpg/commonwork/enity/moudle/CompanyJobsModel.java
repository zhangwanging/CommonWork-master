package com.hzu.jpg.commonwork.enity.moudle;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.Receiver.CompanyJobsListReceiver;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Bean.CompanyJobsListBean;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyJobsModel {


    public void getData(final CompanyJobsModel.OnCompanyJobsReceiveListener listener, int start) {
        String url = Config.IP + "/HRM/capply/android/jobList.html" ;
//        StringRequest stringRequest = new StringRequest(url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Gson gson=new Gson();
//                        CompanyJobsListReceiver receiver=gson.fromJson(response, CompanyJobsListReceiver.class);
//                        if(receiver.getStatu().equals("1")){
//                            listener.onCompanyJobsReceive(receiver.getData());
//                        }
//                    }
//                }, new NetworkErrorListener(Config.CONTEXT));
//        MyApplication.getQueue().add(stringRequest);

        OkHttpUtils.get().url(url).addParams("start", String.valueOf(start)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                CompanyJobsListReceiver receiver = gson.fromJson(response, CompanyJobsListReceiver.class);
                if (receiver.getStatu().equals("1")) {
                    listener.onCompanyJobsReceive(receiver.getData());
                } else {
                    ToastUtil.showToast(receiver.getMessage());
                }
            }
        });
    }


    public interface OnCompanyJobsReceiveListener {
        void onCompanyJobsReceive(List<CompanyJobsListBean> list);
    }
}
