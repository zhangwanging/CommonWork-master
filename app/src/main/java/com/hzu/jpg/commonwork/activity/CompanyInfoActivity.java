package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.Presenter.CompanyInfoPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.widgit.MyLinearLayout;

import java.io.File;


public class CompanyInfoActivity extends AppCompatActivity {

    MyLinearLayout llName;
    MyLinearLayout llTelephone;
    MyLinearLayout llLabel;
    MyLinearLayout llDetails;
    MyLinearLayout llLinkMan;
    MyLinearLayout llLinkPhone;
    MyLinearLayout llEmail;
    MyLinearLayout llLocation;
    TextView tvDescribes;
    TextView tvEdit;
    ImageView ivHead;
    ImageView ivLicense;

    ImageButton ibBack;

    CompanyInfoPresenter presenter;

    public final static int EDIT_COMPANY_INFO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info);

        llDetails= (MyLinearLayout) findViewById(R.id.ll_company_info_details);
        llEmail= (MyLinearLayout) findViewById(R.id.ll_company_info_email);
        llLabel= (MyLinearLayout) findViewById(R.id.ll_company_info_label);
        llLinkMan= (MyLinearLayout) findViewById(R.id.ll_company_info_linkman);
        llLinkPhone= (MyLinearLayout) findViewById(R.id.ll_company_info_link_phone);
        llName= (MyLinearLayout) findViewById(R.id.ll_company_info_name);
        tvDescribes= (TextView) findViewById(R.id.ll_company_info_describes);
        tvEdit= (TextView) findViewById(R.id.tv_company_info_edit);
        llTelephone= (MyLinearLayout) findViewById(R.id.ll_company_info_telephone);
        llLocation= (MyLinearLayout) findViewById(R.id.ll_company_info_location);

        ivHead= (ImageView) findViewById(R.id.iv_company_info_head);
        ivLicense= (ImageView) findViewById(R.id.iv_company_info_license);
        //ivTax= (ImageView) findViewById(R.id.iv_company_info_tax);

        ibBack= (ImageButton) findViewById(R.id.ib_company_info_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CompanyInfoActivity.this,CompanyInfoEditActivity.class),0);
            }
        });
        presenter=new CompanyInfoPresenter(this);
        presenter.setData();

        Glide.with(this).load(Config.IP+ MyApplication.user.getIcno())
                .error(R.mipmap.ic_head_default)
                .into(ivHead);
        Glide.with(this).load(Config.IP+ MyApplication.user.getLicense())
                .error(R.mipmap.no_picture)
                .into(ivLicense);
//        Glide.with(this).load(Config.IP+ MyApplication.user.getTax())
//                .error(R.mipmap.no_picture)
//                .into(ivTax);
    }

    public void setLocation(String data){
        llLocation.setContent(data);
    }

    public String getName() {
        return llName.getContent();
    }

    public void setName(String data) {
        this.llName .setContent(data);
    }

    public String getTelephone() {
        return llTelephone.getContent();
    }

    public void setTelephone(String data) {
        this.llTelephone.setContent(data);
    }

    public String getLabel() {
        return llLabel.getContent();
    }

    public void setLabel(String data) {
        this.llLabel.setContent(data);
    }

    public String getDetails() {
        return llDetails.getContent();
    }

    public void setDetails(String data) {
        this.llDetails.setContent(data);
    }

    public String getLinkMan() {
        return llLinkMan.getContent();
    }

    public void setLinkMan(String data) {
        this.llLinkMan.setContent(data);
    }

    public String getLinkPhone() {
        return llLinkPhone.getContent();
    }

    public void setLinkPhone(String data) {
        this.llLinkPhone .setContent(data);
    }

    public String getEmail() {
        return llEmail.getContent();
    }

    public void setEmail(String data) {
        this.llEmail .setContent(data);
    }

    public String getDescribes() {
        return tvDescribes.getText().toString();
    }

    public void setDescribes(String data) {
        this.tvDescribes.setText(data);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==EDIT_COMPANY_INFO){
            presenter.setData();
            Glide.with(this).load(Config.IP+ MyApplication.user.getIcno())
                    .error(R.mipmap.ic_head_default)
                    .into(ivHead);
            Glide.with(this).load(Config.IP+MyApplication.user.getLicense())
                    .error(R.mipmap.no_picture)
                    .into(ivLicense);
//            Glide.with(this).load(Config.IP+ MyApplication.user.getTax())
//                    .error(R.mipmap.no_picture)
//                    .into(ivTax);
            setResult(Config.INFO_EDIT);
        }

    }
}
