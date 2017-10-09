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
import com.stevengo.myapplication.utils.GeneralUtil;

/**
 * Created by abner on 2016/8/31.
 */
public class WebViewActivity extends Activity {
    public static final String WEB_URL = "url";
    private WebView wv_content;
    private Button mButton;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //沉浸式
        GeneralUtil.fullSceeen(this);
        setContentView(R.layout.activity_webview);
        wv_content = (WebView) findViewById(R.id.id_webview_content);
        mButton=(Button)findViewById(R.id.id_button_back);
        getBundleData();
        initData();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getBundleData() {
        Intent intent = getIntent();
        if(intent!=null){
            url = intent.getStringExtra(WEB_URL);
        }
    }


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
        wv_content.loadUrl(url);
//        wv_content.loadDataWithBaseURL();
    }

}
