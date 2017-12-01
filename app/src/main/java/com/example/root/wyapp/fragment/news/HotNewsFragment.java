package com.example.root.wyapp.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.root.wyapp.NewsDetailActivity;
import com.example.root.wyapp.R;
import com.example.root.wyapp.Utils.HttpRespone;
import com.example.root.wyapp.Utils.HttpUtil;
import com.example.root.wyapp.Utils.JsonUtil;
import com.example.root.wyapp.adapter.NewsTouListViewAdapter;
import com.example.root.wyapp.bean.newsBean.NewsListBean;
import com.example.root.wyapp.bean.newsBean.NewsListsArrayBean;
import com.example.root.wyapp.bean.newsBean.NewsListsBean;
import com.example.root.wyapp.custom.BannerView;
import com.example.root.wyapp.custom.constant;

import java.io.IOException;
import java.util.ArrayList;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static com.example.root.wyapp.NewsDetailActivity.NEWS_ID;

/**
 * Created by root on 2017/7/19.
 */

public class HotNewsFragment extends Fragment {

    private ListView mLvNews;
    private PtrClassicFrameLayout mPtrFrame;
    public NewsTouListViewAdapter mHotNewsListAdapter;
    private BannerView mBannerView;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", "到了 1");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.frag_hot_news, container, false);
            Log.e("TAG", "到了2");
            mLvNews = (ListView) view.findViewById(R.id.lv_news);
            mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_frame);
            setListView();
        }
        setRefresh();
        return view;
    }

    private void setRefresh() {
        Log.e("TAG", "到了 4");
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(final PtrFrameLayout ptrFrameLayout) {
                //请求最新的数据
                requestData(false);
            }
        });
    }

    private void setListView() {
        Log.e("TAG", "到了 3");
        View footView = View.inflate(getContext(), R.layout.view_foot, null);
        mLvNews.addFooterView(footView);
        mLvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击的监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                NewsListsBean item = (NewsListsBean) parent.getAdapter().getItem(position);
                String id1 = item.getId();
                Log.e("TAG", "id1"+id1);
                intent.putExtra(NEWS_ID,item.getId());
                startActivity(intent);
            }
        });

        mLvNews.setOnScrollListener(new AbsListView.OnScrollListener() {//设置滑动监听
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //整个列表最后的一个条目的位置
                    int lastItemPosition = view.getAdapter().getCount() - 1;
                    //当前可见最后的一个条目
                    int lastVisiblePosition = view.getLastVisiblePosition();
                    if(lastItemPosition==lastVisiblePosition){
                        //请求加载更多数据
                        Log.e(getClass().getSimpleName()+" xmg", "onScrollStateChanged: "+"请求数据");
                        requestData(true);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestData(false);
    }

    private int loadMoreCount = 0;//加载更多次数
    private void requestData(final boolean b) {

        String url = constant.getHotNewsUrl(0,9);
        if(b){
            loadMoreCount++;
            url = constant.getHotNewsUrl(0+loadMoreCount*10,9+loadMoreCount*10);
        }
        HttpUtil.getInstance().getData(url, new HttpRespone() {
            @Override
            public void onFail(IOException e) {
                if(mPtrFrame.isRefreshing()){
                    mPtrFrame.refreshComplete();
                }
            }
            @Override
            public void onSuccess(String result) {
                if (mPtrFrame.isRefreshing()) {
                    mPtrFrame.refreshComplete();
                }
                NewsListsArrayBean newsListBean = JsonUtil.parseJson(result, NewsListsArrayBean.class);
                final ArrayList<NewsListsBean> t1348647909107 = newsListBean.getT1348647909107();
                if (!b) {
                    //先拿走第一条轮播图数据
                    NewsListsBean bannerData = t1348647909107.remove(0);
                    setBanner(bannerData);
                }


                if(mHotNewsListAdapter==null){
                    mHotNewsListAdapter = new NewsTouListViewAdapter(t1348647909107);
                    mLvNews.setAdapter(mHotNewsListAdapter);
                }else{
                    //不是第一次进来了,直接添加数据进来,并刷新
                    if(b){
                        //添加数据
                        mHotNewsListAdapter.addData(t1348647909107);
                    }else{
                        //下拉刷新时,不要直接add,应该将先前的数据清除掉,再add
                        mHotNewsListAdapter.updateData(t1348647909107);
                    }
                }
            }
        });



    }

    public void setBanner(NewsListsBean banner) {
        ArrayList<NewsListBean> ads = banner.getAds();

        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> picUrls = new ArrayList<>();
        for (int i = 0; i < ads.size(); i++) {
            NewsListBean newsListBean = ads.get(i);
            String imgsrc = newsListBean.getImgsrc();
            String title = newsListBean.getTitle();
            titles.add(title);
            picUrls.add(imgsrc);
        }

        if(mBannerView==null){
            mBannerView = new BannerView(getContext());
            //设置数据到BannerView中
            mBannerView.setData(titles,picUrls);
            mLvNews.addHeaderView(mBannerView);
        }else{
            //不是第一次进来,直接对bannerView中数据来进行刷新
            mBannerView.updateData(titles,picUrls);
        }
    }
}

