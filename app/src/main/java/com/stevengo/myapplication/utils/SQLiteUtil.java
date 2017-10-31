package com.stevengo.myapplication.utils;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.stevengo.myapplication.db.MessageRecordSQLiteHelper;
import com.stevengo.myapplication.entitys.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StevenGo on 2017/10/9.
 * 数据库操作工具
 */

public class SQLiteUtil {
    private static final String SQL_SELECT_DATA_LIMIT="select * from MessageRecord";
    /**将记录写入数据库*/
    private static final String SQL_INSERT_DATA="insert into MessageRecord(msg_code,msg_text,msg_url,msg_time)" +
            "values(?,?,?,?)";
    /**统计消息数量*/
    private static final String SQL_SELECT_COUNT="select count(*) from MessageRecord";
    /**删除消息记录*/
    private static final String SQL_DELETE_DATA="delete from MessageRecord";
    /**数据库名称*/
    private static final String DATABASE_NAME="Message.db3";
    /**读取消息记录，并将得到的记录转化成list*/
    public static List<ChatMessage> readChatMessage(Context context, int startIndex){
        List<ChatMessage> chatMessages=new ArrayList<>();
        //数据库操作类对象
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        //得到数据库操作对象
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        //执行操作语句
        Cursor cursor=sqLiteDatabase.rawQuery(SQL_SELECT_DATA_LIMIT,null);
        //对得到的消息记录进行转化处理
        while(cursor.moveToNext()){
           // Log.d("StevenGo",String.valueOf(cursor.getLong(3)));
            ChatMessage chatMessage=new ChatMessage(cursor.getInt(0),cursor.getString(1),cursor.getString(2),new java.util.Date(cursor.getString(3)));
            chatMessages.add(chatMessage);
        }
        //如果打开的数据库没有被关闭，则将其关闭
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return chatMessages;
    }
    /**将消息写入数据库*/
    public static void writeChatMessage(Context context,ChatMessage chatMessage){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        //执行sql语句
        sqLiteDatabase.execSQL(SQL_INSERT_DATA,new String[]{String.valueOf(chatMessage.getCode()),chatMessage.getText(),chatMessage.getUrl(),chatMessage.getDate().toString()});
        //如果数据库没有关闭，则将其关闭
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
    }
    /**读取消息记录的数据*/
    public static int chatMessageNumbers(Context context){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(SQL_SELECT_COUNT,null);
        //将光标移动到最开始的位置
        cursor.moveToFirst();
        int result=cursor.getInt(0);
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return result;
    }
    /**清除聊天记录*/
    public static void clearChatMessage(Context context){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        sqLiteDatabase.execSQL(SQL_DELETE_DATA);
    }

}
