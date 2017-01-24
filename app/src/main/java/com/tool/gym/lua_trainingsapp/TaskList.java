package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_layout);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("topic");
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(value);
        }
    }

}
