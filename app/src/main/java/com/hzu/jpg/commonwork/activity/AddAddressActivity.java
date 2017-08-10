package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.enity.Address;
import com.hzu.jpg.commonwork.enity.AddressStatus;
import com.hzu.jpg.commonwork.utils.ToastUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhutao on 2017/8/2 0002.
 */

public class AddAddressActivity extends AppCompatActivity {

    private EditText mEtAddr;
    private ImageButton mBack;
    private Button mBtnSave;
    private RequestAction action;
    private AddressStatus mStatus;
    private String addrId;
    private String address;
    private int userId;
    private Address addr;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        action = new RequestAction(this);
        addr = (Address) getIntent().getSerializableExtra("addr");
        initView();
        if (addr != null) {
            isEdit = true;
            mEtAddr.setText(addr.getAddress());
        }
    }

    private void initView() {
        mBack = (ImageButton) findViewById(R.id.ib_setting_back);
        mEtAddr = (EditText) findViewById(R.id.et_address);
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(mEtAddr.getText().toString().trim())) {
                    ToastUtil.showToast("请输入收货地址！");
                    return;
                }
                List<NameValuePair> params = new ArrayList<>();
                NameValuePair ordreAddr = new BasicNameValuePair("orderAddr", mEtAddr.getText().toString());
                if (isEdit) {
                    NameValuePair addrId = new BasicNameValuePair("addrId", addr.getAddr_id());
                    params.add(addrId);
                }
                params.add(ordreAddr);
                mStatus = action.addOrModifyAddress(params);
                if (mStatus.getAddStatus() == 1 || mStatus.getUpdateStatus() == 1) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtil.showToast(isEdit ? "修改收货地址失败！" : "添加收货地址失败！");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
