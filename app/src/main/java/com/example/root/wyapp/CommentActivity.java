package com.example.root.wyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.root.wyapp.Utils.HttpRespone;
import com.example.root.wyapp.Utils.HttpUtil;
import com.example.root.wyapp.Utils.JsonUtil;
import com.example.root.wyapp.adapter.NewsCommentAdapter;
import com.example.root.wyapp.bean.newsBean.NewsCommentBean;
import com.example.root.wyapp.custom.constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.root.wyapp.NewsDetailActivity.NEWS_ID;

public class CommentActivity extends AppCompatActivity {

    private ListView mListViewReply;
    /**
     * 发送
     */
    private TextView mTvSendReply;
    /**
     * 写跟贴
     */
    private EditText mEtReply;
    private String mNews_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        mNews_id = "";
        if(intent!=null){
            mNews_id = intent.getStringExtra(NEWS_ID);
        }
        initView();
        initData();
    }

    private void initData() {
        String url = constant.getNewsCommentUrl(mNews_id);
        Log.e("TAG", "result:"+url);
        HttpUtil.getInstance().getData(url, new HttpRespone() {
            @Override
            public void onFail(IOException e) {

            }

            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "result:"+result);
                parseJson(result);
            }
        });
    }

    private void parseJson(String result) {
        ArrayList<NewsCommentBean> beanArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray commentIds = jsonObject.getJSONArray("commentIds");
            JSONObject comments = jsonObject.getJSONObject("comments");
            for(int i = 0; i < commentIds.length(); i++) {
                String commentId = commentIds.getString(i);
                if(commentId.contains(",")) {
                    int indexOf = commentId.lastIndexOf(",");
                    commentId = commentId.substring(indexOf + 1);
                }
                JSONObject comment = comments.getJSONObject(commentId);
                NewsCommentBean commentBean = JsonUtil.parseJson(comment.toString(), NewsCommentBean.class);
                beanArrayList.add(commentBean);

            }


            NewsCommentAdapter newsCommentAdapter = new NewsCommentAdapter(beanArrayList);
            Log.e("TAG", "beanArrayList:"+beanArrayList.toString());
            mListViewReply.setAdapter(newsCommentAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        mListViewReply = (ListView) findViewById(R.id.listView_reply);
        mTvSendReply = (TextView) findViewById(R.id.tv_send_reply);
        mEtReply = (EditText) findViewById(R.id.et_reply);
    }
}
