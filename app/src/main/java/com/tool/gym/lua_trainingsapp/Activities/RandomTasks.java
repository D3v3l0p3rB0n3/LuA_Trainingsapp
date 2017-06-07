package com.tool.gym.lua_trainingsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.tool.gym.lua_trainingsapp.ChooseTask;
import com.tool.gym.lua_trainingsapp.NormalformenTask;
import com.tool.gym.lua_trainingsapp.R;
import com.tool.gym.lua_trainingsapp.RelationalTask;
import com.tool.gym.lua_trainingsapp.TermVereinfachenTask;
import com.tool.gym.lua_trainingsapp.WahrheitstabellenTask;

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
            ChooseTask task = new ChooseTask(getApplicationContext());
            task.nextRelationalTask(this);
        }
        else if (id == R.id.random_bool){
            ChooseTask task = new ChooseTask(getApplicationContext());
            task.nextBoolTask(this);
            //Intent i = new Intent(RandomTasks.this, NormalformenTask.class);
            //i.putExtra("startactivity", RandomTasks.class.getSimpleName());
            //startActivity(i);
        }

    }
}
