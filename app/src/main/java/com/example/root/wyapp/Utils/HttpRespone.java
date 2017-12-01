package com.example.root.wyapp.Utils;

import java.io.IOException;

/**
 * Created by root on 2017/7/20.
 */

public interface HttpRespone {
    void onFail(IOException e);
    void onSuccess(String result);
}
