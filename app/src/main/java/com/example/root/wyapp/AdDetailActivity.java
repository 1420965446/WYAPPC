package com.example.root.wyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AdDetailActivity extends AppCompatActivity {

    public static final String AD_DETAIL_URL = "AD_DETAIL_URL";
    private WebView addetail_webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_detail);
        addetail_webview = (WebView)findViewById(R.id.addetail_webview);
        Intent intent = getIntent();
        String linkUrl = "";
        if(intent!=null) {
            linkUrl = intent.getStringExtra(AD_DETAIL_URL);
        }
        addetail_webview.setWebViewClient(new WebViewClient());
        addetail_webview.getSettings().setJavaScriptEnabled(true);
        addetail_webview.loadUrl(linkUrl);
    }
    @Override
    public void onBackPressed() {
        if(addetail_webview.canGoBack()){
            //如果webView能够后退到上一个页面
            //就后退呗
            addetail_webview.goBack();
        }else{
            //否则
            super.onBackPressed();
        }
    }
}
