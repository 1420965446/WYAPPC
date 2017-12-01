package com.example.root.wyapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.root.wyapp.Utils.HashCodeUtil;
import com.example.root.wyapp.Utils.SPUtils;
import com.example.root.wyapp.Utils.statusBarUtils;
import com.example.root.wyapp.bean.AdsBean;
import com.example.root.wyapp.bean.AdsListBean;
import com.example.root.wyapp.custom.SkipView;
import com.example.root.wyapp.service.DownAdsService;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.root.wyapp.AdDetailActivity.AD_DETAIL_URL;
import static com.example.root.wyapp.service.DownAdsService.DOWNLOAD_SERVICE_DATA;

public class MainActivity extends AppCompatActivity {

    private SkipView skip_main;
    private ImageView ad_lists_main;
    public static final String AD_JSON = "AD_JSON";
    public static final String OUT_DATE_TIME = "OUT_DATE_TIME";
    public static final String AD_PIC_INDEX = "AD_PIC_INDEX";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        statusBarUtils.statusBar(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ad_lists_main = (ImageView) findViewById(R.id.ad_lists_main);
        skip_main = (SkipView)findViewById(R.id.skip_main);
        initData();
        initListener();
    }

    private void initListener() {
        skip_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        skip_main.setOnSikpListener(new SkipView.onSikpListener() {
            @Override
            public void onSikp() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    private void initData() {
        String json = SPUtils.getString(MainActivity.this, AD_JSON);
        Long aLong = SPUtils.getLong(MainActivity.this, OUT_DATE_TIME);
        long newTime = System.currentTimeMillis();
        if(json.isEmpty()||newTime>aLong) {
            requestDataOkHttp();
            return;
        }
        //图片→有一个图片的路径→图片的下载地址(hashCode)→遍历javaBean→json缓存
        Log.e("TAG", "路径");
        Gson gson = new Gson();
        AdsListBean listBean = gson.fromJson(json, AdsListBean.class);
        List<AdsBean> ads = listBean.getAds();
        int anInt = SPUtils.getInt(MainActivity.this, AD_PIC_INDEX);
        anInt = anInt % ads.size();
        AdsBean adsBean = ads.get(anInt);
        String s = adsBean.getRes_url()[0];
        String fileName = getExternalCacheDir() + "/" + HashCodeUtil.getHashCodeFileName(s) + ".jpg";
        File file = new File(fileName);
        if(file.exists() && file.length()>0) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            ad_lists_main.setImageBitmap(bitmap);

            anInt++;
            SPUtils.setInt(MainActivity.this,AD_PIC_INDEX,anInt);
            final String link_url = adsBean.getAction_params().getLink_url();
            if (TextUtils.isEmpty(link_url)) {
                return;
            }
            ad_lists_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AdDetailActivity.class);
                    intent.putExtra(AD_DETAIL_URL,link_url);
                    Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    Intent[] intentlist = {homeIntent,intent};
                    startActivities(intentlist);
                    finish();
                }
            });
        }



    }

    private void requestDataOkHttp() {
        Log.e("TAG", "requestDataOkHttp：开始");
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Constant.AD_URL).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if (!successful) {
                    return;
                }
                ResponseBody body = response.body();
                String string = body.string();
                Gson gson = new Gson();
                AdsListBean adsListBean = gson.fromJson(string, AdsListBean.class);

                startDownLoadInBackground(adsListBean);
                Log.e("TAG", "requestDataOkHttp：结束");

            }
        });
    }

    private void startDownLoadInBackground(AdsListBean adsListBean) {

        Gson gson = new Gson();
        String json = gson.toJson(adsListBean);
        SPUtils.setString(MainActivity.this, AD_JSON, json);
        long outTime = System.currentTimeMillis() + adsListBean.getNext_req() * 60000;
        SPUtils.setLong(getApplicationContext(),OUT_DATE_TIME,outTime);
        Log.e("TAG", "启动服务了");
        Intent intent = new Intent(MainActivity.this, DownAdsService.class);
        intent.putExtra(DOWNLOAD_SERVICE_DATA, adsListBean);
        startService(intent);

    }
}
