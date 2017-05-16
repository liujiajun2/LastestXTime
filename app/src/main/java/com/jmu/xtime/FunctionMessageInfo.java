package com.jmu.xtime;

import android.graphics.drawable.Drawable;

/**
 * Created by qing on 2017/5/13.
 */

public class FunctionMessageInfo {
    private String textLogin;
    private Drawable iconLogin;
    public FunctionMessageInfo(String text, Drawable icon){
        this.textLogin = text;
        this.iconLogin = icon;
    }
    public void setTextLogin(String textLogin){
        this.textLogin =textLogin;
    }
    public  String getTextLogin(){
        return textLogin;
    }
    public void seticonLogin(Drawable iconLogin){
        this.iconLogin =iconLogin;
    }
    public  Drawable getIconLogin(){
        return iconLogin;
    }

}
