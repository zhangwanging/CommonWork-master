package com.hzu.jpg.commonwork.activity.goods;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.GoodsRecordingAdapter;
import com.hzu.jpg.commonwork.enity.GoodsRecordingVo;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsRecordingActivity extends AppCompatActivity {

    @Bind(R.id.ib_company_info_back)
    ImageButton ibCompanyInfoBack;
    @Bind(R.id.tv_company_info_edit)
    TextView tvCompanyInfoEdit;
    @Bind(R.id.back_layout)
    LinearLayout backLayout;
    @Bind(R.id.listContent)
    ListView listContent;

    private GoodsRecordingVo recordingVo;

    private GoodsRecordingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_recording);
        ButterKnife.bind(this);
        initData();
    }

    @OnClick(R.id.ib_company_info_back)
    public void onclick() {
        finish();
    }

    private void initData() {
        String url = "https://www.jiongzhiw.com/HRM/life/getPerOrderInfo.html";
        OkHttpUtils.post().url(url)
                .addParams("method", "android")
                .addParams("startPage", "1")
                .addParams("pageSize", "1000")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null && !"".equals(response)) {
                    Gson gson = new Gson();
                    recordingVo = gson.fromJson(response, GoodsRecordingVo.class);
                    if (recordingVo != null)
                        if (recordingVo.getStatus().equals("1")) {
                            if (recordingVo.getOrderList().size() > 0) {
                                adapter = new GoodsRecordingAdapter(GoodsRecordingActivity.this, recordingVo.getOrderList());
                                listContent.setAdapter(adapter);
                            } else
                                ToastUtil.showToast("暂无订单");
                        } else
                            ToastUtil.showToast("暂无订单");

                }
            }
        });
    }
}
