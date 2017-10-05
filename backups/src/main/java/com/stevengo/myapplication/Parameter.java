package com.stevengo.myapplication;

/**
 * Created by StevenGo on 2017/10/3.
 */

public class Parameter {
    private final String KEY="9860d2ace4a0479c8f7716811c6a24b5";
    private String info;
    private final String USERID="StevenGo";
    public Parameter(String info){
        this.info=info;
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

    public String getUSERID() {
        return USERID;
    }
}
