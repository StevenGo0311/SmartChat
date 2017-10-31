package com.stevengo.myapplication.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by StevenGo on 2017/10/6.
 * 日期工具
 */

public class DateUtil {
    private final static String DATE_FORM ="MM月dd日 hh:mm:ss";
    /**得到当前日期*/
    public static Date getCurrentDate(){
        Date date=new Date();
        return date;
    }
    /**对数据库中的日期格式进行格式化*/
    public static String formatDate(java.sql.Date data){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_FORM);
        String result=simpleDateFormat.format(data);
        return result;
    }
    /**对日期进行格式化*/
    public static String formatDate(java.util.Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_FORM);
        String result=simpleDateFormat.format(date);
        return result;
    }
}
