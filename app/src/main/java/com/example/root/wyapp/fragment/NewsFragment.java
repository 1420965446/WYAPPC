package com.example.root.wyapp.fragment;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.wyapp.R;
import com.example.root.wyapp.Utils.JsonUtil;
import com.example.root.wyapp.Utils.SPUtils;
import com.example.root.wyapp.adapter.AddTitleAdapter;
import com.example.root.wyapp.adapter.NewsPagerAdapter;
import com.example.root.wyapp.adapter.ShowTitleAdapter;
import com.example.root.wyapp.event.TabHostVisibleEvent;
import com.example.root.wyapp.fragment.news.EmptyFragment;
import com.example.root.wyapp.fragment.news.HotNewsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 2017/7/19.
 */

public class NewsFragment extends Fragment {

    private ViewPager mViewPager;
    private SmartTabLayout viewpagertab;
    private View view;
    private ImageView mIvVa;
    /**
     * 切换栏目
     */
    private TextView mTvChangeTip;
    private ImageButton mIbtnArrow;
    /**
     * 完成
     */
    private TextView mTvChangeDone;
    private ListView mNewsListview;
    private ValueAnimator valueAnimUp;
    private ValueAnimator valueAnimDown;
    private TranslateAnimation translateAnimationShow;
    private TranslateAnimation translateAnimationHide;
    private FrameLayout mFlChangeTitle;
    private View inflate;
    private GridView mGvShowTitle;
    private GridView mGvAddTitle;
    private ShowTitleAdapter showTitleAdapter;
    private AddTitleAdapter addTitleAdapter;
    private NewsPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_news, container, false);
        initView(inflate);
        initChangeTitle();
        initAnim();
        initListener();
        return inflate;
    }

    private void initChangeTitle() {
        View inflate = View.inflate(getContext(), R.layout.view_change_title, null);
        mGvShowTitle = (GridView) inflate.findViewById(R.id.gv_show_title);
        mGvAddTitle = (GridView) inflate.findViewById(R.id.gv_add_title);

        mFlChangeTitle.addView(inflate);

        mGvShowTitle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mTvChangeDone.setVisibility(View.VISIBLE);
                showTitleAdapter.setShowDelete(true);
                return true;
            }
        });

        mGvShowTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean showDelete = showTitleAdapter.isShowDelete();
                if (showDelete) {
                    if (position == 0) {
                        return;
                    }
                    String s = showTitleAdapter.deleteItem(position);
                    addTitleAdapter.addItem(s);
                } else {
                    mIbtnArrow.performClick();
                    mViewPager.setCurrentItem(position);
                }
            }
        });

        mTvChangeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                showTitleAdapter.setShowDelete(false);
            }
        });

        mGvAddTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = addTitleAdapter.deleteItem(position);
                showTitleAdapter.addItem(s);
            }
        });
    }

    private void initAnim() {
        valueAnimUp = ValueAnimator.ofFloat(0, 180).setDuration(500);
        ValueAnimator.AnimatorUpdateListener animatorUp = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mIbtnArrow.setRotation(value);
            }
        };
        valueAnimUp.addUpdateListener(animatorUp);

        valueAnimDown = ValueAnimator.ofFloat(180, 0).setDuration(500);
        valueAnimDown.addUpdateListener(animatorUp);

        Animator.AnimatorListener abcd = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isFinish = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFinish = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

        valueAnimUp.addListener(abcd);
        valueAnimDown.addListener(abcd);

        translateAnimationShow = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1,
                TranslateAnimation.RELATIVE_TO_SELF, 0);
        translateAnimationShow.setDuration(500);

        translateAnimationHide = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -1);
        translateAnimationHide.setDuration(500);


    }

    private boolean isDown = true;//代表箭头是否向下
    private boolean isFinish = true;//代表动画是否播放完了

    private void initListener() {
        mIbtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinish) {
                    return;
                }
                if (isDown) {

                    valueAnimUp.start();
                    mTvChangeTip.setVisibility(View.VISIBLE);
                    mFlChangeTitle.setVisibility(View.VISIBLE);
                    mFlChangeTitle.startAnimation(translateAnimationShow);
                    EventBus.getDefault().post(new TabHostVisibleEvent(false));
                } else {
                    String newsSrting = JsonUtil.listToString(showTitleAdapter.getData());
                    if (!newsSrting.equals(olddata)) {
                        olddata = newsSrting;
                        saveCache();
                        refreshFragment();
                    }
                    valueAnimDown.start();
                    mFlChangeTitle.startAnimation(translateAnimationHide);
                    mTvChangeTip.setVisibility(View.GONE);
                    mFlChangeTitle.setVisibility(View.GONE);
                    EventBus.getDefault().post(new TabHostVisibleEvent(true));
                }
                isDown = !isDown;
            }
        });
    }

    private void saveCache() {
        String showstring = JsonUtil.listToString(showTitleAdapter.getData());
        SPUtils.setString(getActivity(), SHOW_TITLE, showstring);

        String addstring = JsonUtil.listToString(addTitleAdapter.getData());
        SPUtils.setString(getActivity(), ADD_TITLE, addstring);

    }

    private void refreshFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> data = showTitleAdapter.getData();
        Log.e("TAG", "data"+data.toString());
        for(int i = 0; i < data.size(); i++) {
          if(i==0) {
              fragments.add(new HotNewsFragment());
          }else{
              fragments.add(new EmptyFragment());
          }

        }
        adapter.updateData(fragments,data);
        viewpagertab.setViewPager(mViewPager);
    }

    public static final String SHOW_TITLE = "SHOW_TITLE";
    public static final String ADD_TITLE = "ADD_TITLE";
    private String olddata = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<String> showTitleList = new ArrayList<>();
        ArrayList<String> addTitleList = new ArrayList<>();

        String showTitles = SPUtils.getString(getActivity(), SHOW_TITLE);
        String addTitles = SPUtils.getString(getActivity(), ADD_TITLE);

        List<String> showTitleCache = JsonUtil.stringToList(showTitles);
        List<String> addTitleCache = JsonUtil.stringToList(addTitles);

        if (!TextUtils.isEmpty(showTitles) && showTitleCache.size() > 0) {
            showTitleList.addAll(showTitleCache);
            addTitleList.addAll(addTitleCache);
        } else {
            String[] showarray = getResources().getStringArray(R.array.news_titles);
            showTitleList.addAll(Arrays.asList(showarray));

            String[] addarray = getResources().getStringArray(R.array.to_add_news_titles);
            addTitleList.addAll(Arrays.asList(addarray));
        }

        showTitleAdapter = new ShowTitleAdapter(showTitleList);
        mGvShowTitle.setAdapter(showTitleAdapter);
        addTitleAdapter = new AddTitleAdapter(addTitleList);
        mGvAddTitle.setAdapter(addTitleAdapter);

        olddata = JsonUtil.listToString(showTitleAdapter.getData());

        ArrayList<Fragment> fragments = new ArrayList<>();
        String[] stringArray = getResources().getStringArray(R.array.news_titles);
        for (int i = 0; i < showTitleList.size(); i++) {
            if (i == 0) {
                fragments.add(new HotNewsFragment());
            } else {
                fragments.add(new EmptyFragment());
            }
        }

        adapter = new NewsPagerAdapter(getChildFragmentManager(), fragments, showTitleList);
        mViewPager.setAdapter(adapter);
        viewpagertab.setViewPager(mViewPager);


    }

    public boolean onBackPressed() {
        if (showTitleAdapter.isShowDelete()) {
            showTitleAdapter.setShowDelete(false);
            mTvChangeDone.setVisibility(View.GONE);
            return false;
        } else if (!isDown) {
            mIbtnArrow.performClick();
            return false;
        }
        return true;
    }

    private void initView(View inflate) {
        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        viewpagertab = (SmartTabLayout) inflate.findViewById(R.id.viewpagertab);

        mIvVa = (ImageView) inflate.findViewById(R.id.iv_va);
        mTvChangeTip = (TextView) inflate.findViewById(R.id.tv_change_tip);
        mIbtnArrow = (ImageButton) inflate.findViewById(R.id.ibtn_arrow);
        mTvChangeDone = (TextView) inflate.findViewById(R.id.tv_change_done);
        mNewsListview = (ListView) inflate.findViewById(R.id.news_listview);
        mViewPager = (ViewPager) inflate.findViewById(R.id.viewPager);
        mFlChangeTitle = (FrameLayout) inflate.findViewById(R.id.fl_change_title);
    }
}
