package com.jmu.xtime;

import android.graphics.drawable.Drawable;

/**
 * Created by 倾城一世 on 2017/3/22.
 */

public class MyselfMessageInfo {
    private String textLogin;
    private Drawable iconLogin;
    public MyselfMessageInfo(String text, Drawable icon){
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
