package com.stevengo.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by StevenGo on 2017/10/3.
 */

public interface ApiBase {
    /**采用GET注解，字符串的内容为相对路径*/
    @GET("/openapi/api")
    /**声明从网络读取数据的方法*/
    Call<ChatMessageReceive> getMessage(@Query("key")String chatMessage_key, @Query("info")String chatMessage_info, @Query("userid")String chagMessage_userid);
}
