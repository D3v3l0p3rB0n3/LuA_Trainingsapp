package com.tool.gym.lua_trainingsapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tool.gym.lua_trainingsapp.R;

public class TaskList extends AppCompatActivity {
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_layout);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String value = extras.getString("topic");
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(value);
            Task[] tasklist = new Task[]
            {
                new Task("1","Aufgabe1", "Richtig"),
                new Task("2","Aufgabe2", "Falsch"),
                new Task("3","Aufgabe3", ""),
                new Task("2","Aufgabe4", ""),
                new Task("1","Aufgabe5", "Richtig")
            };
            ListAdapter adapter = new TaskAdapter(this, R.layout.listview_item, tasklist);
            listview = (ListView)findViewById(R.id.list_view);
            listview.setAdapter(adapter);
        }
    }

}
