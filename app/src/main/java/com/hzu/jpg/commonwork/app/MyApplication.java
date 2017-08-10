package com.hzu.jpg.commonwork.app;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;
import com.hzu.jpg.commonwork.enity.moudle.User;
import com.hzu.jpg.commonwork.service.LocationService;
import com.hzu.jpg.commonwork.utils.cookieUtil.ClearableCookieJar;
import com.hzu.jpg.commonwork.utils.cookieUtil.PersistentCookieJar;
import com.hzu.jpg.commonwork.utils.cookieUtil.cache.SetCookieCache;
import com.hzu.jpg.commonwork.utils.cookieUtil.persistence.SharedPrefsCookiePersistor;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/1/21.
 */

public class MyApplication extends Application {

    public static ImageLoader imageLoader = ImageLoader.getInstance();
    public static User user;
    //0 为学生，1 为企业
    public static int role;
    public static List<AllCityRegionModel> allCityRegionModels;
    public LocationService locationService;
    static RequestQueue queue;
    private static final String APP_ID = "wxf23ad2f43650def0";
    public static IWXAPI api;

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {

        mContext = getApplicationContext();

        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);

        Config.CONTEXT = getApplicationContext();
        super.onCreate();

        // 图片缓存配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).discCacheFileCount(100)
                .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
        imageLoader.init(config);
        //初始化百度定位和地图
        locationService = new LocationService(Config.CONTEXT);

        queue = Volley.newRequestQueue(Config.CONTEXT);

        //设置cookie保持自动登陆
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(),
                new SharedPrefsCookiePersistor(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar).build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public static RequestQueue getQueue() {
        return queue;
    }
}
