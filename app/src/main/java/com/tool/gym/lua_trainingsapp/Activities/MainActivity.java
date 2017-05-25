package com.tool.gym.lua_trainingsapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tool.gym.lua_trainingsapp.R;
import com.tool.gym.lua_trainingsapp.SpecificTasks;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //Test Marcel
    LinearLayout random;
    LinearLayout specific;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        random = (LinearLayout) findViewById(R.id.randomtasks);
        random.setOnClickListener(this);
        specific = (LinearLayout) findViewById(R.id.specifictasks);
        specific.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.randomtasks){
            Intent i = new Intent(MainActivity.this, RandomTasks.class);
            startActivity(i);
        }
        else if(id == R.id.specifictasks){
            Intent i = new Intent(MainActivity.this, SpecificTasks.class);
            startActivity(i);
        }
    }
}
