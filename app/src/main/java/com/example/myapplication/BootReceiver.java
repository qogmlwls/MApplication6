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
            Intent m_intent = new Intent(context.getApplicationContext(), MyService.class);
            context.startService(m_intent);

        }

    }

}


