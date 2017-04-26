package com.jmu.xtime.update.service;

/**
 * Created by asus on 2017/3/21.
 */

public class TokenHolder {
    private static String TOKEN;
    private static String ID;
    private static String USERNAME;
    private static String PASSWORD;

    public static String getToken() {
        return TOKEN;
    }
    public static String getId() {
        return ID;
    }
    public static String getUserName() {
        return USERNAME;
    }
    public static String getPassword() {
        return PASSWORD;
    }
    public static void setUserInfo(String userName,String password){
           USERNAME = userName;
           PASSWORD = password;
    }
    public static void setToken(String id,String token){
           ID= id;
           TOKEN = token;
    }
}
