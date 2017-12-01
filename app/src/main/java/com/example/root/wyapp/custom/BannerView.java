package com.example.root.wyapp.custom;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.wyapp.R;
import com.example.root.wyapp.Utils.ImageUtil;
import com.example.root.wyapp.adapter.BannerAdapter;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/23.
 */

public class BannerView extends LinearLayout {
    private TextView mTvBanner;
    private LinearLayout mLlDot;
    private ViewPager mViewPagerBanner;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private ArrayList<String> mTitles;
    private ArrayList<String> mPicUrls;

    private void init() {
        View view = View.inflate(getContext(), R.layout.view_banner, this);
        mViewPagerBanner = (ViewPager) view.findViewById(R.id.viewPager_banner);
        mTvBanner = (TextView) view.findViewById(R.id.tv_banner);
        mLlDot = (LinearLayout) view.findViewById(R.id.ll_dot);
        mViewPagerBanner.addOnPageChangeListener(new MyPageChangeListener());
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //翻到下页
                int currentItem = mViewPagerBanner.getCurrentItem();
                currentItem++;
                mViewPagerBanner.setCurrentItem(currentItem);

                this.sendEmptyMessageDelayed(0, time);
            }
        };
    }

    private int time = 2000;
    private Handler mHandler;

    public void setData(ArrayList<String> titles, ArrayList<String> picUrls) {
        mTitles = titles;
        mPicUrls = picUrls;
        //初始化轮播图中的图片
        initImage();
        //初始化轮播图中的小点指示器
        initDot();

        //默认选中第一个小点
        selectDot(0);
        mTvBanner.setText(mTitles.get(0));
        int currentItem = Integer.MAX_VALUE / 2;
        currentItem = currentItem - currentItem % mPicUrls.size();
        mViewPagerBanner.setCurrentItem(currentItem);
        //开始去自动翻页
        mHandler.sendEmptyMessageDelayed(0, time);
    }

    private void initDot() {
        mLlDot.removeAllViews();
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, -2);
        layoutParams.setMargins(0, 0, 10, 0);
        for (int i = 0; i < mTitles.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.bg_dot);
            mLlDot.addView(imageView, layoutParams);
        }
    }

    public void updateData(ArrayList<String> titles, ArrayList<String> picUrls) {
        mTitles = titles;
        mPicUrls = picUrls;
        //初始化轮播图中的图片
        initImage();
        //初始化轮播图中的小点指示器
        initDot();

        //默认选中第一个小点
        selectDot(0);
        mTvBanner.setText(mTitles.get(0));
    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            //通过取余数,就不会越界
            position = position % mPicUrls.size();
            selectDot(position);
            mTvBanner.setText(mTitles.get(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private int mLastIndex = 0;

    private void selectDot(int position) {
        ImageView child = (ImageView) mLlDot.getChildAt(mLastIndex);
        child.setImageResource(R.drawable.bg_dot);
        mLastIndex = position;
        child = (ImageView) mLlDot.getChildAt(position);
        child.setImageResource(R.drawable.bg_dot_selected);
    }

    private void initImage() {
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < mPicUrls.size(); i++) {
            String picUrl = mPicUrls.get(i);
            ImageView imageView = new ImageView(getContext());

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            ImageUtil.getSingleton().showImage(picUrl, imageView);
            imageViews.add(imageView);
        }

        BannerAdapter bannerAdapter = new BannerAdapter(imageViews);
        mViewPagerBanner.setAdapter(bannerAdapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacksAndMessages(null);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.sendEmptyMessageDelayed(0,time);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
