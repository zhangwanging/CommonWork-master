package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.Presenter.MyInfoPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.widgit.CircleImageView;
import com.hzu.jpg.commonwork.widgit.MyLinearLayout;

import java.io.File;


public class MyInfoActivity extends AppCompatActivity {

    MyLinearLayout ll_telephone;
    MyLinearLayout ll_username;
    MyLinearLayout ll_realname;
    MyLinearLayout ll_sex;
    MyLinearLayout ll_birthday;
    MyLinearLayout ll_idcard;
    MyLinearLayout ll_school;
    MyLinearLayout ll_major;
    MyLinearLayout ll_province;
    MyLinearLayout ll_city;
    MyLinearLayout ll_region;
    MyLinearLayout ll_require;
    MyLinearLayout ll_balance;
    MyLinearLayout ll_bank_card;
    MyLinearLayout ll_point;
    MyLinearLayout ll_sign;
    MyLinearLayout ll_linkPhone;
    MyLinearLayout ll_entry_time;

    TextView tvEdit;

    MyInfoPresenter presenter;

    CircleImageView circleImageView;

    public static final int EMPLOYEE_EDIT_INFO=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        setBack();
        findView();
        if(presenter==null){
            presenter=new MyInfoPresenter(this);
        }
        presenter.setData();
    }

    public void findView(){
        ll_balance= (MyLinearLayout) findViewById(R.id.ll_my_info_balance);
        ll_bank_card= (MyLinearLayout) findViewById(R.id.ll_my_info_bank_card);
        ll_birthday= (MyLinearLayout) findViewById(R.id.ll_my_info_birthday);
        ll_city= (MyLinearLayout) findViewById(R.id.ll_my_info_city);
        ll_idcard= (MyLinearLayout) findViewById(R.id.ll_my_info_idcard);
        ll_major= (MyLinearLayout) findViewById(R.id.ll_my_info_major);
        ll_point= (MyLinearLayout) findViewById(R.id.ll_my_info_point);
        ll_province= (MyLinearLayout) findViewById(R.id.ll_my_info_province);
        ll_realname= (MyLinearLayout) findViewById(R.id.ll_my_info_realname);
        ll_region= (MyLinearLayout) findViewById(R.id.ll_my_info_region);
        ll_require= (MyLinearLayout) findViewById(R.id.ll_my_info_require);
        ll_school= (MyLinearLayout) findViewById(R.id.ll_my_info_school);
        ll_sex= (MyLinearLayout) findViewById(R.id.ll_my_info_sex);
        ll_sign= (MyLinearLayout) findViewById(R.id.ll_my_info_sign);
        ll_telephone= (MyLinearLayout) findViewById(R.id.ll_my_info_telephone);
        ll_username= (MyLinearLayout) findViewById(R.id.ll_my_info_username);
        ll_linkPhone= (MyLinearLayout) findViewById(R.id.ll_my_info_link_phone);
        ll_entry_time= (MyLinearLayout) findViewById(R.id.ll_my_info_entry_time);

        tvEdit= (TextView) findViewById(R.id.tv_my_info_edit);
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyInfoActivity.this,MyInfoEditActivity.class),0);

            }
        });

        circleImageView= (CircleImageView) findViewById(R.id.iv_my_info);

        Glide.with(this).load(Config.IP + MyApplication.user.getIcno())
                .error(R.mipmap.ic_head_default)
                .into(circleImageView);
    }

    public void setLinkPhone(String s){
        ll_linkPhone.setContent(s);
    }

    public  void setEntryTime(String s){
        ll_entry_time.setContent(s);
    }

    public String getTelephone() {
        return ll_telephone.getContent();
    }

    public void setTelephone(String s) {
        this.ll_telephone.setContent(s);
    }

    public void setUsername(String s) {
        this.ll_username.setContent(s);
    }

    public void setSex(String s) {
        this.ll_sex .setContent(s);
    }

    public void setRealname(String s) {
        this.ll_realname .setContent(s);
    }

    public void setBirthday(String s) {
        this.ll_birthday .setContent(s);
    }

    public void setSchool(String s) {
        this.ll_school.setContent(s);
    }

    public void setIdcard(String s) {
        this.ll_idcard.setContent(s);
    }

    public void setMajor(String s) {
        this.ll_major.setContent(s);
    }

    public String getProvince() {
        return ll_province.getContent();
    }

    public void setProvince(String s) {
        this.ll_province .setContent(s);
    }

    public String getCity() {
        return ll_city.getContent();
    }

    public void setCity(String s) {
        this.ll_city .setContent(s);
    }

    public String getRegion() {
        return ll_region.getContent();
    }

    public void setRegion(String s) {
        this.ll_region.setContent(s);
    }

    public String getLl_require() {
        return ll_require.getContent();
    }

    public void setRequire(String s) {
        this.ll_require.setContent(s);
    }

    public String getBalance() {
        return ll_balance.getContent();
    }

    public void setBalance(String s) {
        this.ll_balance.setContent(s);
    }

    public String getBank_card() {
        return ll_bank_card.getContent();
    }

    public void setBank_card(String s) {
        this.ll_bank_card.setContent(s);
    }

    public String getPoint() {
        return ll_point.getContent();
    }

    public void setPoint(String s) {
        this.ll_point.setContent(s);
    }

    public String getSign() {
        return ll_sign.getContent();
    }

    public void setSign(String s) {
        this.ll_sign.setContent(s);
    }

    private void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_my_info_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==EMPLOYEE_EDIT_INFO){
            presenter.setData();
            setResult(Config.INFO_EDIT);
            Glide.with(this).load(Config.IP+MyApplication.user.getIcno())
                    .error(R.mipmap.ic_head_default)
                    .into(circleImageView);
        }
    }
}
