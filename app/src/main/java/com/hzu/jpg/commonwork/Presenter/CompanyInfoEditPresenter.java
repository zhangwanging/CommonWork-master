package com.hzu.jpg.commonwork.Presenter;


import android.util.Log;

import com.hzu.jpg.commonwork.activity.CompanyInfoActivity;
import com.hzu.jpg.commonwork.activity.CompanyInfoEditActivity;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.CompanyInfoBean;
import com.hzu.jpg.commonwork.enity.moudle.CompanyInfoEditModel;
import com.hzu.jpg.commonwork.enity.moudle.MyInfoEditModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CompanyInfoEditPresenter {

    CompanyInfoEditActivity activity;
    CompanyInfoEditModel model;



    public CompanyInfoEditPresenter(CompanyInfoEditActivity activity) {
        this.activity=activity;
        model=new CompanyInfoEditModel(activity);
    }

    public void setData(){
        User user=model.getData();
        if(!StringUtils.isEmpty(user.getProvince())){
            activity.setProvince(user.getProvince().substring(0,user.getProvince().length()-1));
        }
        if(!StringUtils.isEmpty(user.getCity())){
            activity.setCity(user.getCity().substring(0,user.getCity().length()-1));
        }
        if(!StringUtils.isEmpty(user.getProvince())){
            activity.setRegion(user.getRegion().substring(0,user.getRegion().length()-1));
        }
        activity.setDescribes(user.getDescribes());
        activity.setEmail(user.getEmail());
        activity.setLabel(user.getLabel());
        activity.setLinkMan(user.getLink_man());
        activity.setLinkPhone(user.getLink_phone());
        activity.setName(user.getName());
        activity.setDetails(user.getDetails());
    }

    public void upload(){
        if(!check()){
            ToastUtil.showToast("请填写正确的信息");
            return ;
        }
        activity.showDialog();
        CompanyInfoBean bean=new CompanyInfoBean();

        bean.setCompanyId(MyApplication.user.getId());
        bean.setTelephone(MyApplication.user.getTelephone());

        bean.setName(activity.getName());
        bean.setLinkPhone(activity.getLinkPhone());
        bean.setLabel(activity.getLabel());
        bean.setLinkMan(activity.getLinkMan());
        bean.setCity(activity.getCity()+"市");
        bean.setDescribes(activity.getDescribes());
        bean.setEmail(activity.getEmail());
        bean.setRegion(activity.getRegion()+"区");
        bean.setProvince(activity.getProvince()+"省");
        bean.setDetails(activity.getDetails());
        bean.setIcon(activity.getIcon());
        bean.setLicense(activity.getLicense());
        //bean.setTax(activity.getTax());
        model.uploadOkHttp3(bean, new MyInfoEditModel.OnUploadListener() {
            @Override
            public void onUploadSuccess(Object obj) {
                CompanyInfoBean bean= (CompanyInfoBean) obj;
                User user= MyApplication.user;
                user.setName(bean.getName());
                user.setLabel(bean.getLabel());
                user.setDetails(bean.getDetails());
                user.setDescribes(bean.getDescribes());
                user.setCity(bean.getCity());
                user.setRegion(bean.getRegion());
                user.setProvince(bean.getProvince());
                user.setLink_man(bean.getLinkMan());
                user.setLink_phone(bean.getLinkPhone());
                user.setEmail(bean.getEmail());
                user.setLicense(bean.getLicense());
                user.setIcno(bean.getIcon());
                activity.dismiss();
                ToastUtil.showToast("上传成功！");
                activity.setResult(CompanyInfoActivity.EDIT_COMPANY_INFO);
                activity.finish();
            }

            @Override
            public void onUploadFail(Call call) {
                activity.dismiss();
                ToastUtil.showToast("上传失败");
            }
        });
    }

    private boolean check(){
        if(activity.getEmail()!=null&&!activity.getEmail().equals("")){
            Log.e("emal",activity.getEmail());
            if (activity.getEmail().contains("@"))
                return true;
            return false;
        }
        return true;
    }

}
