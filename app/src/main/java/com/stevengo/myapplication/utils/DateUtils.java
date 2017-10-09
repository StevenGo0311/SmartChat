package com.stevengo.myapplication.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by StevenGo on 2017/10/6.
 */

public class DateUtils {
    public static String getDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM月dd日 hh:mm:ss");
        Date date=new Date();
        String result=simpleDateFormat.format(date);
        Log.d("StevenGo",result);
        return result;
    }
}
