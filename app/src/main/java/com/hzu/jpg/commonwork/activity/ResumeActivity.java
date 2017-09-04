package com.hzu.jpg.commonwork.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.Poi;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.PopupWindowAdapter;
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

import java.util.ArrayList;
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
    private PopupWindow pop;
    private List<String> str = new ArrayList<>();

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reusume;
    }

    @Override
    protected void initViewsAndEvents() {
        str.add("假期工");
        str.add("临时工");
        str.add("兼职");
        str.add("长期工");
        showContactUsPopWin(this, "选择工作类型", str);
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
                } else {
                    ToastUtil.showToast("请选择求职区域或工作类型");
                }
                break;
            case R.id.tv_classify:
                pop.showAtLocation(view, Gravity.CENTER, 0, 0);
                break;
        }
    }

    private boolean checkMsg() {
        return StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(region) && StringUtils.isNotEmpty(classify);
    }

    public void showContactUsPopWin(Context context, String title, List<String> list) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.dialog_add_client_view, null);
        view.getBackground().setAlpha(100);
        // 创建PopupWindow对象
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        View pop_reward_view = view.findViewById(R.id.pop_reward_view);
        TextView title_tv = (TextView) view.findViewById(R.id.title_tv);
        title_tv.setText(title);
        final PopupWindowAdapter adapter = new PopupWindowAdapter(context, list);
        ListView listView = (ListView) view.findViewById(R.id.listContent);
        listView.setAdapter(adapter);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop_reward_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                classify = adapter.getAll().get(i).toString();
                tvClassify.setText(classify);
                pop.dismiss();
            }
        });
    }
}
