package com.stevengo.myapplication;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StevenGo on 2017/10/3.
 */

public class InternetUtil {
    public static ChatMessageReceive doGet(Parameter parameter){
        //记录从互联上网得到的所有音乐的信息
        ChatMessageReceive chatMessageReceive=null;
        Retrofit retrofit=new Retrofit.Builder()
                //添加baseUrl
                .baseUrl("http://www.tuling123.com")
                //添加转化器，将json映射到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiBase apiBase =retrofit.create(ApiBase.class);
        Call<ChatMessageReceive> musicEntityCall= apiBase.getMessage(parameter.getKEY(),parameter.getInfo(),parameter.getUSERID());

        //同步处理Call，将json转换成对象
        try{
            chatMessageReceive=musicEntityCall.execute().body();
            Log.d("StevenGo",chatMessageReceive.getText());
        }catch (IOException e){

        }
        return chatMessageReceive;
    }

}
