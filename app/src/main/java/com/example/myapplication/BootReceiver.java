package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

            Toast.makeText(context, "BootReceiver", Toast.LENGTH_SHORT).show();
            Log.i(TAG,"ACTION_BOOT_COMPLETED");

            SharedPreferencesManager sharedPreferencesManager = SharedPreferencesManager.getInstance(context.getApplicationContext());

            // 로그인상태라면 메인 페이지로 이동.
            boolean result = sharedPreferencesManager.getLoginStatus();
            Log.i(TAG,"로그인 상태 : "+Boolean.toString(result));

            if(result){
                Intent m_intent = new Intent(context.getApplicationContext(), MyService.class);
                context.startService(m_intent);
                Log.i(TAG,"로그인 상태라서 서비스 실행.");
            }

        }

    }

}


