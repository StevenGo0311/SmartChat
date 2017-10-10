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
 */

public class SQLiteUtil {
    private static final String SQL_SELECT_DATA_LIMIT="select * from MessageRecord limit ?,?";
    private static final String SQL_INSERT_DATA="insert into MessageRecord(msg_code,msg_text,msg_url,msg_time)" +
            "values(?,?,?,?)";
    private static final String SQL_SELECT_COUNT="select count(*) from MessageRecord";
    private static final String SQL_DELETE_DATA="delete from MessageRecord";
    private static final String DATABASE_NAME="Message.db3";
    public static List<ChatMessage> readChatMessage(Context context, int startIndex){
        List<ChatMessage> chatMessages=new ArrayList<>();
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        String start=String.valueOf(startIndex);
        String length="10";
        //Cursor cursor=sqLiteDatabase.rawQuery(SQL_SELECT_DATA_LIMIT,new String[]{start,length});
        Cursor cursor=sqLiteDatabase.rawQuery("select * from MessageRecord",null);
        while(cursor.moveToNext()){
            Log.d("StevenGo",String.valueOf(cursor.getLong(3)));
            ChatMessage chatMessage=new ChatMessage(cursor.getInt(0),cursor.getString(1),cursor.getString(2),new java.util.Date(cursor.getString(3)));
            chatMessages.add(chatMessage);
        }
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return chatMessages;
    }
    public static void writeChatMessage(Context context,ChatMessage chatMessage){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        sqLiteDatabase.execSQL(SQL_INSERT_DATA,new String[]{String.valueOf(chatMessage.getCode()),chatMessage.getText(),chatMessage.getUrl(),chatMessage.getDate().toString()});
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
    }
    public static int chatMessageNumbers(Context context){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(SQL_SELECT_COUNT,null);
        cursor.moveToFirst();
        int result=cursor.getInt(0);
        if(sqLiteDatabase!=null&&sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return result;
    }
    public static void clearChatMessage(Context context){
        MessageRecordSQLiteHelper messageRecordSQLiteHelper=new MessageRecordSQLiteHelper(context,DATABASE_NAME,null,1);
        SQLiteDatabase sqLiteDatabase=messageRecordSQLiteHelper.getReadableDatabase();
        sqLiteDatabase.execSQL(SQL_DELETE_DATA);
    }

}
