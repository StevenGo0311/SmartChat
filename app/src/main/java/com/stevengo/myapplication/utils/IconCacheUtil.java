package com.stevengo.myapplication.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by StevenGo on 2017/10/8.
 * 操作头像的工具
 */

public class IconCacheUtil {
    /**将头像保存到本地文件中*/
    public static boolean writeIcon(Context context, Bitmap bitmap, String iconName){
        boolean flag;
        FileOutputStream fileOutputStream=null;
        try {
            //打开文件输出流
            fileOutputStream=context.openFileOutput(iconName,Context.MODE_PRIVATE);
            //将图片写入文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            flag=true;
        }catch (FileNotFoundException e){
            flag=false;
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**读取头像*/
    public static Bitmap readIcon(Context context,String iconName){
        FileInputStream fileInputStream=null;
        Bitmap bitmap=null;
        try {
            fileInputStream=context.openFileInput(iconName);
            bitmap= BitmapFactory.decodeStream(fileInputStream);
        }catch (FileNotFoundException e){

        }finally {
            try {
                if(fileInputStream!=null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
}
