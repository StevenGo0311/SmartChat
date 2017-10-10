package com.stevengo.myapplication.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by StevenGo on 2017/10/6.
 */

public class DateUtil {
    private final static String DATE_FORM ="MM月dd日 hh:mm:ss";
    public static Date getCurrentDate(){
        Date date=new Date();
        return date;
    }
    public static String formatDate(java.sql.Date data){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_FORM);
        String result=simpleDateFormat.format(data);
        return result;
    }
    public static String formatDate(java.util.Date date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_FORM);
        String result=simpleDateFormat.format(date);
        return result;
    }
}
