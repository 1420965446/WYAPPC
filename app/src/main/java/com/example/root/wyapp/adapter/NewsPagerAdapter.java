package com.example.root.wyapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/19.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragments;
    ArrayList<String> mStrings;
    public NewsPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> stringArray) {
        super(fm);
        mStrings = stringArray;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments!=null?mFragments.size():0;//判断是否为null
    }

    @Override //提供标题数据
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }


    public void updateData(ArrayList<Fragment> fragments, ArrayList<String> strings) {
        mFragments = fragments;
        mStrings = strings;
        notifyDataSetChanged();
    }
}
