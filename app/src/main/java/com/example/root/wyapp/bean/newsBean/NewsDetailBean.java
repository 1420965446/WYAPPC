package com.example.root.wyapp.bean.newsBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 2017/7/23.
 */

public class NewsDetailBean implements Serializable {
    String body;//正文
    String title;//标题
    String source;//来源
    String ptime;//发布的时间
    ArrayList<NewsDetailImageBean> img;

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", ptime='" + ptime + '\'' +
                ", img=" + img +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public ArrayList<NewsDetailImageBean> getImg() {
        return img;
    }

    public void setImg(ArrayList<NewsDetailImageBean> img) {
        this.img = img;
    }
}
