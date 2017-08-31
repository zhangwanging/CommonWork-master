package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.BorrowRecordPresenter;
import com.hzu.jpg.commonwork.R;


public class BorrowRecordActivity extends AppCompatActivity {

    RecyclerView rv;
    BorrowRecordPresenter presenter;
    TextView tvBorrowMoney;
    Button btBorrowApply;

    public final static int BORROW_APPLY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_record);
        init();
        if (presenter == null) {
            presenter = new BorrowRecordPresenter(this);
        }
        presenter.setData();

        this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init() {
        rv = (RecyclerView) findViewById(R.id.rv_my_packet);
        tvBorrowMoney = (TextView) findViewById(R.id.tv_my_packet_borrow);
        btBorrowApply = (Button) findViewById(R.id.bt_borrow_record_apply_borrow);

        btBorrowApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BorrowRecordActivity.this, BorrowApplyActivity.class), 0);
            }
        });

    }

    public void setRvData(RecyclerView.Adapter adapter) {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    public void setBorrowMoney(String money) {
        tvBorrowMoney.setText(money);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BORROW_APPLY) {
            presenter.setData();
        }
    }
}
