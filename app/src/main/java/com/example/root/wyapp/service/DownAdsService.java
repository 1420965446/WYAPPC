package com.example.root.wyapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.root.wyapp.Utils.HashCodeUtil;
import com.example.root.wyapp.bean.AdsBean;
import com.example.root.wyapp.bean.AdsListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownAdsService extends IntentService {

    public static final String DOWNLOAD_SERVICE_DATA = "DOWNLOAD_SERVICE_DATA";

    public DownAdsService() {
        super("DownAdsService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String name = Thread.currentThread().getName();
        if (intent != null) {
            AdsListBean listBean = (AdsListBean) intent.getSerializableExtra(DOWNLOAD_SERVICE_DATA);
            List<AdsBean> ads = listBean.getAds();
            Log.e("TAG", "  :"+listBean.toString()+"ads  : "+ads);

            for (int i = 0; i < ads.size(); i++) {
                AdsBean adsBean = ads.get(i);
                String picUrl = adsBean.getRes_url()[0];
                String fileName = getExternalCacheDir() + "/" + HashCodeUtil.getHashCodeFileName(picUrl) + ".jpg";
                Log.e("TAG", fileName);

                File file = new File(fileName);
                if(file.exists()&&file.length()>0) {
                    continue;
                }
                downloadPic(picUrl);
            }

        }
    }

    private void downloadPic(final String picUrl) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(picUrl).build();
        Log.e("TAG",picUrl+ "=====requestS："+request);
        Call call = okHttpClient.newCall(request);
        Log.e("TAG", "call："+call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(DownAdsService.this, "下载图片失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean successful = response.isSuccessful();
                if(!successful) {
                    return;
                }
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                String fileName = getExternalCacheDir() + "/" + HashCodeUtil.getHashCodeFileName(picUrl) + ".jpg";
                File file = new File(fileName);
               // if(!file.exists())
               //     file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            }
        });

    }

}
