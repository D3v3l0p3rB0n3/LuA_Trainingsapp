package com.tool.gym.lua_trainingsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tool.gym.lua_trainingsapp.R;

import Database.SQLiteDatabase;

/**
 * Created by mabr on 23.01.2017.
 */

public class Splash extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //DB physisch anlegen
        SQLiteDatabase db = new SQLiteDatabase(this);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}
