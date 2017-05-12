package com.tool.gym.lua_trainingsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class RelationalTask extends AppCompatActivity implements OnClickListener{


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationtask_layout);
        setTaskdetails("Bitte gebe den Ausdruck der Relationenalgebra an");
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
    }

    private void setTaskdetails(String s) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        taskheader.setText(s);


    }

    public void onClick(View v) {
        /*int id = v.getId();
        if (id == R.id.random_bool) {
            Intent i = new Intent(RandomTasks.this, RelationalTask.class);
            startActivity(i);
        }

        else if(id == R.id.random_all){
            Intent i = new Intent(RandomTasks.this, BoolTask.class);
            startActivity(i);
        }
        else if(id == R.id.random_relational){
            Intent i = new Intent(RandomTasks.this, All_Tasks.class);
            startActivity(i);
    }*/
    }
}