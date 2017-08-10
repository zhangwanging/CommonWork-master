package com.hzu.jpg.commonwork.enity.moudle;

import android.content.Context;

import com.hzu.jpg.commonwork.app.MyApplication;


/**
 * Created by Administrator on 2017/4/9.
 */

public class CompanyInfoModel {

    Context context;

    public CompanyInfoModel(Context context) {
        this.context = context;
    }

    public void getData(OnCompanyInfoReceiveListener listener){
        listener.onCompanyInfoReceive(MyApplication.user);

    }

    public interface OnCompanyInfoReceiveListener{
        void onCompanyInfoReceive(User user);
    }

}
