package com.hzu.jpg.commonwork.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.MyJobInfoPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.utils.DialogUtil;
import com.hzu.jpg.commonwork.widgit.MyFlowLayout;


public class MyJobInfoActivity extends AppCompatActivity {

    TextView tvTitle;
    TextView tvSalary;
    TextView tvUnit;
    TextView tvCount;
    TextView tvRequired;
    TextView tvLocation;
    TextView tvCompanyName;
    TextView tvConnector;
    TextView tvConnectNumber;
    TextView tvDescribes;
    TextView tvDate;
    TextView tvMoreSalary;
    TextView tvIsFinish;
    MyFlowLayout flowLayout;
    ImageView iv;
    RelativeLayout relativeLayout;

    AlertDialog dialog;

    MyJobInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job_info);
        tvCompanyName= (TextView) findViewById(R.id.tv_my_job_info_company_name);
        tvConnectNumber= (TextView) findViewById(R.id.tv_my_job_info_connector_number);
        tvConnector= (TextView) findViewById(R.id.tv_my_job_info_connector);
        tvCount= (TextView) findViewById(R.id.tv_my_job_info_count);
        tvDate= (TextView) findViewById(R.id.tv_my_job_info_date);
        tvDescribes= (TextView) findViewById(R.id.tv_my_job_info_describes);
        tvLocation= (TextView) findViewById(R.id.tv_my_job_info_location);
        tvRequired= (TextView) findViewById(R.id.tv_my_job_info_require);
        tvSalary= (TextView) findViewById(R.id.tv_my_job_info_salary);
        tvTitle= (TextView) findViewById(R.id.tv_my_job_info_title);
        tvUnit= (TextView) findViewById(R.id.tv_my_job_info_unit);
        tvMoreSalary= (TextView) findViewById(R.id.tv_my_job_info_more_salary);
        tvIsFinish= (TextView) findViewById(R.id.tv_my_job_info_finish);
        relativeLayout= (RelativeLayout) findViewById(R.id.rl_layout_job_info);

        flowLayout= (MyFlowLayout) findViewById(R.id.fl_my_job_info);

        iv= (ImageView) findViewById(R.id.iv_my_job_info_icon);


        String id=getIntent().getStringExtra("id");
        presenter=new MyJobInfoPresenter(this);

        dialog = DialogUtil.showLoadingDialog(this);
        relativeLayout.setVisibility(View.INVISIBLE);
        presenter.setData(id);
    }

    public void setIsFinish(String s){
        if(s.equals("1")){
            tvIsFinish.setTextColor(Color.parseColor("#32CD32"));
            tvIsFinish.setText("招聘中");
        }else{
            tvIsFinish.setTextColor(Color.parseColor("#ff0000"));
            tvIsFinish.setText("招聘结束");
        }
    }

    public void setMoreSalary(String s){
        tvMoreSalary.setText(s);
    }

    public void setJob(String s) {
        tvTitle.setText(s);
    }

    public void setRequired(String s) {
        tvRequired.setText(s);
    }

    public void setNumber(String s) {
        tvCount.setText(s);
    }

    public void setLocation(String s){
        tvLocation.setText(s);
    }

    public void setDate(String s) {
        tvDate.setText(s);
    }

    public void setSalary(String s) {
        tvSalary.setText(s);
    }

    public void setLinkMan(String s) {
        tvConnector.setText(s);
    }

    public void setLinkPhone(String s) {
        tvConnectNumber.setText(s);
    }

    public void setDescribes(String s) {
        tvDescribes.setText(s);
    }

    public void setCname(String s) {
        tvCompanyName.setText(s);
    }

    public void setUnit(String s) {
        tvUnit.setText(s);
    }

    public void setIcon(Bitmap bitmap){
        if(bitmap!=null)
            iv.setImageBitmap(bitmap);
        else
            iv.setImageDrawable(getResources().getDrawable(R.mipmap.no_picture));
    }

    public void setFlowLayoutData(String[] jobLabel){
        for (String label:jobLabel){
            flowLayout.addView(label);
        }
        flowLayout.requestLayout();
    }

    public void cancelProgress(){
        dialog.dismiss();
        relativeLayout.setVisibility(View.VISIBLE);
    }
}
