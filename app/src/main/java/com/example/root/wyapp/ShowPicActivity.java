package com.example.root.wyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.wyapp.Utils.ImageUtil;
import com.example.root.wyapp.adapter.ShowPicAdapter;
import com.example.root.wyapp.bean.newsBean.NewsDetailImageBean;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

public class ShowPicActivity extends AppCompatActivity {

    private int mIndex;
    public static final String SHOW_PIC_SRCLS = "SHOW_PIC_SRCLS";
    public static final String SHOW_PIC_INDEX = "SHOW_PIC_INDEX";
    private ArrayList<NewsDetailImageBean> mImgArrayList;
    private TextView mTvIndex;
    private TextView mTvTotal;
    private ViewPager mViewPagerShowPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        Intent intent = getIntent();
        if(intent!=null){
            mIndex = intent.getIntExtra(SHOW_PIC_INDEX, 0);
            mImgArrayList = (ArrayList<NewsDetailImageBean>) intent.getSerializableExtra(SHOW_PIC_SRCLS);
        }
        initView();
        initData();
    }

    private void initData() {
        ArrayList<ImageView> arrayList = new ArrayList<>();
        for (int i = 0; i < mImgArrayList.size(); i++) {
            PhotoView photoView = new PhotoView(ShowPicActivity.this);
            NewsDetailImageBean newsDetailImageBean = mImgArrayList.get(i);
            ImageUtil.getSingleton().showImage(newsDetailImageBean.getSrc(),photoView);
            arrayList.add(photoView);
        }
        ShowPicAdapter showPicAdapter = new ShowPicAdapter(arrayList);
        mViewPagerShowPic.setAdapter(showPicAdapter);
        mTvIndex.setText(mIndex+1+"/");
        mTvTotal.setText(mImgArrayList.size()+"");
        mViewPagerShowPic.setCurrentItem(mIndex);
    }

    private void initView() {
        mTvIndex = (TextView) findViewById(R.id.tv_index);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mViewPagerShowPic = (ViewPager) findViewById(R.id.viewPager_show_pic);
        mViewPagerShowPic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTvIndex.setText(position+1+"/");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
