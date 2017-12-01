package com.example.root.wyapp.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/23.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    ArrayList<T> mData;
    public MyBaseAdapter(ArrayList<T> data){
        mData = data;
    }

    public ArrayList<T> getData(){
        return mData;
    }

    @Override
    public int getCount() {
        return mData==null?0:mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
