package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.CompanyMsgAdapter;
import com.hzu.jpg.commonwork.adapter.CompanyVideoAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.CompanyVideoVo;
import com.hzu.jpg.commonwork.enity.moudle.JobMsg;
import com.hzu.jpg.commonwork.utils.GjsonUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/1.
 */

public class CompanyMsgActivity extends AppCompatActivity {

    private static final String TAG = "CompanyMsgActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_companyName)
    TextView tvCompanyName;
    @Bind(R.id.tv_label)
    TextView tvLabel;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_jobDescribe)
    TextView tvJobDescribe;
    @Bind(R.id.img_company)
    ImageView imgCompany;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_next)
    ImageButton btnNext;
    @Bind(R.id.layout_toolbarBg)
    RelativeLayout layoutToolbarBg;
    @Bind(R.id.image_view_1)
    ImageView imageView1;
    @Bind(R.id.layout_view_1)
    RelativeLayout layoutView1;
    @Bind(R.id.image_view_2)
    ImageView imageView2;
    @Bind(R.id.layout_view_2)
    RelativeLayout layoutView2;
    @Bind(R.id.image_view_3)
    ImageView imageView3;
    @Bind(R.id.layout_view_3)
    RelativeLayout layoutView3;
    @Bind(R.id.listAddress)
    ListView listAddress;
    @Bind(R.id.listContent)
    ListView listContent;

    private CompanyMsgAdapter adapter;
    private List<JobMsg> jobMsg = new ArrayList<>();
    private CompanyVideoVo videoVo;
    private CompanyVideoAdapter videoAdapter;
    private View company_msg_detail, company_msg_work, company_msg_video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_company_msg);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        initXmlView();
        initData();
    }

    public void initXmlView() {
        company_msg_detail = findViewById(R.id.company_msg_detail);
        company_msg_work = findViewById(R.id.company_msg_work);
        company_msg_video = findViewById(R.id.company_msg_video);
        company_msg_detail.setVisibility(View.VISIBLE);
        listAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompanyVideoVo.DataBean bean = videoAdapter.getAll().get(i);
                Uri uri = Uri.parse(Config.IP + bean.getVideoUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "video/mp4");
                startActivity(intent);
            }
        });

    }

    private void showImageView(ImageView imageView, View view) {
        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
        company_msg_detail.setVisibility(View.GONE);
        company_msg_work.setVisibility(View.GONE);
        company_msg_video.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.back, R.id.layout_view_1, R.id.layout_view_2, R.id.layout_view_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.layout_view_1:
                showImageView(imageView1, company_msg_detail);
                initData();
                break;
            case R.id.layout_view_2:
                showImageView(imageView2, company_msg_work);
                searchJobSmg();
                break;
            case R.id.layout_view_3:
                showImageView(imageView3, company_msg_video);
                getVideo();
                break;
        }
    }

    private void searchJobSmg() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(Config.KEY_ID);
        OkHttpUtils
                .post()
                .url(Config.IP + "/HRM/capply/gtJobListByCId.html")
                .addParams("pageSize", "10")
                .addParams("startPage", "1")
                .addParams("companyId", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showNetError();
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null || !response.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int totalPage = jsonObject.getInt("totalPage");
                        if (totalPage > 0) {
                            jobMsg = GjsonUtil.jsonToArrayList(jsonObject.getString("data").toString(), JobMsg.class);
                            adapter = new CompanyMsgAdapter(CompanyMsgActivity.this,jobMsg);
                            listContent.setAdapter(adapter);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getVideo() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(Config.KEY_ID);
        OkHttpUtils.post().url(Config.IP + "/HRM/video/gtVListToMTerminal.html")
                .addParams("companyId", id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showNetError();
            }

            @Override
            public void onResponse(String response, int id) {
                if (response != null || !response.isEmpty()) {
                    videoVo = GjsonUtil.parseJsonWithGson(response, CompanyVideoVo.class);
                    if (videoVo.getStatus() == 1) {
                        videoAdapter = new CompanyVideoAdapter(CompanyMsgActivity.this, videoVo.getData());
                        listAddress.setAdapter(videoAdapter);
                    }
                }
            }
        });


    }

    private void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra(Config.KEY_ID);
        OkHttpUtils.post().url(Config.URL_COMPANY_MSG)
                .addParams(Config.KEY_ID, id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " + response, null);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            switch (jsonObject.getInt(Config.KEY_STATU)) {
                                case Config.STATUS_FAIL:
                                    ToastUtil.showToast(Config.KEY_MESSAGE);
                                    break;
                                case Config.STATUS_SUCCESS:
                                    jsonObject = jsonObject.getJSONObject(Config.KEY_DATA);
                                    setData(jsonObject);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: " + e.getMessage(), null);
                        }
                    }
                });
        tvTitle.setText("公司详情");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void setData(JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString(Config.KEY_NAME);
        String label = jsonObject.getString(Config.KEY_LABEL);
        String province = jsonObject.getString(Config.KEY_PROVINCE);
        String city = jsonObject.getString(Config.KEY_CITY);
        String region = jsonObject.getString(Config.KEY_REGION);
        String detail = jsonObject.getString(Config.KEY_DETAILS);
        String describes = jsonObject.getString(Config.KEY_DESCRIBES);
        String url = jsonObject.getString(Config.KEY_ICON);
        Glide.with(this).load(Config.IP + url).into(imgCompany);
        tvCompanyName.setText("null".equals(name) ? "" : name);
        tvLabel.setText("null".equals(label) ? "" : label);
        tvJobDescribe.setText("null".equals(describes) ? "" : describes);
        tvLocation.setText(("null".equals(province) ? "" : province) + " " +
                ("null".equals(city) ? "" : city) + " " + ("null".equals(region) ? "" : region)
                + " " + ("null".equals(detail) ? "" : detail));
        Log.e(TAG, "setData: " + name + label, null);
    }
}
