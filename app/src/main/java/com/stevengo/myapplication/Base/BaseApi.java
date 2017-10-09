package com.stevengo.myapplication.Base;

import com.stevengo.myapplication.entitys.ChatMessage;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by StevenGo on 2017/10/3.
 */

public interface BaseApi {
    /**采用GET注解，字符串的内容为相对路径*/
    @POST(ConsTable.URL_RELATIVE)
    /**声明从网络读取数据的方法*/
    Call<ChatMessage> getMessage(@Query("key") String chatMessage_key, @Query("info") String chatMessage_info, @Query("userid") String chagMessage_userid);
}
