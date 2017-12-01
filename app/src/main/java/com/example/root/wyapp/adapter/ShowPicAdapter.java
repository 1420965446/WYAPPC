package com.example.root.wyapp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/26.
 */

public class ShowPicAdapter extends PagerAdapter {

    ArrayList<ImageView> mArrayList;
    public ShowPicAdapter(ArrayList<ImageView> arrayList) {
        mArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return mArrayList!=null?mArrayList.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = mArrayList.get(position);
        container.addView(imageView);
        return imageView;
    }

//    @Override
//    public Object instantiateItem(View container, int position) {
//        return super.instantiateItem(container, position);
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
