package com.hzu.jpg.commonwork.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.activity.home.MainActivity;
import com.hzu.jpg.commonwork.adapter.TabFragmentAdapter;
import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.app.MyApplication;
import com.hzu.jpg.commonwork.db.DBManager;
import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;
import com.hzu.jpg.commonwork.event.AllLocationEvent;
import com.hzu.jpg.commonwork.fragment.OverTimeRecordFragment;
import com.hzu.jpg.commonwork.fragment.mainFragment.HomeFragment;
import com.hzu.jpg.commonwork.fragment.mainFragment.MessageFragment;
import com.hzu.jpg.commonwork.fragment.mainFragment.PersonalFragment;
import com.hzu.jpg.commonwork.service.LocationService;
import com.hzu.jpg.commonwork.utils.SharedPreferencesUtil;
import com.hzu.jpg.commonwork.utils.ToastUtil;
import com.hzu.jpg.commonwork.widgit.TabItemView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.fangx.common.ui.activity.BaseAppCompatActivity;
import me.fangx.common.util.eventbus.EventCenter;
import me.fangx.common.util.netstatus.NetUtils;
import okhttp3.Call;

public class HomeActivity extends BaseAppCompatActivity {


    private static final String TAG = "HomeActivity";
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    MainActivity homeFragment;
    PersonalFragment personalFragment;

    private TabItemView[] tabItem = new TabItemView[4];
    public TabFragmentAdapter adapter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.aty_home;
    }

    @Override
    protected void initViewsAndEvents() {
        List<Fragment> fragmentList = new ArrayList<>();
        homeFragment = new MainActivity();
        personalFragment = new PersonalFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(personalFragment);
        List<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("个人中心");
        final int[] imgIds = {R.mipmap.ic_home_normal,
                R.mipmap.ic_personal_normal};
        final int[] imgIdsPress = {R.mipmap.ic_home_press,
                R.mipmap.ic_personal_press};
        for (int i = 0; i < titles.size(); i++) {
            tabItem[i] = new TabItemView(HomeActivity.this, titles.get(i),
                    imgIds[i], imgIdsPress[i], getResources().getColor(R.color.black), getResources().getColor(R.color.colorPrimary), i);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(tabItem[i]);
            tabLayout.addTab(tab);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                ((TabItemView) tab.getCustomView()).toggle(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TabItemView) tab.getCustomView()).toggle(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        adapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, titles);
        viewPager.setAdapter(adapter);
        TabLayout.TabLayoutOnPageChangeListener listener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        viewPager.setOnPageChangeListener(listener);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setFitsSystemWindows(true);
        initRegionData();

    }

    private void initRegionData() {
        if (SharedPreferencesUtil.getRegionData().isEmpty()) {
            OkHttpUtils.post().url(Config.URL_SHOW_LOCATION)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.e(TAG, "onResponse: " + response, null);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int status = jsonObject.getInt(Config.KEY_STATU);
                                switch (status) {
                                    case Config.STATUS_SUCCESS:
                                        ObjectMapper mapper = new ObjectMapper();
                                        List<AllCityRegionModel> model = mapper.readValue(jsonObject.getString(Config.KEY_DATA)
                                                , new TypeReference<List<AllCityRegionModel>>() {
                                                });
                                        SharedPreferencesUtil.saveRegionData(model);
                                        break;
                                    case Config.STATUS_FAIL:
                                        ToastUtil.showToast(jsonObject.getString(Config.KEY_MESSAGE));
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            } catch (JsonMappingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
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
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
