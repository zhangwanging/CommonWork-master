package com.hzu.jpg.commonwork.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.StatisticsPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.OverTimeRecordActivity;
import com.hzu.jpg.commonwork.activity.StatisticsAddAllowanceActivity;
import com.hzu.jpg.commonwork.activity.StatisticsAddCutActivity;
import com.hzu.jpg.commonwork.activity.StatisticsItemSettingActivity;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.TimeUtil;
import com.hzu.jpg.commonwork.widgit.MyLinearLayout;


/**
 * Created by Administrator on 2017/3/3.
 */

public class StatisticsFragment extends Fragment {

    ListView lv_allowance;
    ListView lv_cut;

    TextView tv_lastMonth;
    TextView tv_date;
    TextView tv_nextMonth;

    TextView tv_totalSalary;
    TextView tv_basic_item;

    TextView tv_allowance_item;
    TextView tv_cut_item;
    TextView tv_help_payment_item;

    MyLinearLayout ll_social_security;
    MyLinearLayout ll_accumulation_fund;
    MyLinearLayout ll_individual_income_tax;
    MyLinearLayout ll_basic_salary;
    MyLinearLayout ll_ot_salary;

    Button bt_add_allowance;
    Button bt_add_cut;
    ImageButton ibBack;

    StatisticsPresenter presenter;

    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        tv_allowance_item = (TextView) rootView.findViewById(R.id.tv_statistics_allowance_item);
        tv_basic_item = (TextView) rootView.findViewById(R.id.tv_statistics_basic_item);
        tv_cut_item = (TextView) rootView.findViewById(R.id.tv_statistics_cut_item);
        tv_help_payment_item = (TextView) rootView.findViewById(R.id.tv_statistics_help_payment_item);
        tv_lastMonth = (TextView) rootView.findViewById(R.id.tv_statistics_last_month);
        tv_date = (TextView) rootView.findViewById(R.id.tv_statistics_date);
        tv_nextMonth = (TextView) rootView.findViewById(R.id.tv_statistics_next_month);
        tv_totalSalary = (TextView) rootView.findViewById(R.id.tv_statistics_total_salary);

        ll_accumulation_fund = (MyLinearLayout) rootView.findViewById(R.id.ll_statistics_accumulation_fund);
        ll_basic_salary = (MyLinearLayout) rootView.findViewById(R.id.ll_statistics_basic_salary);
        ll_individual_income_tax = (MyLinearLayout) rootView.findViewById(R.id.ll_statistics_individual_income_tax);
        ll_social_security = (MyLinearLayout) rootView.findViewById(R.id.ll_statistics_social_security);
        ll_ot_salary = (MyLinearLayout) rootView.findViewById(R.id.ll_statistics_ot_salary);

        lv_allowance = (ListView) rootView.findViewById(R.id.lv_statistics_allowance);
        lv_cut = (ListView) rootView.findViewById(R.id.lv_statistics_cut);

        bt_add_allowance = (Button) rootView.findViewById(R.id.bt_statistics_add_allowance);
        bt_add_cut = (Button) rootView.findViewById(R.id.bt_statistics_add_cut);
        ibBack= (ImageButton) rootView.findViewById(R.id.ib_statistics_back);

        presenter=new StatisticsPresenter(this);

        initView();
        presenter.initData(TimeUtil.getDateYM());
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void initView() {

        if(Config.hourWork){
            ll_ot_salary.setName("出勤工资");
        }

        tv_date.setText(TimeUtil.getDateYM());

        tv_lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date= TimeUtil.getDateYM( tv_date.getText().toString(),-1);
                presenter.initData(date);
                tv_date.setText(date);

            }
        });
        tv_nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date= TimeUtil.getDateYM( tv_date.getText().toString(),+1);
                presenter.initData(date);
                tv_date.setText(date);

            }
        });

        bt_add_allowance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),StatisticsAddAllowanceActivity.class);
                intent.putExtra("date",tv_date.getText().toString());
                startActivityForResult(intent,1);


            }
        });

        bt_add_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),StatisticsAddCutActivity.class);
                intent.putExtra("date",tv_date.getText().toString());
                startActivityForResult(intent,1);

            }
        });

        ll_ot_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_basic_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_social_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.helpItemSetting(v,   tv_date.getText().toString());
            }
        });
        ll_accumulation_fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.helpItemSetting(v,   tv_date.getText().toString());

            }
        });

        ll_individual_income_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.helpItemSetting(v,   tv_date.getText().toString());
            }
        });
        lv_allowance.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.itemSetting(StatisticsItemSettingActivity.CHANGE_ALLOWANCE_ITEM,   tv_date.getText().toString(),view,position);
            }
        });

        lv_cut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.itemSetting(StatisticsItemSettingActivity.CHANGE_CUT_ITEM,  tv_date.getText().toString(),view,position);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OverTimeRecordActivity)getActivity()).toFirstItem();
            }
        });

    }

    public void setDate(String date) {
        tv_date.setText(date);
    }

    public void setTotalSalary(String salary) {
        tv_totalSalary.setText(salary);
    }

    public void setBasicItem(String basicItem) {
        tv_basic_item.setText(basicItem);
    }

    public void setBasicSalary(String salary) {
        ll_basic_salary.setMoney(salary);
    }

    public void setOtSalary(String ot_salary) {
        ll_ot_salary.setMoney(ot_salary);
    }


    public void setAllowanceTotal(String allowanceItem) {
        tv_allowance_item.setText(allowanceItem);
    }

    public void setCutTotal(String cut) {
        tv_cut_item.setText(cut);
    }

    public void setHelpPaymentTotal(String help){
        tv_help_payment_item.setText(help);
    }

    public void setSocialSecurity(String socialSecurity) {
        ll_social_security.setMoney(socialSecurity);
    }

    public void setAccumulation(String accumulation) {
        ll_accumulation_fund.setMoney(accumulation);
    }

    public void setIncomeTax(String incomeTax) {
        ll_individual_income_tax.setMoney(incomeTax);
    }

    public void setLv_allowance(BaseAdapter adapter) {
        lv_allowance.setAdapter(adapter);
    }

    public void setLv_cut(BaseAdapter adapter) {
        lv_cut.setAdapter(adapter);
    }

    public String getAllowanceTotal(){
        return tv_allowance_item.getText().toString();
    }

    public String getCutTotal(){
        return tv_cut_item.getText().toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== StatisticsAddAllowanceActivity.ADD_ALLOWANCE){
            String date=data.getStringExtra("date");
            presenter.setBasicData(date);
            presenter.setAllowanceData(date);
        }else if(resultCode== StatisticsAddCutActivity.ADD_CUT){
            String date=data.getStringExtra("date");
            presenter.setBasicData(date);
            presenter.setCutData(date);
        }else if(resultCode== StatisticsItemSettingActivity.CHANGE_ALLOWANCE_ITEM){
            String date=data.getStringExtra("date");
            int position=data.getIntExtra("position",0);
            double money=data.getDoubleExtra("money",0.0);
            presenter.setBasicData(date);
            presenter.updateAllowance(position,money);
        }else if(resultCode==StatisticsItemSettingActivity.CHANGE_CUT_ITEM){
            String date=data.getStringExtra("date");
            int position=data.getIntExtra("position",0);
            double money=data.getDoubleExtra("money",0.0);
            presenter.setBasicData(date);
            presenter.updateCut(position,money);
        }else if(resultCode==StatisticsItemSettingActivity.CHANGE_HELP_PAYMENT){
            String date=data.getStringExtra("date");
            presenter.setBasicData(date);
            presenter.setHelpPayment(date);
        }
    }
}
