package com.hzu.jpg.commonwork.fragment.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.HourWork.Activity.ChooseOverTimeRecordActivity;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.AdviceActivity;
import com.hzu.jpg.commonwork.activity.AgentActivity;
import com.hzu.jpg.commonwork.activity.BorrowRecordActivity;
import com.hzu.jpg.commonwork.activity.CompanyInfoActivity;
import com.hzu.jpg.commonwork.activity.CompanyPublishActivity;
import com.hzu.jpg.commonwork.activity.ContactUsActivity;
import com.hzu.jpg.commonwork.activity.LoginActivity;
import com.hzu.jpg.commonwork.activity.MyFindActivity;
import com.hzu.jpg.commonwork.activity.MyInfoActivity;
import com.hzu.jpg.commonwork.activity.MyInfoEditActivity;
import com.hzu.jpg.commonwork.activity.SalaryFormActivity;
import com.hzu.jpg.commonwork.activity.SettingActivity;
import com.hzu.jpg.commonwork.activity.goods.GoodsActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.event.LoginEvent;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/1/21.
 */

public class PersonalFragment extends Fragment {
    private CircleImageView circleImageView;
    TextView tvName;
    TextView tvTelephone;
    TextView tvBorrow;
    TextView tvPoint;

    ImageButton ibSetting;

    LinearLayout llEmployeeItems;
    LinearLayout llCompanyItems;
    LinearLayout llLoginInfo;

    TextView tvNoLogin;
    TextView bt_center_my_score_shop;

    //学生
    TextView tvFindJob;
    TextView tvSalaryForm;
    TextView tvOtRecord;
    TextView tvInfo;
    TextView tvQrCode;
    TextView tvAdvice;
    TextView tvContactUs;

    //企业
    TextView tvPublish;
    TextView tvCompanyInfo;

    boolean login=false;

