package com.example.root.wyapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 2017/7/18.
 */

public class AdsListBean implements Serializable {
    List<AdsBean> ads;
    int next_req;

    @Override
    public String toString() {
        return "AdsListBean{" +
                "ads=" + ads +
                ", next_req=" + next_req +
                '}';
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }
}
