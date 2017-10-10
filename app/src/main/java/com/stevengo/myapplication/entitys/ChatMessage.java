package com.stevengo.myapplication.entitys;

import java.util.Date;
import java.util.List;

/**
 * Created by StevenGo on 2017/10/6.
 */

public class ChatMessage {

    private int code;
    private String text;
    private String url;
    private Date date;
    public ChatMessage(int code,String text,Date date){
        this.code=code;
        this.text=text;
        this.date=date;
    }
    public ChatMessage(int code,String text,String url,Date date){
        this.code=code;
        this.text=text;
        this.url=url;
        this.date=date;
    }
    public ChatMessage(){

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
