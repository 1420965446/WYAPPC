package com.example.root.wyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.root.wyapp.Utils.statusBarUtils;
import com.example.root.wyapp.custom.MyFragmentTabHost;
import com.example.root.wyapp.event.TabHostVisibleEvent;
import com.example.root.wyapp.fragment.MeFragment;
import com.example.root.wyapp.fragment.NewsFragment;
import com.example.root.wyapp.fragment.TopicFragment;
import com.example.root.wyapp.fragment.VaFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class HomeActivity extends AppCompatActivity {

    public Class[] mFragments = new Class[]{NewsFragment.class, VaFragment.class,
            TopicFragment.class, MeFragment.class};
    public String[] mTabTexts = new String[]{"新闻", "直播", "话题", "我"};
    public int[] mResIds = new int[]{R.drawable.tab_news, R.drawable.tab_va,
            R.drawable.tab_topic, R.drawable.tab_me};

    private MyFragmentTabHost tabHost;
    private ImageView iv;
    private TextView tv;
    private TranslateAnimation translateAnimationShow;
    private TranslateAnimation translateAnimationHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        statusBarUtils.statusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventBus.getDefault().register(this);
        initView();
        initTabHost();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void setTabHostGone(TabHostVisibleEvent tabHostVisibleEvent) {
        boolean isShow = tabHostVisibleEvent.isShow;
        translateAnimationShow = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        translateAnimationShow.setDuration(500);

        translateAnimationHide = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 1);
        translateAnimationHide.setDuration(500);
        if(isShow) {
            tabHost.startAnimation(translateAnimationShow);
        }else{
            tabHost.startAnimation(translateAnimationHide);
        }
        tabHost.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    private void initView() {
        tabHost = (MyFragmentTabHost) findViewById(R.id.tabHost);
    }

    @Override
    public void onBackPressed() {
        if(tabHost.getCurrentTab()==0) {
            Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag("0");
            if(fragmentByTag!=null&&fragmentByTag instanceof NewsFragment) {
                boolean isNeed = ((NewsFragment) fragmentByTag).onBackPressed();
                if(!isNeed) {
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    private void initTabHost() {
        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.fl_content);
        //        tabHost.addTab();
        for (int i = 0; i < mFragments.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(i + "");
            View view = View.inflate(getApplicationContext(), R.layout.item_tab, null);
            iv = (ImageView) view.findViewById(R.id.iv_tab);
            tv = (TextView) view.findViewById(R.id.tv_tab);
            iv.setImageResource(mResIds[i]);
            tv.setText(mTabTexts[i]);
            tabSpec.setIndicator(view);
            tabHost.addTab(tabSpec, mFragments[i], null);
        }

        //        tabHost.setOnTabChangedListener();
    }

}

























