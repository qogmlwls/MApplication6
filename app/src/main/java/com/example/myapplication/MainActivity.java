package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    SharedPreferencesManager sharedPreferencesManager;

    Button 전송버튼, 로그아웃;
    EditText 입력뷰;
    TextView 채팅텍스트뷰;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate()");


        sharedPreferencesManager = SharedPreferencesManager.getInstance(getApplicationContext());

        전송버튼 = findViewById(R.id.button4);
        입력뷰 = findViewById(R.id.editTextText);
        채팅텍스트뷰 = findViewById(R.id.textView1);
        로그아웃 = findViewById(R.id.button);


        로그아웃.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 채팅서버와의 연결 끊기.
                // 로그인 화면으로 이동하기
                sharedPreferencesManager.logout();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);

                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                finish();

            }
        });


        전송버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                // 실행 중인 서비스 목록을 가져옵니다.

                for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                    // 특정 서비스가 목록에 있는지 확인합니다.
                    if (MyService.class.getName().equals(service.service.getClassName())) {
                        Toast.makeText(getApplicationContext(), "실행 중인 서비스", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "실행 x", Toast.LENGTH_SHORT).show();
                    }
                }

                if(activityManager.getRunningServices(Integer.MAX_VALUE).size() == 0){
                    Toast.makeText(getApplicationContext(), "실행 x && size 0", Toast.LENGTH_SHORT).show();
                }

            }
        });



        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        // 실행 중인 서비스 목록을 가져옵니다.

        for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            // 특정 서비스가 목록에 있는지 확인합니다.
            if (MyService.class.getName().equals(service.service.getClassName())) {
                Toast.makeText(getApplicationContext(), "실행 중인 서비스", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "실행 x", Toast.LENGTH_SHORT).show();
            }
        }

        if(activityManager.getRunningServices(Integer.MAX_VALUE).size() == 0){
            Toast.makeText(getApplicationContext(), "실행 x && size 0", Toast.LENGTH_SHORT).show();
        }

        requestDisableBatteryOptimization();


    }
    public void requestDisableBatteryOptimization() {
//        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            String packageName = getPackageName();
//            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                // 사용자에게 배터리 최적화 예외 목록에 앱을 추가할 것을 요청하는 인텐트 생성
//                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
//                intent.setData(Uri.parse("package:" + packageName));
//                startActivity(intent);
//            }
//        }
        Intent i = new Intent();

        String packageName = getPackageName();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        if(pm.isIgnoringBatteryOptimizations(packageName)){
            i.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        } else {
            i.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            i.setData(Uri.parse("package:" + packageName));
            startActivity(i);
        }


    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy()");

    }



}