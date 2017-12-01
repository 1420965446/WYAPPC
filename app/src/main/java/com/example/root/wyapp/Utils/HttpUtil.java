package com.example.root.wyapp.Utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by root on 2017/7/20.
 */

public class HttpUtil {

    private static OkHttpClient okHttpClient;

    private HttpUtil() {
        okHttpClient = new OkHttpClient();
    }

    static HttpUtil HttpUtil;

    public static HttpUtil getInstance(){
        if(HttpUtil==null) {
            synchronized (HttpUtil.class){
                if(HttpUtil==null) {
                    HttpUtil = new HttpUtil();
                }
            }
        }

        return HttpUtil;
    }

    public void getData(String url, final HttpRespone tHttpRespone ){
        Log.e("TAG", "");
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tHttpRespone.onFail(e);
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    onFailure(call,new IOException("响应失败"));
                    return;
                }
                final String string = response.body().string();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tHttpRespone.onSuccess(string);
                    }
                });

            }
        });

    }



}
