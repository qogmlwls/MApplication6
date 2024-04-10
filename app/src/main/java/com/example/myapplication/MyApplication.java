package com.example.myapplication;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

public class MyApplication extends Application {


    private final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate()");
        Toast.makeText(this, "MyApplication 생성", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG,"onTerminate()");
        Toast.makeText(this, "MyApplication onTerminate", Toast.LENGTH_SHORT).show();

    }



}

