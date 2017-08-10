package com.hzu.jpg.commonwork.activity.goods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.goods.GoodsVo;

public class GoodsDetailActivity extends AppCompatActivity {

    private GoodsVo detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        detail = (GoodsVo) this.getIntent().getSerializableExtra("detail");
    }
}
