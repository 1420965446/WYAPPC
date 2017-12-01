package com.example.root.wyapp.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by root on 2017/7/18.
 */

public class AdsBean implements Serializable {
    ActionParams action_params;
    String[] res_url;

    @Override
    public String toString() {
        return "AdsBean{" +
                "action_params=" + action_params +
                ", res_url=" + Arrays.toString(res_url) +
                '}';
    }

    public ActionParams getAction_params() {
        return action_params;
    }

    public void setAction_params(ActionParams action_params) {
        this.action_params = action_params;
    }

    public String[] getRes_url() {
        return res_url;
    }

    public void setRes_url(String[] res_url) {
        this.res_url = res_url;
    }
}
