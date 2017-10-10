package com.stevengo.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.stevengo.myapplication.Base.BaseApi;
import com.stevengo.myapplication.Base.ConsTable;
import com.stevengo.myapplication.entitys.ChatMessage;
import com.stevengo.myapplication.entitys.Parameter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by StevenGo on 2017/10/3.
 */

public class InternetUtil {
    private static final int RECEIVE_NULL=000000;
    private static final int DEFAULT_TIMEOUT=3*1000;
    public static ChatMessage doPost(Parameter parameter){
        //记录从互联上网得到的所有音乐的信息
        ChatMessage messageReceive=null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                //添加baseUrl
                .baseUrl(ConsTable.URL_BASE)
                //添加转化器，将json映射到对象
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        BaseApi apiBase =retrofit.create(BaseApi.class);
        Call<ChatMessage> musicEntityCall= apiBase.getMessage(parameter.getKEY(),parameter.getInfo(),parameter.getUserId());

        //同步处理Call，将json转换成对象
        try{
            messageReceive=musicEntityCall.execute().body();
            messageReceive.setDate(DateUtil.getCurrentDate());
            if(messageReceive==null){
                messageReceive=new ChatMessage(RECEIVE_NULL,"对不起，没有找到相关信息",DateUtil.getCurrentDate());
            }
            if(messageReceive.getText()==null){
                messageReceive.setText("对不起，没有找到相关信息");
            }
        }catch (IOException e){
            messageReceive=new ChatMessage(RECEIVE_NULL,"网络链接不畅，请稍候再试",DateUtil.getCurrentDate());
        }
        return messageReceive;
    }
    public static boolean isInternetConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络标志初始化为false
        boolean flag=false;
        if(connectivityManager!=null){
            //获取所有连接
            NetworkInfo[] netWorkInfos=connectivityManager.getAllNetworkInfo();
            if (netWorkInfos != null) {
                //遍历连接
                for (int i = 0; i < netWorkInfos.length; i++) {
                    //当有连接的时候把标志置为true
                    if (netWorkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        flag=true;
                    }
                }
            }
        }
        //判断有无网络连接
        if(!flag) {
            return false;
        }else{
            return true;
        }
    }
}
