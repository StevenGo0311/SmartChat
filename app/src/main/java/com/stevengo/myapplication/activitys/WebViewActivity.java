package com.stevengo.myapplication.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.utils.ActiivtyStack;
import com.stevengo.myapplication.utils.GeneralUtil;

/**
 * Created by abner on 2016/8/31.
 * 显示网页的页面
 */
public class WebViewActivity extends Activity {
    /**url*/
    public static final String WEB_URL = "url";
    /**布局中定义的webView*/
    private WebView wv_content;
    /**返回按钮*/
    private Button mButton;
    /**url*/
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiivtyStack.getScreenManager().pushActivity(this);
        //沉浸式
        GeneralUtil.fullSceeen(this);
        setContentView(R.layout.activity_webview);
        //从布局文件中得到组件
        wv_content = (WebView) findViewById(R.id.id_webview_content);
        mButton=(Button)findViewById(R.id.id_button_back);
        //得到传递过来的数据
        getBundleData();
        //初始化数据
        initData();
        //给返回按钮设置监听
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    /**取得绑定的数据*/
    private void getBundleData() {
        Intent intent = getIntent();
        if(intent!=null){
            url = intent.getStringExtra(WEB_URL);
        }
    }
    /**初始化数据*/
    private void initData() {
        WebSettings settings = wv_content.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_content.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        //加载页面
        wv_content.loadUrl(url);
//        wv_content.loadDataWithBaseURL();
    }

    @Override
    protected void onDestroy() {
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
