package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class MyService extends Service {

    // 서버와 데이터 주고받기까지 진행.
    private final String TAG = "MyService";
    private final String SERVER_IP = "49.247.30.164";
    private final int SERVER_PORT = 4445;

    private Socket socket;
    DataOutputStream out; // 이 변수는 사용자가 입력한 데이터를 출력할 때 사용합니다.
    DataInputStream in;


    int i = 0;
    public MyService() {
    }



    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            // TODO: 이 메시지를 사용하는 코드. 예: UI 업데이트
//            sendMessageToActivity(message);]

            // ** 쓰레드 필수 **
            // android.os.NetworkOnMainThreadException
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, "Thread run()");

                    // 서버에 전송
                    try {
                        out.writeUTF(message);
                    } catch (IOException e) {

                        // java.lang.RuntimeException: java.io.EOFException
                        // 서버와의 연결 종료
                        Toast.makeText(context, "소켓 연결 끊김( 메세지 전송 중.)", Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException(e);
                    }
//                    catch (EOFException e) {
//
//                        // java.lang.RuntimeException: java.io.EOFException
//                        // 서버와의 연결 종료
//                        Toast.makeText(context, "소켓 연결 끊김", Toast.LENGTH_SHORT).show();
//    //                        throw new RuntimeException(e);
//                    }
                }
            });
            thread.start();
            Log.d(TAG, "Thread start()");



        }
    };



    private void sendMessageToActivity(String message) {
        Intent intent = new Intent("receiveChat");
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate()");
        i++;
        Log.i(TAG,"onCreate() i : "+Integer.toString(i));

        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter("sendChat"));

//        Toast.makeText(this, "service 시작", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MyService.this, MainActivity.class);
//        startActivity(intent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand(Intent intent, int flags, int startId)");
        Toast.makeText(this, "service onStartCommand", Toast.LENGTH_SHORT).show();
//        return Service.START_STICKY;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d(TAG, "Thread run()");

                try {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    in = new DataInputStream(socket.getInputStream());
                    out = new DataOutputStream(socket.getOutputStream());

                    Log.d(TAG, "Socket connect");

                    while (true) { // 무한 루프
                        String data = in.readUTF(); // UTF-8 문자열 읽기
                        // 읽은 데이터를 처리하는 로직
                        System.out.println(data);
                        sendMessageToActivity(data);
                    }

                } catch (IOException e) {

                    Toast.makeText(getApplicationContext(), "소켓 연결 끊김", Toast.LENGTH_SHORT).show();

//                    throw new RuntimeException(e);
                }


            }
        });
        thread.start();
        Log.d(TAG, "Thread start()");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

//        백그라운드 서비스 실행 제한 해제 필요**
        Log.i(TAG, "--------------------------------------------------");
        Log.i(TAG,"onTaskRemoved");
        Toast.makeText(this, "service onTaskRemoved", Toast.LENGTH_SHORT).show();

        Intent restartIntent = new Intent(getApplicationContext(), MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(),
                0, restartIntent, PendingIntent.FLAG_ONE_SHOT );
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Toast.makeText(this, "alarmManager.set", Toast.LENGTH_SHORT).show();

            alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000,
                    pendingIntent);
        }
        else{
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        }

////        if(isRunning){
////
////        }
        Log.i(TAG, "--------------------------------------------------");

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");
        i++;
        Log.i(TAG,"onCreate() i : "+Integer.toString(i));

        Toast.makeText(this, "service onDestroy", Toast.LENGTH_SHORT).show();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);


        try {

            out.close();
            in.close();

            socket.close();

        } catch (IOException e) {
            Log.i(TAG,e.getMessage());
//            throw new RuntimeException(e);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG,"onBind(Intent intent)");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}