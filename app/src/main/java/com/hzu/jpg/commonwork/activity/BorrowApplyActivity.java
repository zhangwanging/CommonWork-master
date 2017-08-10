package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.Receiver.BorrowApplyResultReceiver;
import com.hzu.jpg.commonwork.enity.moudle.BorrowModel;
import com.hzu.jpg.commonwork.utils.JsonUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;


public class BorrowApplyActivity extends AppCompatActivity {

    EditText etMoney;
    EditText etName;
    EditText etCompany;
    EditText etId;
    EditText etPurpose;

    Button btBorrow;

    BorrowModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_borrow);
        setBack();
        etCompany= (EditText) findViewById(R.id.et_borrow_apply_company);
        etId= (EditText) findViewById(R.id.et_borrow_apply_id);
        etMoney= (EditText) findViewById(R.id.et_borrow_apply_money);
        etName= (EditText) findViewById(R.id.et_borrow_apply_username);
        etPurpose= (EditText) findViewById(R.id.et_borrow_apply_purpose);
        btBorrow= (Button) findViewById(R.id.bt_borrow);

        model=new BorrowModel(this);

        btBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money=etMoney.getText().toString();
                String name=etName.getText().toString();
                String company=etCompany.getText().toString();
                String id=etId.getText().toString();
                String purpose=etPurpose.getText().toString();

                boolean ok=true;

                if (money.trim().equals("")) ok=false;
                if (name.trim().equals("")) ok=false;
                if (company.trim().equals("")) ok=false;
                if (id.trim().equals("")) ok=false;
                if(purpose.trim().equals("")) ok=false;
                if(ok){
                    model.borrow(money, name, company, id, purpose, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Log.d("borrow response",s);
                            BorrowApplyResultReceiver receiver= (BorrowApplyResultReceiver) JsonUtil.Json2Object(s, BorrowApplyResultReceiver.class);
                            if(receiver.getResult().equals("申请成功")){
                                ToastUtil.showToast("申请成功");
                                BorrowApplyActivity.this.setResult(BorrowRecordActivity.BORROW_APPLY);
                                BorrowApplyActivity.this.finish();
                            }else{
                                ToastUtil.showToast(receiver.getResult());
                            }
                        }
                    });
                }else{
                    Toast.makeText(BorrowApplyActivity.this,"请填写完整的信息",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void setBack(){
        ImageButton ibBack= (ImageButton) findViewById(R.id.ib_borrow_apply_back);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
