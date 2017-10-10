package com.stevengo.myapplication.activitys;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.stevengo.myapplication.R;
import com.stevengo.myapplication.db.MessageRecordSQLiteHelper;
import com.stevengo.myapplication.entitys.ChatMessage;
import com.stevengo.myapplication.utils.DateUtil;
import com.stevengo.myapplication.utils.SQLiteUtil;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //SQLiteUtil.writeChatMessage(this,new ChatMessage(11000,"haha", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(10001,"第一条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11002,"第二条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11003,"第三条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11004,"第四条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11005,"第五条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11006,"第六条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11007,"第七条数据", DateUtil.getCurrentDate()));
//        SQLiteUtil.writeChatMessage(this,new ChatMessage(11008,"第八条数据", DateUtil.getCurrentDate()));
//        Log.d("StevenGo","前："+ String.valueOf(SQLiteUtil.chatMessageNumbers(this)));
//        SQLiteUtil.clearChatMessage(this);
        Log.d("StevenGo","后："+String.valueOf(SQLiteUtil.chatMessageNumbers(this)));
        List<ChatMessage> list=SQLiteUtil.readChatMessage(this,1);
        for (int i = 0; i <list.size() ; i++) {
            Log.d("StevenGo","----"+String.valueOf(list.get(i).getCode())+"---"+list.get(i).getText());

        }

    }
}
