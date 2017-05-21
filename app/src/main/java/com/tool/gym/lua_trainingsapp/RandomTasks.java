package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

/**
 * Created by mabr on 23.01.2017.
 */
public class RandomTasks extends AppCompatActivity implements OnClickListener{

    LinearLayout bool_tasks;
    LinearLayout relation_tasks;
    LinearLayout all_tasks;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.randomtasks_layout);
        bool_tasks = (LinearLayout)findViewById(R.id.random_bool);
        bool_tasks.setOnClickListener(this);
        relation_tasks = (LinearLayout)findViewById(R.id.random_relational);
        relation_tasks.setOnClickListener(this);
        all_tasks = (LinearLayout)findViewById(R.id.random_all);
        all_tasks.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.random_relational) {
            Intent i = new Intent(RandomTasks.this, RelationalTask.class);
            startActivity(i);
        }
        else if (id == R.id.random_bool){

            // Zufällig Term / Wahrheitstabelle
            Double x = Math.random() * 2;
            Integer zufall = (int) Math.round(x);
            Log.d(RandomTasks.class.getSimpleName(), x.toString() + "...." + zufall.toString() );

            Intent i = null;
            switch (zufall)
            {
                case 0:
                    i = new Intent(RandomTasks.this, WahrheitstabellenTask.class);
                    break;
                case 1:
                    i = new Intent(RandomTasks.this, TermVereinfachenTask.class);
                    break;
                case 2:
                    i = new Intent(RandomTasks.this, NormalformenTask.class);
                    break;

            }
            startActivity(i);
        }

    }
}
