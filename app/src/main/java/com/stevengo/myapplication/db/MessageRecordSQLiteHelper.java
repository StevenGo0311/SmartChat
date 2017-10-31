package com.stevengo.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by StevenGo on 2017/10/9.
 * 数据库操作工具
 */

public class MessageRecordSQLiteHelper extends SQLiteOpenHelper {
    /**创建表的sql语句*/
    private final static String CREATE_TABLE="CREATE TABLE MessageRecord(" +
            "msg_code INT NOT NULL," +
            "msg_text VARCHAR(256) NOT NULL," +
            "msg_url VARCHAR(512)," +
            "msg_time DATETIME)";

    public MessageRecordSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行该语句
        sqLiteDatabase.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
