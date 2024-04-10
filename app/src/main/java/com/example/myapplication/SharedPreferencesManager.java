package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "MyAppPrefs";

    private static final String KEY_LoginStatus = "loginStatus";

    private SharedPreferences sharedPreferences;
    private static SharedPreferencesManager instance;


    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // 인스턴스를 반환하는 public 메서드. 필요한 경우에만 인스턴스를 생성합니다.
    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context);
        }
        return instance;
    }

    public void login() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LoginStatus, true);
        boolean result = editor.commit();
        Log.i("login",Boolean.toString(result));
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_LoginStatus);
        boolean result = editor.commit();
        Log.i("logout",Boolean.toString(result));
    }

    public boolean getLoginStatus() {
        return sharedPreferences.getBoolean(KEY_LoginStatus, false);
    }

}