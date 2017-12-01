package com.example.root.wyapp.bean.newsBean;

import java.io.Serializable;

/**
 * Created by root on 2017/7/20.
 */

public class NewsListBean implements Serializable {
    String imgsrc;
    //String tag;
    String title;

    @Override
    public String toString() {
        return "NewsListBean{" +
                "imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
