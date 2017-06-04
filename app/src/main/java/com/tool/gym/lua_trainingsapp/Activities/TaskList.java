package com.tool.gym.lua_trainingsapp.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.tool.gym.lua_trainingsapp.R;



import Database.SQLiteDatabase;

public class TaskList extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase db;
    Task[] tasklist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasklist_layout);
        Bundle extras = getIntent().getExtras();
        db = new SQLiteDatabase(this);

        if (extras != null) {
            String topic = extras.getString("topic");
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(topic);

            //Aufgaben unterscheidung je nach gewähltem Themengebiet
            String sql;


            if (topic.equals(getString(R.string.bool))) //boolsche Algebra
            {
                Log.d(TaskList.class.getSimpleName(), "boolsche Algebra gewählt");

           tasklist = new Task[]
            {
                new Task("1","1","Aufgabe1", "Richtig"),
                new Task("2","2","Aufgabe2", "Falsch"),
                new Task("3","3","Aufgabe3", ""),
                new Task("4","2","Aufgabe4", ""),
                new Task("5","1","Aufgabe5", "Richtig")
            };

            }

            if (topic.equals(getString(R.string.relation))) //Relationenalgebra
            {
                Log.d(TaskList.class.getSimpleName(), "Relationenalgebra");
                sql =  "SELECT * " +
                       "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                       "INNER JOIN Relationenschema r ON a.id = r.id ";


                Log.d(TaskList.class.getSimpleName(), sql.toString());

                Cursor c = db.query(db.getReadableDatabase(),sql);
                tasklist = new Task[c.getCount()];

                Integer counter = 0;

                while (c.moveToNext())
                {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String aufgabe= c.getString(c.getColumnIndex("Aufgabenbeschreibung"));
                    String schwierigkeitsgrad = c.getString(c.getColumnIndex("Schwierigkeitsgrad"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    tasklist[counter] = new Task(id.toString(),schwierigkeitsgrad,aufgabe,status);

                    Log.d(TaskList.class.getSimpleName(), id.toString() + " , " + aufgabe.toString() + " , " + schwierigkeitsgrad.toString() + " , " + status.toString());
                    counter++;
                }


            }





            ListAdapter adapter = new TaskAdapter(this, R.layout.listview_item, tasklist);
            listview = (ListView)findViewById(R.id.list_view);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String test = (String) view parent.getItemAtPosition(position).toString();
                    Log.d(TaskList.class.getSimpleName(), "Test");

                }

            });
        }
    }



}
