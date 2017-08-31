package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.action.RequestAction;
import com.hzu.jpg.commonwork.adapter.AddressAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.Address;
import com.hzu.jpg.commonwork.enity.AddressInfo;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by zhutao on 2017/8/2 0002.
 */

public class MyAddressActivity extends AppCompatActivity {

    private ImageButton mBack;
    private RequestAction action;
    private AddressAdapter mAdapter;
    private AddressInfo mInfo;
    private ListView mListAddr;
    private Button mBtnAdd;
    private int addrNumber = 0;
    private boolean isSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initview();
        isSelector = this.getIntent().getBooleanExtra("isSelector", false);
        action = new RequestAction(this);
        initData();
    }

    private void initview() {
        mBack = (ImageButton) findViewById(R.id.ib_setting_back);
        mListAddr = (ListView) findViewById(R.id.listAddress);
        mBtnAdd = (Button) findViewById(R.id.btn_add_address);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addrNumber >= 10) {
                    ToastUtil.showToast("您已超出限定收货地址数量，不可继续添加新收货地址！");
                    return;
                }
                startActivityForResult(new Intent(MyAddressActivity.this, AddAddressActivity.class), 1);
            }
        });
        mListAddr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address addr = mInfo.getAddrList().get(position);
                if (isSelector) {
                    Config.address = mInfo.getAddrList().get(position);
                    finish();
                } else {
                    Intent intent = new Intent(MyAddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("addr", addr);
                    startActivityForResult(intent, 1);
                }

            }
        });
    }

    private void initData() {
        String url = "https://www.jiongzhiw.com/HRM/gAddr/getGAddrList.html";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null && !"".equals(response)) {
                    Gson gson = new Gson();
                    mInfo = gson.fromJson(response, AddressInfo.class);
                    if (mInfo != null && mInfo.getAmount() > 0) {
                        addrNumber = mInfo.getAmount();
                        mAdapter = new AddressAdapter(MyAddressActivity.this, mInfo.getAddrList());
                        mListAddr.setAdapter(mAdapter);
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mInfo = null;
            initData();
        }
    }
}
