package com.example.root.wyapp.bean.newsBean;

import java.util.ArrayList;

/**
 * Created by root on 2017/7/20.
 */

public class NewsListsBean {
    ArrayList<NewsListBean> ads;//轮播图数据
    String img;//图片
    String source;
    String title;//标题
    String replyCount;//回帖数
    String interest;//为S 代表是置顶
    String id;//需要使用id请求新闻详情


    @Override
    public String toString() {
        return "NewsListsBean{" +
                "ads=" + ads +
                ", img='" + img + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", replyCount='" + replyCount + '\'' +
                ", interest='" + interest + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<NewsListBean> getAds() {
        return ads;
    }

    public void setAds(ArrayList<NewsListBean> ads) {
        this.ads = ads;
    }
}
