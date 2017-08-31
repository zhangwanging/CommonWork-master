package com.hzu.jpg.commonwork.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.MyRvAdapter;
import com.hzu.jpg.commonwork.enity.Bean.BorrowBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class BorrowRvHolder extends MyRvHolder{

    TextView tvBorrowDate;
    TextView tvBorrowName;
    TextView tvWorkCompany;
    TextView tvPurpose;
    TextView tvReturnDate;
    TextView tvMoney;
    TextView tvStatus;
    TextView tvJobNumber;
    TextView tvRefuseReason;

    public BorrowRvHolder(View itemView) {
        super(itemView);
        tvBorrowName= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_borrow_name);
        tvBorrowDate= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_borrow_date);
        tvMoney= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_money);
        tvPurpose= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_purpose);
        tvWorkCompany= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_work_company);
        tvReturnDate= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_return_date);
        tvStatus= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_status);
        tvJobNumber= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_job_number);
        tvRefuseReason= (TextView) itemView.findViewById(R.id.tv_item_borrow_record_refuse_reason);
    }

    @Override
    public void setData(Object obj){
        BorrowBean bean= (BorrowBean) obj;
        tvBorrowDate.setText(bean.getBorrowdate());
        tvWorkCompany.setText(bean.getWorkcompany());
        tvPurpose.setText(bean.getPurpose());
        tvMoney.setText(bean.getLoanamount());
        tvBorrowName.setText(bean.getBorrowname());
        tvJobNumber.setText(bean.getJobnumber());

        String status=bean.getLoanstatus();
        tvStatus.setText(status);
        if (status.equals("等待审核")){
            tvStatus.setTextColor(itemView.getContext().getResources().getColor(R.color.colorPrimaryDark));
            tvReturnDate.setVisibility(View.GONE);
        }else if(status.equals("已借款")){
            tvStatus.setTextColor(Color.RED);
            tvReturnDate.setVisibility(View.GONE);
        }else if(status.equals("已还款")){
            tvStatus.setTextColor(Color.GREEN);
            tvReturnDate.setText(bean.getReturndate());
        }
        String refuse=bean.getRefuseReason();
        if(refuse==null||refuse.equals("")){
            tvRefuseReason.setText("无");
        }else{
            tvRefuseReason.setText(refuse);
        }

    }

    @Override
    public void setOnClick(MyRvAdapter.OnRvClickListener listener, Object obj, final int position) {

    }

    @Override
    public Object getOnClickId(Object obj) {
        return null;
    }
}
