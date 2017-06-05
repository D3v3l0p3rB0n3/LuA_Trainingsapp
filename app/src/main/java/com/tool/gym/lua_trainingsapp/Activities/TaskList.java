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
import com.tool.gym.lua_trainingsapp.TermVereinfachenTask;


import Database.SQLiteDatabase;

public class TaskList extends AppCompatActivity {
    ListView listview;
    SQLiteDatabase db;
    Task[] tasklist;
    Cursor c;
    Integer counter;


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
                //Anzahl der Aufgaben für die boolsche Algebra ermitteln
                // sind alle Aufgaben außer die der Relationenalgebra
                sql = "SELECT COUNT(a.ID) as ID "+
                        "FROM Aufgabe a " +
                        "WHERE a.ID  not in (SELECT a1.ID FROM Aufgabe a1 INNER JOIN Relationenschema r on a1.ID = r.ID)";

                Log.d(TaskList.class.getSimpleName(), sql.toString());

                c = db.query(db.getReadableDatabase(), sql);
                c.moveToFirst();
                Integer anzahl = c.getInt(c.getColumnIndex("ID"));

                tasklist = new Task[anzahl];
                counter = 0;


                //Aufgaben der Termvereinfachung holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Termvereinfachung t on t.id = a.id ";

                Log.d(TaskList.class.getSimpleName(), sql.toString());

                c = db.query(db.getReadableDatabase(), sql);

                while (c.moveToNext())
                {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String aufgabe = c.getString(c.getColumnIndex("Term"));
                    String aufgabenstellung = c.getString(c.getColumnIndex("Aufgabenstellung"));
                    String aufgabe_anzeige = aufgabenstellung + "\n" + aufgabe;
                    String schwierigkeitsgrad = c.getString(c.getColumnIndex("Schwierigkeitsgrad"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    tasklist[counter] = new Task(id.toString(), schwierigkeitsgrad, aufgabe_anzeige, status);
                    Log.d(TaskList.class.getSimpleName(), id.toString() + " , " + aufgabe.toString() + " , " + schwierigkeitsgrad.toString() + " , " + status.toString());
                    counter++;
                }

                //Aufgaben der Normalformen holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Normalformen n on n.id = a.id ";

                Log.d(TaskList.class.getSimpleName(), sql.toString());

                c = db.query(db.getReadableDatabase(), sql);

                while (c.moveToNext())
                {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String aufgabe = c.getString(c.getColumnIndex("Term"));
                    String aufgabenstellung = c.getString(c.getColumnIndex("Aufgabenstellung"));
                    String aufgabe_anzeige = aufgabenstellung + "\n" + aufgabe;
                    String schwierigkeitsgrad = c.getString(c.getColumnIndex("Schwierigkeitsgrad"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    tasklist[counter] = new Task(id.toString(), schwierigkeitsgrad, aufgabe_anzeige, status);
                    Log.d(TaskList.class.getSimpleName(), id.toString() + " , " + aufgabe.toString() + " , " + schwierigkeitsgrad.toString() + " , " + status.toString());
                    counter++;
                }

                //Aufgaben der Wahrheitstabellen holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Wahrheitstabellen w on w.id = a.id ";

                Log.d(TaskList.class.getSimpleName(), sql.toString());

                c = db.query(db.getReadableDatabase(), sql);

                while (c.moveToNext())
                {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String aufgabe = c.getString(c.getColumnIndex("Term"));
                    String aufgabenstellung = c.getString(c.getColumnIndex("Aufgabenstellung"));
                    String aufgabe_anzeige = aufgabenstellung + "\n" + aufgabe;
                    String schwierigkeitsgrad = c.getString(c.getColumnIndex("Schwierigkeitsgrad"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    tasklist[counter] = new Task(id.toString(), schwierigkeitsgrad, aufgabe_anzeige, status);
                    Log.d(TaskList.class.getSimpleName(), id.toString() + " , " + aufgabe.toString() + " , " + schwierigkeitsgrad.toString() + " , " + status.toString());
                    counter++;
                }

            }

            if (topic.equals(getString(R.string.relation))) //Relationenalgebra
            {
                Log.d(TaskList.class.getSimpleName(), "Relationenalgebra");
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Relationenschema r ON a.id = r.id ";


                Log.d(TaskList.class.getSimpleName(), sql.toString());

                c = db.query(db.getReadableDatabase(), sql);
                tasklist = new Task[c.getCount()];

                counter = 0;

                while (c.moveToNext()) {
                    Integer id = c.getInt(c.getColumnIndex("ID"));
                    String aufgabe = c.getString(c.getColumnIndex("Aufgabenbeschreibung"));
                    String schwierigkeitsgrad = c.getString(c.getColumnIndex("Schwierigkeitsgrad"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    tasklist[counter] = new Task(id.toString(), schwierigkeitsgrad, aufgabe, status);

                    Log.d(TaskList.class.getSimpleName(), id.toString() + " , " + aufgabe.toString() + " , " + schwierigkeitsgrad.toString() + " , " + status.toString());
                    counter++;
                }


            }


            ListAdapter adapter = new TaskAdapter(this, R.layout.listview_item, tasklist);
            listview = (ListView) findViewById(R.id.list_view);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //String test = (String) view parent.getItemAtPosition(position).toString();
                    Task t = (Task) parent.getItemAtPosition(position);
                    String aufgabe = t.number.toString();
                    Integer a = Integer.parseInt(aufgabe);

                    Log.d(TaskList.class.getSimpleName(), "ausgewählte Aufgabe: " + aufgabe);

                    // IDS: Relationenalgebra: 1-24
                    //  25 - 29 Termin
                    //  30 - 38 NF
                    // 39 - 43
                    Intent i;
                    if (a < 25)
                    {
                        Log.d(TaskList.class.getSimpleName(), "Relationenalgebra");
                    }
                    else if (a < 30 )
                    {
                        Log.d(TaskList.class.getSimpleName(), "Term");
                        i = new Intent(TaskList.this, TermVereinfachenTask.class);
                        i.putExtra("startactivity", TaskList.class.getSimpleName());
                        i.putExtra("choice", aufgabe);
                        startActivity(i);

                    }
                    else if (a < 39)
                    {
                        Log.d(TaskList.class.getSimpleName(), "Normalformen");
                    }
                    else
                    {
                        Log.d(TaskList.class.getSimpleName(), "Wahrheitstabellen");
                    }

                }

            });
        }
    }


}
