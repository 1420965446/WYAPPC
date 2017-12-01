package com.example.root.wyapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.wyapp.Utils.HttpRespone;
import com.example.root.wyapp.Utils.HttpUtil;
import com.example.root.wyapp.Utils.JsonUtil;
import com.example.root.wyapp.bean.newsBean.NewsDetailBean;
import com.example.root.wyapp.bean.newsBean.NewsDetailImageBean;
import com.example.root.wyapp.custom.constant;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.example.root.wyapp.ShowPicActivity.SHOW_PIC_INDEX;
import static com.example.root.wyapp.ShowPicActivity.SHOW_PIC_SRCLS;

public class NewsDetailActivity extends SwipeBackActivity {

    private EditText et_reply;
    private TextView tv_send_reply;
    private TextView tv_reply;
    private ImageView iv_share;
    public static final String NEWS_ID = "NEWS_ID";
    private String mNews_id;
    private NewsDetailBean webView;
    private ArrayList<NewsDetailImageBean> imageBeen;
    private WebView mWebView;
    private Drawable drawableBottom;
    private Drawable drawableft;
    private ImageButton mNewsDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);



        Intent intent = getIntent();
        mNews_id = "";
        if (intent != null) {
            mNews_id = intent.getStringExtra(NEWS_ID);
        }
        initView();
        initData();

        initListener();
    }

    private void initListener() {
        et_reply.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mIsFocused = hasFocus;
                if (hasFocus) {
                    et_reply.setCompoundDrawables(null, null, null, drawableBottom);
                    tv_send_reply.setVisibility(View.VISIBLE);
                    tv_reply.setVisibility(View.GONE);
                    iv_share.setVisibility(View.GONE);
                }
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    et_reply.setCompoundDrawables(drawableft, null, null, drawableBottom);
                    tv_send_reply.setVisibility(View.GONE);
                    tv_reply.setVisibility(View.VISIBLE);
                    iv_share.setVisibility(View.VISIBLE);
                }

            }
        });

        mNewsDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CommentActivity.class);
                intent.putExtra(NEWS_ID,mNews_id);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        et_reply = (EditText) findViewById(R.id.et_reply);
        tv_send_reply = (TextView) findViewById(R.id.tv_send_reply);
        tv_reply = (TextView) findViewById(R.id.tv_reply);
        iv_share = (ImageView) findViewById(R.id.iv_share);
        mWebView = (WebView) findViewById(R.id.webView);
        mNewsDetailImage = (ImageButton) findViewById(R.id.news_detail_image);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(NewsDetailActivity.this, "demo");

        drawableft = getResources().getDrawable(R.drawable.icon_edit_icon);
        drawableBottom = getResources().getDrawable(R.drawable.bg_edit_text);
        //设置一下边界区域大小,不然没东西展示
        drawableft.setBounds(0, 0, drawableft.getIntrinsicWidth(), drawableft.getIntrinsicHeight());
        drawableBottom.setBounds(0, 0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());

    }

    private void initData() {
        String url = constant.getNewsDetailUrl(mNews_id);
        HttpUtil.getInstance().getData(url, new HttpRespone() {
            @Override
            public void onFail(IOException e) {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String string = jsonObject.getString(mNews_id);
                    NewsDetailBean newsDetailBean = JsonUtil.parseJson(string, NewsDetailBean.class);
                    Log.e("TAG", "newsde" + newsDetailBean.toString());
                    setWebView(newsDetailBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean mIsFocused = false;

    @Override
    public void onBackPressed() {
        if (mIsFocused) {
            mWebView.requestFocus();
        } else {
            super.onBackPressed();
        }

    }

    public void setWebView(NewsDetailBean webView) {
        String body = webView.getBody();
        String titleHTML = "<p style = \"margin:25px 0px 0px 0px\"><span style='font-size:22px;'><strong>" + webView.getTitle() + "</strong></span></p>";// 标题
        titleHTML = titleHTML + "<p><span style='color:#999999;font-size:14px;'>" + webView.getSource() + "&nbsp&nbsp" + webView.getPtime() + "</span></p>";//来源与时间
        titleHTML = titleHTML + "<div style=\"border-top:1px dotted #999999;margin:20px 0px\"></div>";//加条虚线
        //设置正文的字体
        body = "<div style='line-height:180%;'><span style='font-size:15px;'>" + body + "</span></div>";
        imageBeen = webView.getImg();
        for (int i = 0; i < imageBeen.size(); i++) {
            NewsDetailImageBean newsDetailImageBean = imageBeen.get(i);
            String ref = newsDetailImageBean.getRef();
            String src = newsDetailImageBean.getSrc();
            body = body.replace(ref, "<img onClick=\"show(" + i + ")\" src=\"" + src + "\"/>");
        }

        body = "<html><head><style>img{width:100%}</style>" +
                "<script type=\"text/javascript\">function show(i){window.demo.showPic(i);}</script>" +
                "</head>" + titleHTML + body + "</html>";
        mWebView.loadData(body, "text/html;charset=UTF-8", null);

    }

    @JavascriptInterface
    public void showPic(int index) {
        Log.e("TAG", "jinlaile");
        Intent intent = new Intent(NewsDetailActivity.this, ShowPicActivity.class);
        intent.putExtra(SHOW_PIC_SRCLS, imageBeen);
        intent.putExtra(SHOW_PIC_INDEX, imageBeen);
        startActivity(intent);
    }
}