    private View contentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.framgent_personal, container,false);
        contentView = inflate;

        bt_center_my_score_shop = (TextView)inflate.findViewById(R.id.bt_center_my_score_shop);
        bt_center_my_score_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login)
                    startActivity(new Intent(getActivity(),GoodsActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });

        circleImageView = (CircleImageView) inflate.findViewById(R.id.iv_center_head);
        ibSetting= (ImageButton) inflate.findViewById(R.id.ib_my_center_employee_setting);

        tvName= (TextView) inflate.findViewById(R.id.tv_center_username);
        tvTelephone= (TextView) inflate.findViewById(R.id.tv_center_phone);
        tvNoLogin= (TextView) inflate.findViewById(R.id.tv_center_no_login);

        //学生
        tvBorrow= (TextView) inflate.findViewById(R.id.tv_center_employee_borrow);
        tvPoint= (TextView) inflate.findViewById(R.id.tv_center_score);
        tvFindJob= (TextView) inflate.findViewById(R.id.bt_center_my_found_job);
        tvSalaryForm= (TextView) inflate.findViewById(R.id.bt_center_my_salary_form);
        tvOtRecord= (TextView) inflate.findViewById(R.id.bt_center_my_ot_record);
        tvInfo= (TextView) inflate.findViewById(R.id.bt_center_my_info);
        tvQrCode= (TextView) inflate.findViewById(R.id.bt_center_my_qr_code);
        tvAdvice = (TextView) inflate.findViewById(R.id.bt_advice);
        tvContactUs = (TextView) inflate.findViewById(R.id.bt_contact_us);

        //企业
        tvPublish= (TextView) inflate.findViewById(R.id.bt_center_my_publish);
        tvCompanyInfo= (TextView) inflate.findViewById(R.id.bt_center_company_info);

        llEmployeeItems= (LinearLayout) inflate.findViewById(R.id.ll_center_employee_items);
        llCompanyItems= (LinearLayout) inflate.findViewById(R.id.ll_center_company_items);
        llLoginInfo= (LinearLayout) inflate.findViewById(R.id.ll_center_login_info);

        tvOtRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ChooseOverTimeRecordActivity.class));
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login)
                    startActivityForResult(new Intent(getActivity(),LoginActivity.class),0);
            }
        });
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(),SettingActivity.class),0);
            }
        });

        tvAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                    startActivity(new Intent(getActivity(),AdviceActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
            }
        });

        if(MyApplication.user==null){
            login=false;
            llLoginInfo.setVisibility(View.GONE);
            llCompanyItems.setVisibility(View.GONE);

            tvNoLogin.setVisibility(View.VISIBLE);
            llEmployeeItems.setVisibility(View.VISIBLE);

        }else{
            onLogin();
        }
        EventBus.getDefault().register(this);
        return inflate;
    }

    public void initEmployButton(){

        bt_center_my_score_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                    startActivity(new Intent(getActivity(),GoodsActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });

        LinearLayout llBorrow= (LinearLayout) contentView.findViewById(R.id.ll_my_borrow);
        llBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                startActivity(new Intent(getActivity(),BorrowRecordActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvFindJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                    startActivity(new Intent(getActivity(),MyFindActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvSalaryForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                startActivity(new Intent(getActivity(),SalaryFormActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                    startActivityForResult(new Intent(getActivity(),MyInfoEditActivity.class),0);
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                    startActivity(new Intent(getActivity(),AgentActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
    }
    public void initCompanyButton(){
        tvCompanyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                startActivityForResult(new Intent(getActivity(),CompanyInfoActivity.class),0);
                else
                    ToastUtil.showToast("请先登录");
            }
        });
        tvPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login)
                startActivity(new Intent(getActivity(),CompanyPublishActivity.class));
                else
                    ToastUtil.showToast("请先登录");
            }
        });
    }

    private void onLogin(){
        Log.d("log",Config.IP+MyApplication.user.getIcno());
        login=true;
        Glide.with(getActivity()).load(Config.IP+MyApplication.user.getIcno())
                .error(R.mipmap.ic_head_default)
                .into(circleImageView);
        if(MyApplication.role==0){
            llEmployeeItems.setVisibility(View.VISIBLE);
            llCompanyItems.setVisibility(View.GONE);
            tvNoLogin.setVisibility(View.GONE);
            llLoginInfo.setVisibility(View.VISIBLE);
            tvPoint.setText(MyApplication.user.getPoint());

            tvName.setText(MyApplication.user.getUsername());
            tvTelephone.setText(MyApplication.user.getTelephone());
            initEmployButton();
        }else if (MyApplication.role==1) {
            llEmployeeItems.setVisibility(View.GONE);
            llCompanyItems.setVisibility(View.VISIBLE);
            tvNoLogin.setVisibility(View.GONE);
            llLoginInfo.setVisibility(View.VISIBLE);

            tvName.setText(MyApplication.user.getName());
            tvTelephone.setText(MyApplication.user.getTelephone());
            initCompanyButton();
        }
    }

    private void onEdit(){
        login=true;
        if(MyApplication.role==0){
            tvName.setText(MyApplication.user.getUsername());
            tvTelephone.setText(MyApplication.user.getTelephone());
        }else if (MyApplication.role==1) {
            tvName.setText(MyApplication.user.getName());
            tvTelephone.setText(MyApplication.user.getTelephone());
        }
        Glide.with(this).load(Config.IP+MyApplication.user.getIcno())
                .error(R.mipmap.ic_head_default)
                .into(circleImageView);
    }

    private void onExit(){
        login=false;
        llEmployeeItems.setVisibility(View.VISIBLE);
        llCompanyItems.setVisibility(View.GONE);
        llLoginInfo.setVisibility(View.GONE);
        tvNoLogin.setVisibility(View.VISIBLE);
        tvPoint.setText("0");
        Log.d("exit","on exit");
        Glide.with(this).load(R.mipmap.ic_head_default)
                .into(circleImageView);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnLogin(LoginEvent loginEvent){
        if (loginEvent.isLogin())
        onLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case Config.LOGIN:
                onLogin();
                break;
            case Config.INFO_EDIT:
                onEdit();
                break;
            case Config.EXIT:
                onExit();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
