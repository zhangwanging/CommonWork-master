package com.hzu.jpg.commonwork.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.adapter.cityAdapter.CityListAdapter;
import com.hzu.jpg.commonwork.adapter.cityAdapter.ResultListAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.db.DBManager;
import com.hzu.jpg.commonwork.enity.City;
import com.hzu.jpg.commonwork.enity.LocateState;
import com.hzu.jpg.commonwork.event.AddressEvent;
import com.hzu.jpg.commonwork.service.LocationService;
import com.hzu.jpg.commonwork.widgit.SideLetterBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Author Bro0cL on 2016/12/16.
 */
public class CityPickerActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_PICKED_CITY = "picked_city";
    private static final String TAG = "CityPickerActivity";

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;
    private LocationService locationService;
    private boolean firstLocate = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(!firstLocate){
                handler.sendEmptyMessageDelayed(0,1500);
                startLocate();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        EventBus.getDefault().register(this);
        initData();
        initView();
        startLocate();
//        if(!firstLocate){
//            handler.sendEmptyMessageDelayed(0,1500);
//        }
    }


    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                startLocate();
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
        mCityAdapter.updateLocateState(LocateState.LOCATING,Config.LOCATION_CITY);
    }

    private void startLocate(){
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例
        locationService.registerListener(locationListener);
        //注册监听
        locationService.start();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                back(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    private void back(String city){
        Intent data = new Intent();
        if(!city.endsWith("市"))
            city += "市";
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        Config.SELECTED_CITY = city;
        finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();

        }
    }



    BDLocationListener locationListener = new BDLocationListener() {

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.e(TAG, "onReceiveLocation: " + location.getLocType(), null);
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                String city = location.getCity();
                Log.e(TAG, "onReceiveLocation: " + city, null);
                int type = location.getLocType();

                if (type == BDLocation.TypeGpsLocation
                        || type == BDLocation.TypeNetWorkLocation
                        || type == BDLocation.TypeOffLineLocation) {
                    //定位成功
                    Config.LOCATION_CITY = city;
                    EventBus.getDefault().post(new AddressEvent(city));
                    firstLocate = true;
                } else{
                    //定位失败
                    EventBus.getDefault().post(null);
                }
                locationService.unregisterListener(locationListener); //定位成功后注销定位监听
                locationService.stop(); //定位成功后停止定位服务
            }else{
                Log.e(TAG, "onReceiveLocation: 定位失败", null);
                EventBus.getDefault().post(null);
            }
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            Log.e(TAG, "onConnectHotSpotMessage: " + s, null);
        }
    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setAddressEvent(AddressEvent event){
        if(event != null){
            mCityAdapter.updateLocateState(LocateState.SUCCESS, event.getAddress());
        }else{
            mCityAdapter.updateLocateState(LocateState.FAILED, null);
        }
    }

    @Override
    protected void onDestroy() {
        locationService.unregisterListener(locationListener);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
