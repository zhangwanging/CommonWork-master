package com.hzu.jpg.commonwork.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/17.
 */

public class AdviceActivity extends BaseAppCompatActivity {
    private static final String TAG = "AdviceActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_suggestion)
    EditText etSuggestion;
    @Bind(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.et_title)
    EditText etTitle;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_advice;
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("建议反馈");
        toolbar.setTitleTextColor(Color.WHITE);
        ButterKnife.bind(this);
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_commit)
    public void onClick() {
       if(checkMsg()){
           OkHttpUtils.post().url(Config.URL_COMPLAINTS)
                   .addParams(Config.KEY_TITLE, etTitle.getText().toString())
                   .addParams(Config.KEY_CONTENT, etSuggestion.getText().toString())
                   .addParams(Config.KEY_LINK_PHONE,etPhoneNumber.getText().toString())
                   .build().execute(new StringCallback() {
               @Override
               public void onError(Call call, Exception e, int id) {
                   ToastUtil.showNetError();
               }

               @Override
               public void onResponse(String response, int id) {
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       int statu = jsonObject.getInt(Config.KEY_STATU);
                       if(statu == Config.STATUS_SUCCESS){
                           ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                           finish();
                       }
                       else
                           ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                   } catch (JSONException e) {
                       e.printStackTrace();
                       ToastUtil.showNetError();
                   }
               }
           });
       }else
           ToastUtil.showToast("请完善信息");

    }

    private boolean checkMsg() {
        return StringUtils.isNotEmpty(etTitle.getText().toString())
                && StringUtils.isNotEmpty(etPhoneNumber.getText().toString())
                && StringUtils.isNotEmpty(etSuggestion.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
