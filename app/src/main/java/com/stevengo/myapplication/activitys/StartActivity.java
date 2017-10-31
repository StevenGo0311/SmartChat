package com.stevengo.myapplication.activitys;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.utils.ActiivtyStack;

public class StartActivity extends AppCompatActivity {
    /**等待的时长*/
    private static final int DELAY_MILLIS=3*1000;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将该activity加入activity栈
        ActiivtyStack.getScreenManager().pushActivity(this);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        setContentView(R.layout.activity_start);
        mHandler.sendEmptyMessageDelayed(123,DELAY_MILLIS);
    }
    @Override
    protected void onDestroy() {
        ActiivtyStack.getScreenManager().popActivity(this);
        super.onDestroy();
    }
}
