package com.example.root.wyapp.bean.newsBean;

import java.io.Serializable;

/**
 * Created by root on 2017/7/23.
 */

public class NewsDetailImageBean implements Serializable {
    String ref;
    String src;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
