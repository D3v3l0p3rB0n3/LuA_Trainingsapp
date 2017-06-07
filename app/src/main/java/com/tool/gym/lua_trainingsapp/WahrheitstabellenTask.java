package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import Database.SQLiteDatabase;

import com.tool.gym.lua_trainingsapp.Activities.RandomTasks;
import com.tool.gym.lua_trainingsapp.Activities.TaskList;

public class WahrheitstabellenTask extends AppCompatActivity implements OnClickListener {
    private Button commitbutton;
    private Button next_tast_button;
    private Button help_button;
    String sql, taskhelp;
    Cursor c;
    SQLiteDatabase db;
    Bundle extras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database.SQLiteDatabase(this);
        extras = getIntent().getExtras();

        //Layout
        setContentView(R.layout.wahrheitstabellentask_layout);

        //Listener für Button
        commitbutton = (Button) findViewById(R.id.commitbutton);
        commitbutton.setOnClickListener(this);
        next_tast_button = (Button) findViewById(R.id.next_task);
        next_tast_button.setOnClickListener(this);
        help_button = (Button) findViewById(R.id.helpbutton);
        help_button.setOnClickListener(this);

        //Aufgabenermittlung
        setTask();
    }

    //Ermittelt die zu bearbeitende Aufgabe und zeigt diese an
    private void setTask() {
        String[] taskinformation;
        taskinformation = getTask();
        setTaskdetails(taskinformation);
    }


    //Führt den Select auf die Datenbank aus, um die Aufgabe zu ermitteln
    private String[] getTask() {

        if (extras!=null)
        {
            String choser = extras.getString("startactivity");

            if (choser.equals(TaskList.class.getSimpleName())) // Aufgabe aus der gezielten Aufgabenauswahl gewählt
            {
                String aufgabe = extras.getString("choice");

                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Wahrheitstabellen w ON a.id = w.id " +
                        "WHERE a.id =  " + aufgabe + ";";

            }
            else if (choser.equals(ChooseTask.class.getSimpleName())) //Zufällige Aufgabenauswahl => zuerst noch geringste Bearbeitungszahl ermitteln
            {

                // Kleinste Bearbeitungszahl ermitteln
                sql = "SELECT MIN(az.Anzahl_der_Bearbeitungen) " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a ON az.ID=a.ID " +
                        "INNER JOIN Wahrheitstabellen w ON a.ID = w.ID ";

                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
                c = db.query(db.getReadableDatabase(), sql);

                c.moveToFirst();
                Integer anzahl = c.getInt(0);
                Log.d(TermVereinfachenTask.class.getSimpleName(), "Anzahl an Bearbeitungen: " + anzahl.toString());

                // Aufgaben mit kleinster Bearbeitungszahl aus DB holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Wahrheitstabellen w ON a.id = w.id " +
                        "WHERE az.Anzahl_der_Bearbeitungen = " + anzahl.toString() + ";";
                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
            }

        }

        //gewählte Aufgabe verarbeiten
        String[] taskinformation = new String[5];
        c = db.query(db.getWritableDatabase(), sql);
        c.moveToFirst();

        // Spaltennummer herausfinden
        int hilfe = c.getColumnIndex("Hilfe");
        int term = c.getColumnIndex("Term");
        int schwierigkeitsgrad = c.getColumnIndex("Schwierigkeitsgrad");
        int aufgabenstellung = c.getColumnIndex("Aufgabenstellung");
        int anz_argumente = c.getColumnIndex("Anzahl_Argumente");

        //Zuweisung der Infos aus der DB
        taskinformation[0] = c.getString(aufgabenstellung);
        taskinformation[1] = c.getString(term);
        taskinformation[3] = c.getString(anz_argumente);
        taskinformation[4] = c.getString(schwierigkeitsgrad);
        taskhelp = c.getString(hilfe);

        //taskinformation[0] = "Fülle die Wahrheitstabelle mit den Werten 1 und 0 aus";
        //taskinformation[1] = "a * b + c";
        //taskinformation[2] = "Tabelle";
        //taskinformation[3] = "4";
        //taskinformation[4] = "2";

        //Cursor schließen
        c.close();

        return taskinformation;


    }

    //Zeigt die Aufgabenstellung auf der Oberfläche an
    private void setTaskdetails(String[] taskdetails) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        taskheader.setText(taskdetails[0]);
        TextView task = (TextView) findViewById(R.id.term);
        task.setText(taskdetails[1]);
        setDifficulty(taskdetails[4]);
        createTable(taskdetails[3]);
    }

    //Wahrheitstabelle erstellen
    private void createTable(String arguments) {
        LinearLayout tablelayout = (LinearLayout) findViewById(R.id.tabelle);
        Wahrheitstabelle table = new Wahrheitstabelle();
        View v = table.createTable(this, arguments);
        tablelayout.addView(v);


    }

    //Bild für den Schwierigkeitsgrad festlegen
    private void setDifficulty(String difficulty ) {
        LinearLayout diff_png = (LinearLayout) findViewById(R.id.difficulty);
        if (difficulty.equals("1")) {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit1);
        }
        else if (difficulty.equals("2")) {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit2);
        }
        else {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit3);
        }
        
    }

    //Nach Bestätigung Überprüfung des Ergebnisses
    private void checkInput() {
        ChooseTask task = new ChooseTask(getApplicationContext());
        task.nextBoolTask(this);
    }

    //Button Click verarbeiten
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.commitbutton:
                checkInput();
                break;
            case R.id.next_task:
                ChooseTask task = new ChooseTask(getApplicationContext());
                task.nextBoolTask(this);
                break;
            case R.id.helpbutton:
                Intent help = new Intent(WahrheitstabellenTask.this, HelpPopUp.class);
                help.putExtra("text", taskhelp);
                startActivity(help);
                break;
            case R.id.row1:
                TextView field = (TextView) findViewById(R.id.row1);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row2:
                field = (TextView) findViewById(R.id.row2);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row3:
                field = (TextView) findViewById(R.id.row3);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row4:
                field = (TextView) findViewById(R.id.row4);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row5:
                field = (TextView) findViewById(R.id.row5);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row6:
                field = (TextView) findViewById(R.id.row6);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row7:
                field = (TextView) findViewById(R.id.row7);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row8:
                field = (TextView) findViewById(R.id.row8);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row9:
                field = (TextView) findViewById(R.id.row9);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;case R.id.row10:
                field = (TextView) findViewById(R.id.row10);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row11:
                field = (TextView) findViewById(R.id.row11);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row12:
                field = (TextView) findViewById(R.id.row12);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row13:
                field = (TextView) findViewById(R.id.row13);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row14:
                field = (TextView) findViewById(R.id.row14);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row15:
                field = (TextView) findViewById(R.id.row15);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;
            case R.id.row16:
                field = (TextView) findViewById(R.id.row16);
                if (field.getText().toString().equals("1")) {
                    field.setText("0");
                }
                else
                {
                    field.setText("1");
                }
                break;

        }
    }
}