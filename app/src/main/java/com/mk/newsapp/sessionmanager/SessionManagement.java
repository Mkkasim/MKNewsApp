package com.mk.newsapp.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    private static SharedPreferences sharedPreferences;

    public SessionManagement() {
    }

    public static void init(Context context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences(SessionConstants.SESSION_NAME, Context.MODE_PRIVATE);
        }
    }

    public static boolean isLogged(){
        return sharedPreferences.getBoolean(SessionConstants.IS_LOGGED_IN, false);

    }

    public static void setLogin(boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SessionConstants.IS_LOGGED_IN, value);
        editor.apply();
    }

    public static void saveUserLoginID(String number){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SessionConstants.USER_LOGIN_ID,number);
        editor.apply();
    }

    public static String  getUserLoginID(){

        return sharedPreferences.getString(SessionConstants.USER_LOGIN_ID,"");

    }


}
