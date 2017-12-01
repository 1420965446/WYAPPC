package com.example.root.wyapp.Utils;

import android.widget.ImageView;

import com.example.root.wyapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by root on 2017/7/23.
 */

public class ImageUtil {
    private DisplayImageOptions mOptions;

    private ImageUtil() {
        //展示时的配置放在构造方法,可以只创建一次即可
        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_default) // resource or drawable
                .delayBeforeLoading(500)
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();
    }

    private static volatile ImageUtil sInstance;

    public static ImageUtil getSingleton() {
        if (sInstance == null) {
            synchronized (ImageUtil.class) {
                if (sInstance == null) {
                    sInstance = new ImageUtil();
                }
            }
        }
        return sInstance;
    }



    public void showImage(String url, ImageView imageView){
        //        ImageLoader.getInstance().displayImage(url,imageView,mOptions);
        showImage(url,imageView,R.drawable.icon_default);
    }

    private int mLastResId = R.drawable.icon_default;

    public void showImage(String url,ImageView imageView,int resId){
        if(mLastResId!=resId){
            mLastResId = resId;
            mOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(mLastResId) // resource or drawable
                    .delayBeforeLoading(500)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .build();
        }
        ImageLoader.getInstance().displayImage(url,imageView,mOptions);
    }
}
