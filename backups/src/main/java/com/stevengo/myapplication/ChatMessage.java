package com.stevengo.myapplication;

/**
 * Created by StevenGo on 2017/10/3.
 */

public class ChatMessage {
    private int origin;
    private String content;
    public ChatMessage(int origin,String content){
        this.origin=origin;
        this.content=content;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
