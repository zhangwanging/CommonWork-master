package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.db.DBManager;
import com.hzu.jpg.commonwork.enity.FilterEntity;
import com.hzu.jpg.commonwork.enity.FilterTwoEntity;
import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.utils.DataUtil;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.StringUtils;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.RegionPickerDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/2.
 */

public class ResumeActivity extends BaseAppCompatActivity {
    private static final String TAG = "ResumeActivity";
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.RBtn_male)
    RadioButton RBtnMale;
    @Bind(R.id.RBtn_female)
    RadioButton RBtnFemale;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.btn_confirm)
    Button btnConfirm;
    @Bind(R.id.tv_classify)
    TextView tvClassify;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_school)
    TextView tvSchool;
    @Bind(R.id.tv_phoneNum)
    TextView tvPhoneNum;

    List<FilterTwoEntity> jobs;
    RegionPickerDialog dialog;
    private String city = "";
    private String region = "";
    private String classify = "";
    User user = MyApplication.user;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reusume;
    }

    @Override
    protected void initViewsAndEvents() {
        tvTitle.setText("一键求职");
        jobs = DataUtil.getJobs();
        DBManager db = new DBManager(this);
        List<AllCityRegionModel> allLocation = SharedPreferencesUtil.getRegionData();
        List<FilterTwoEntity> allLocationData = DataUtil.getAllLocationData(allLocation);
        if (allLocationData == null || allLocationData.size() > 0) {
            dialog = new RegionPickerDialog(this, allLocationData);
            dialog.setOnItemClickListener(new RegionPickerDialog.OnItemClickListener() {
                @Override
                public void OnItemClick(FilterTwoEntity FirstLeftSelectedEntity, FilterEntity FirstRightSelectedEntity) {
                    Log.e(TAG, "onItemClick: " + FirstRightSelectedEntity.getKey() + "--" + FirstLeftSelectedEntity.getType(), null);
                    tvLocation.setText(FirstLeftSelectedEntity.getType() + "  " + FirstRightSelectedEntity.getKey());
                    city = FirstLeftSelectedEntity.getType();
                    region = FirstRightSelectedEntity.getKey();
                }
            });
        }
        initData();
    }


    private void initData() {
        tvPhoneNum.setText(user.getLink_phone());
        tvName.setText(user.getUsername());
        tvSchool.setText(user.getSchool());
        if (user.getSex().equals("男")) {
            RBtnMale.setChecked(true);
        } else
            RBtnFemale.setChecked(true);
        tvDate.setText(user.getBirthday());
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
        return true;
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

    @OnClick({R.id.back, R.id.tv_location, R.id.btn_confirm, R.id.tv_classify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_location:
                if (dialog != null) {
                    dialog.showDialog();
                }
                break;
            case R.id.btn_confirm:
                if (checkMsg()) {
                    OkHttpUtils.post().url(Config.URL_ONE_KEY_APPLY_JOB)
                            .addParams(Config.KEY_CITY, city)
                            .addParams(Config.KEY_REGION, region)
                            .addParams(Config.KEY_CLASSIFY, classify)
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e(TAG, "onError: " + e.getMessage(), null);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(TAG, "onResponse: " + response, null);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.getInt(Config.KEY_STATU);
                                switch (status) {
                                    case Config.STATUS_SUCCESS:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                                        finish();
                                        break;
                                    case Config.STATUS_FAIL:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                break;
        }
    }

    private boolean checkMsg() {
        return StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(region) && StringUtils.isNotEmpty(classify);
    }
}
