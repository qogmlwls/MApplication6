package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    String TAG = "LoginActivity";

    SharedPreferencesManager sharedPreferencesManager;
    Button 로그인버튼;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferencesManager = SharedPreferencesManager.getInstance(getApplicationContext());

        // 로그인상태라면 메인 페이지로 이동.
        boolean result = sharedPreferencesManager.getLoginStatus();
        Log.i(TAG,"로그인 상태 : "+Boolean.toString(result));

        if(result){
            Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent2);
            finish();
        }


        로그인버튼 = findViewById(R.id.button3);
        로그인버튼.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 채팅서버와 연결
                // 화면 이동
                sharedPreferencesManager.login();
                boolean result = sharedPreferencesManager.getLoginStatus();
                Log.i(TAG,"로그인 결과 : "+Boolean.toString(result));

                // 로그인 기능과 독립적인 부분이라고 생각.
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);

                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();

            }
        });

    }
}