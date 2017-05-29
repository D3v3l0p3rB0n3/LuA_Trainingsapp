package com.tool.gym.lua_trainingsapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tool.gym.lua_trainingsapp.R;

import com.tool.gym.lua_trainingsapp.Activities.TaskList;

/**
 * Created by mabr on 23.01.2017.
 */
public class SpecificTasks extends AppCompatActivity implements View.OnClickListener{

    LinearLayout bool;
    LinearLayout relational;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specifictasks_layout);
        bool = (LinearLayout)findViewById(R.id.bool);
        bool.setOnClickListener(this);
        relational = (LinearLayout)findViewById(R.id.relational);
        relational.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        String title = null;
        Intent i = new Intent(SpecificTasks.this, TaskList.class);
        if(id == R.id.bool) {
            View header = (View)getLayoutInflater().inflate(R.layout.specifictasks_layout, null);
            TextView textView = (TextView)header.findViewById(R.id.booltext);
            title = textView.getText().toString();
        }
        else if(id == R.id.relational){
            View header = (View)getLayoutInflater().inflate(R.layout.specifictasks_layout, null);
            TextView textView = (TextView)header.findViewById(R.id.relationaltext);
            title = textView.getText().toString();
        }
        i.putExtra("topic", title );
        startActivity(i);
    }
}
