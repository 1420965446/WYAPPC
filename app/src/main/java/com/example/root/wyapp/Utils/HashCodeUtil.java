package com.example.root.wyapp.Utils;

import android.text.TextUtils;

/**
 * Created by root on 2017/7/18.
 */

public class HashCodeUtil {
    public static String getHashCodeFileName(String url) {
        String hashCode = "";
        if (!TextUtils.isEmpty(url)) {
            int i = url.hashCode();
            hashCode = "" + i;
        }
        return hashCode;
    }
}
