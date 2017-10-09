package com.stevengo.myapplication.utils;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import com.stevengo.myapplication.activitys.WebViewActivity;

/**
 * Created by abner on 2016/8/31.
 */
public class CustomUrlSpan extends ClickableSpan {

    private Context context;
    private String url;
    public CustomUrlSpan(Context context, String url){
        this.context = context;
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra(WebViewActivity.WEB_URL,url);
        context.startActivity(intent);
    }
}
