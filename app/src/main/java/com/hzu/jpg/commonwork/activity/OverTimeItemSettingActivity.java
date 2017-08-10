package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hzu.jpg.commonwork.Presenter.OverTimeItemSettingPresenter;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;


public class OverTimeItemSettingActivity extends AppCompatActivity {

    ListView lv;
    TextView tv;

    OverTimeItemSettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_time_item_setting);
        tv = (TextView) findViewById(R.id.tv_ot_item_setting_title);
        lv = (ListView) findViewById(R.id.lv_ot_item_setting);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        tv.setText(title);

        presenter=new OverTimeItemSettingPresenter(this,title);
        presenter.setData();

        setBack();
    }

    public void setLv(BaseAdapter adapter) {
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(presenter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 0) {
            int position = data.getIntExtra("position", -1);
            double d = data.getDoubleExtra("money", 0.0);
            if (position != -1) {
                presenter.update(position, d);
                setResult(OverTimeRecordFragment.CHANGE_OT_SETTING);
            }
        }
    }
    public void setBack(){
        ImageButton ib= (ImageButton) findViewById(R.id.ib_over_time_item_setting_back);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
