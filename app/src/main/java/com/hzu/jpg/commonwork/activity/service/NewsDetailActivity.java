package com.hzu.jpg.commonwork.activity.service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hzu.jpg.commonwork.R;
import com.hzu.jpg.commonwork.enity.service.NewsVo;

public class NewsDetailActivity extends AppCompatActivity {

    private NewsVo.Data data;
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        data = (NewsVo.Data) this.getIntent().getSerializableExtra("data");
        if (data != null && !data.equals("")) {
            if (data.getHref() != null && !data.getHref().equals("")) {
                mWebview = (WebView) findViewById(R.id.tv_about_right);
                mWebview.loadUrl(data.getHref());
                this.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                WebSettings webSettings = mWebview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptEnabled(true);
                webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                webSettings.setUseWideViewPort(true);//关键点
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webSettings.setDisplayZoomControls(false);
                webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
                webSettings.setAllowFileAccess(true); // 允许访问文件
                webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
                webSettings.setSupportZoom(true); // 支持缩放
                webSettings.setLoadWithOverviewMode(true);
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                int mDensity = metrics.densityDpi;
                Log.d("maomao", "densityDpi = " + mDensity);
                if (mDensity == 240) {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                } else if (mDensity == 160) {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                } else if (mDensity == 120) {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
                } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
                } else {
                    webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
                }
                /**
                 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
                 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
                 */
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            }
        }
    }
}
