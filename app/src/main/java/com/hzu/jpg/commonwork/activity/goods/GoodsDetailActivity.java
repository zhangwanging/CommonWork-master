package com.hzu.jpg.commonwork.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.MyAddressActivity;
import com.hzu.jpg.commonwork.adapter.AddressAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.AddressInfo;
import com.hzu.jpg.commonwork.enity.goods.GoodsVo;
import com.hzu.jpg.commonwork.utils.Constants;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GoodsDetailActivity extends AppCompatActivity {

    @Bind(R.id.ib_company_info_back)
    ImageButton ibCompanyInfoBack;
    @Bind(R.id.tv_company_info_edit)
    TextView tvCompanyInfoEdit;
    @Bind(R.id.back_layout)
    LinearLayout backLayout;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_point)
    TextView tvPoint;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_describes)
    TextView tvDescribes;
    @Bind(R.id.tv_saleamount)
    TextView tvSaleamount;
    @Bind(R.id.duihuan_bt)
    Button duihuanBt;
    @Bind(R.id.jifen_tv)
    TextView jifenTv;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.address_layout)
    LinearLayout addressLayout;
    private GoodsVo detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        detail = (GoodsVo) this.getIntent().getSerializableExtra("detail");
        if (detail != null) {
            tvName.setText(detail.getName());
            tvPoint.setText(detail.getPoint() + "积分");
            tvPrice.setText("市场参考价" + detail.getPrice() + "元");
            tvDescribes.setText(detail.getDescribes());
            tvSaleamount.setText("已销售" + detail.getSaleamount() + "件");
            Glide.with(getApplicationContext())
                    .load(Constants.imageUrl + detail.getPicture())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.image_bg_default)
                    .error(R.mipmap.image_bg_default).into(ivImage);
            jifenTv.setText("共计：" + detail.getPoint() + "积分");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.address != null) {
            tvAddress.setText(Config.address.getAddress());
        }
    }

    @OnClick({R.id.back_layout, R.id.address_layout, R.id.duihuan_bt})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.back_layout:
                finish();
                break;
            case R.id.address_layout:
                Intent intent = new Intent(GoodsDetailActivity.this, MyAddressActivity.class);
                intent.putExtra("isSelector", true);
                startActivity(intent);
                break;
            case R.id.duihuan_bt:
                if (tvAddress.getText().toString().trim().equals("")) {
                    ToastUtil.showToast("请选择收货地址");
                } else {
                    initData();
                }
                break;
        }
    }

    private void initData() {
        String url = "https://www.jiongzhiw.com/HRM/life/createShopOrder.html";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("addr_id", Config.address.getAddr_id())
                .addParams("shopId", detail.getId() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showNetError();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response != null && !"".equals(response)) {
                            ToastUtil.showToast(response);
                            try {
                                JSONObject json = new JSONObject(response);
                                if (json.getInt("exchageStatus") == 1) {
                                    ToastUtil.showToast("兑换成功");
                                    finish();
                                } else {
                                    ToastUtil.showToast("兑换失败");
                                }//{"exchageStatus":1}
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
