package com.stevengo.myapplication.entitys;

/**
 * Created by StevenGo on 2017/10/6.
 */

public class Parameter {
    private final String KEY="100e1c83112f4fb087e2cc4745094143";
    private String info;
    private String userId;
    public Parameter(String info,String UserId){
        this.info=info;
        this.userId=UserId;

    }

    public String getKEY() {
        return KEY;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        userId = userId;
    }
}
