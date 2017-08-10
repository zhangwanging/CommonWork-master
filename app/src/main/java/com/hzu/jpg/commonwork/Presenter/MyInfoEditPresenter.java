package com.hzu.jpg.commonwork.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.hzu.jpg.commonwork.activity.MyInfoActivity;
import com.hzu.jpg.commonwork.activity.MyInfoEditActivity;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.enity.Bean.MyInfoBean;
import com.hzu.jpg.commonwork.enity.moudle.MyInfoEditModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import okhttp3.Call;


/**
 * Created by Administrator on 2017/4/7.
 */

public class MyInfoEditPresenter {

    private MyInfoEditActivity activity;
    private MyInfoEditModel model;

    public MyInfoEditPresenter(MyInfoEditActivity activity) {
        this.activity = activity;
        model = new MyInfoEditModel(activity);
    }


    public void setData(User user) {
        if(user==null){
            return ;
        }
        activity.setUsername(user.getUsername());
        activity.setSchool(user.getSchool());
        activity.setRegion(user.getRegion());
        activity.setBankCard(user.getBank_card());
        //activity.setBirthday(user.getBirthday());
        activity.setCity(user.getCity());
        activity.setEntryTime(user.getEntry_time());
        activity.setMajor(user.getMajor());
        activity.setProvince(user.getProvince());
        activity.setRealName(user.getRealname());
        activity.setRequires(user.getRequires());
        activity.setIdCard(user.getIDcard());
        activity.setLinkPhone(user.getLink_phone());
    }

    public void upload() {
        if (!check()) {
            Toast.makeText(activity, "请完善填写必要信息", Toast.LENGTH_LONG).show();
            return;
        }
        if(!checkLegal()){
            Toast.makeText(activity, "请填写正确信息", Toast.LENGTH_LONG).show();
            return;
        }
        activity.showDialog();
        MyInfoBean bean = new MyInfoBean();
        bean.setBank_card(activity.getBankCard());
        bean.setBirthday(activity.getBirthday());
        bean.setSex(activity.getSex());
        bean.setIdCard(activity.getIdCard());
        bean.setSchool(activity.getSchool());
        bean.setRealname(activity.getRealName());
        bean.setCity(activity.getCity());
        bean.setProvince(activity.getProvince());
        bean.setRegion(activity.getRegion());
        bean.setMajor(activity.getMajor());
        bean.setRequires(activity.getRequires());
        bean.setEntry_time(activity.getEntryTime());
        bean.setUsername(activity.getUsername());
        bean.setIcon(activity.getIcon());
        bean.setUserId(MyApplication.user.getId());
        bean.setLinkPhone(activity.getLinkPhone());
        bean.setTelephone(MyApplication.user.getTelephone());
        model.uploadOkHttp3(bean, new MyInfoEditModel.OnUploadListener() {
            @Override
            public void onUploadSuccess(Object obj) {
                MyInfoBean bean= (MyInfoBean) obj;
                User user=MyApplication.user;
                user.setUsername(bean.getUsername());
                user.setRealname(bean.getRealname());
                user.setSex(bean.getSex());
                user.setBirthday(bean.getBirthday());
                user.setIDcard(bean.getIdCard());
                user.setSchool(bean.getSchool());
                user.setMajor(bean.getMajor());
                user.setProvince(bean.getProvince());
                user.setCity(bean.getCity());
                user.setRegion(bean.getRegion());
                user.setRequires(bean.getRequires());
                user.setEntry_time(bean.getEntry_time());
                user.setBank_card(bean.getBank_card());
                user.setLink_phone(bean.getLinkPhone());
                user.setIcno(bean.getIcon());
                activity.setResult(MyInfoActivity.EMPLOYEE_EDIT_INFO);
                activity.finish();
                activity.clearList();
                MyApplication.user.setIs_supplement(1);
                ToastUtil.showToast("上传成功！");
                activity.dismiss();

            }

            @Override
            public void onUploadFail(Call call) {
                ToastUtil.showToast("上传失败");
                Log.d("upload",call.toString());
                activity.dismiss();
            }
        });
    }

    private boolean check() {
        if (activity.getRealName().equals("")) {
            return false;
        }
        /*if (activity.getIdCard().equals("")) {
            return false;
        }*/
        if (activity.getSchool().equals("")) {
            return false;
        }
        if(activity.getLinkPhone().equals("")){
            return false;
        }
        return !(activity.getUsername().equals(""));
    }

    private  boolean checkLegal(){
        if (activity.getIdCard() != null && !"".equals(activity.getIdCard()) && activity.getIdCard().length() < 15)
            return false;
        String birthday=activity.getBirthday();
        if(!birthday.isEmpty()) {
            String ss[] = birthday.split("-");
            int year = Integer.parseInt(ss[0]);
            int month = Integer.parseInt(ss[1]);
            int day = Integer.parseInt(ss[2]);
            if (year > Integer.parseInt(TimeUtil.getDateYM().substring(0, 4))) {
                return false;
            }
            if (month < 1 || month > 12)
                return false;

            if (day < 1 || day > TimeUtil.getDaysOfMonth(month))
                return false;
        }

        return  true;

    }


}
