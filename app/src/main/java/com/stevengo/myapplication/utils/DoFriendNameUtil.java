package com.stevengo.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by StevenGo on 2017/10/7.
 */

public class DoFriendNameUtil {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private final static String DEFAULT_NAME="StevenGo";
    public static void writeFriendName(Context context,String name){
        preferences=context.getSharedPreferences("friendName",Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putString("friendName",name);
        editor.commit();
    }
    public static String readFriendName(Context context){
        preferences=context.getSharedPreferences("friendName",Context.MODE_PRIVATE);
        String name=preferences.getString("friendName",DEFAULT_NAME);
        return name;
    }
}
