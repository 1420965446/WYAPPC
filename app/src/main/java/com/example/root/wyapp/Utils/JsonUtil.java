package com.example.root.wyapp.Utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by root on 2017/7/23.
 */

public class JsonUtil {
    static Gson gson = new Gson();

    public static <T> T parseJson(String json,Class<T> clazz){
        if(TextUtils.isEmpty(json)){
            return null;
        }
        T listBean = gson.fromJson(json, clazz);
        return listBean;
    }

    public static String listToString(List<String> list){ //将List集合转为String
        String s = gson.toJson(list);
        return s;
    }

    public static List<String> stringToList(String json){ //将String转为List集合
        List<String> fromJson = gson.fromJson(json, new TypeToken<List<String>>() {}.getType());
        return fromJson;
    }
}
