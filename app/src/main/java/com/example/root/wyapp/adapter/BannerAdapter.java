package com.example.root.wyapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/23.
 */

public class BannerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    ArrayList<ImageView> mImageViews;
    public BannerAdapter(ArrayList<ImageView> imageViews) {
        mImageViews = imageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position = position%mImageViews.size();
        ImageView imageView = mImageViews.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
