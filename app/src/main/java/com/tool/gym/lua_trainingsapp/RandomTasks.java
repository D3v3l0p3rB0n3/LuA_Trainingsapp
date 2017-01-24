package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by mabr on 23.01.2017.
 */
public class RandomTasks extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.randomtasks_layout);
    }

 /*   public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.random_bool){
            Intent i = new Intent(RandomTasks.this, BoolTerm.class);
            startActivity(i);
        }
        else if(id == R.id.random_all){
            Intent i = new Intent(RandomTasks.this, SpecificTasks.class);
            startActivity(i);
        }
        else if(id == R.id.random_relational){
            Intent i = new Intent(RandomTasks.this, SpecificTasks.class);
            startActivity(i);
    }*/
}
