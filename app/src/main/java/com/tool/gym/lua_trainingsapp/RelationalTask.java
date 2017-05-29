package com.tool.gym.lua_trainingsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import Database.SQLiteDatabase;

public class RelationalTask extends AppCompatActivity implements OnClickListener {

    private Button commitbutton;
    private EditText result;
    private TextWatcher watcher;
    private String loesung;
    private Button next_tast_button;
    private Button help_button;
    private String taskid;
    private String taskhelp;
    SQLiteDatabase db;

    private Runnable colourDefault = new Runnable() {
        public void run() {
            result.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteDatabase(this);
        db.getReadableDatabase();
        //Layout
        setContentView(R.layout.relationtask_layout);

        //Listener für Button
        commitbutton = (Button) findViewById(R.id.commitbutton);
        commitbutton.setOnClickListener(this);
        next_tast_button = (Button) findViewById(R.id.next_task);
        next_tast_button.setOnClickListener(this);
        help_button = (Button) findViewById(R.id.helpbutton);
        help_button.setOnClickListener(this);
        result = (EditText) findViewById(R.id.relationalresult);

        //Textwatcher erfasst jede Eingabe auf Tastatur für die Farbänderung
        watcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable editable) {}

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    result.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else if(loesung.startsWith(charSequence.toString())){
                    result.setBackgroundColor(Color.parseColor("#BCED91"));
                    result.postDelayed(colourDefault, 150);
                }
                else {
                    result.setBackgroundColor(Color.parseColor("#FF4040"));
                    result.postDelayed(colourDefault, 150);
                }
            }
        };

        //Tastatur ausblenden
        result.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();

        //Listener für das Eingabefeld
        result.addTextChangedListener(watcher);

        //Aufgabenermittlung
        setTask();
    }

    //Button Click verarbeiten
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.commitbutton:
                checkInput();
                break;
            case R.id.next_task:
                Intent i = new Intent(RelationalTask.this, RelationalTask.class);
                startActivity(i);
                break;
            case R.id.helpbutton:
                Intent helppopup = new Intent(RelationalTask.this, HelpPopUp.class);
                helppopup.putExtra("text", taskhelp);
                startActivity(helppopup);
                break;
        }
    }

    //Ermittelt die zu bearbeitende Aufgabe und zeigt diese an
    private void setTask() {
        String[] taskinformation;
        taskinformation = getTask();
        loesung = taskinformation[3];
        setTaskdetails(taskinformation[0], taskinformation[1], taskinformation[2], taskinformation[4], taskinformation[5]);
    }


    //Führt den Select auf die Datenbank aus, um die Aufgabe zu ermitteln
    private String[] getTask() {
        String[] taskinformation = new String[6];
        Cursor anzahl = db.query("SELECT MIN(Aufgabenzustand.Anzahl_der_Bearbeitungen) " +
                                 "FROM Aufgabenzustand INNER JOIN Aufgabe ON Aufgabenzustand.ID=Aufgabe.ID " +
                                 "INNER JOIN Relationenschema ON Aufgabe.ID=Relationenschema.ID ");
        anzahl.moveToFirst();
        Integer anzbearbeitungen = anzahl.getInt(0);

        System.out.println(anzbearbeitungen);

        Cursor cursor = db.query("SELECT * " +
                                 "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                                 "INNER JOIN Relationenschema r ON a.id = r.id " +
                                 "WHERE az.Anzahl_der_Bearbeitungen = " + anzbearbeitungen.toString() + ";");
        cursor.moveToFirst();


        int id = cursor.getColumnIndex("ID");
        int hilfe = cursor.getColumnIndex("Hilfe");
        int aufgabenstellung = cursor.getColumnIndex("Aufgabenstellung");
        int schwierigkeitsgrad = cursor.getColumnIndex("Schwierigkeitsgrad");
        int aufgabenbeschreibung = cursor.getColumnIndex("Aufgabenbeschreibung");
        int loesung = cursor.getColumnIndex("Lösung");
        int relationennummer = cursor.getColumnIndex("Relationennummer");

        taskid = cursor.getString(id);
        taskhelp = cursor.getString(hilfe);

        String relationen = getRelationen(cursor.getString(relationennummer));

        taskinformation[0] = cursor.getString(aufgabenstellung);
        taskinformation[1] = relationen;
        taskinformation[2] = cursor.getString(aufgabenbeschreibung);
        taskinformation[3] = cursor.getString(loesung);
        taskinformation[4] = cursor.getString(schwierigkeitsgrad);
        cursor.close();

        return taskinformation;
    }

    private String getRelationen(String relationennummer) {
        String relationen = "";
        Cursor cursor = db.query("SELECT Relation FROM Relation WHERE Relationennummer=" + relationennummer);
        cursor.moveToFirst();
        int colId;

        while(!cursor.isAfterLast()) {
            colId = cursor.getColumnIndex("Relation");
            relationen = relationen + " " + cursor.getString(colId);
            cursor.moveToNext();
        }

        cursor.close();


        return relationen;
    }

    //Zeigt die Aufgabenstellung auf der Oberfläche an
    @SuppressWarnings("deprecation")
    private void setTaskdetails(String title, String relations, String task, String difficulty, String help) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        taskheader.setText(title);
        TextView relation = (TextView) findViewById(R.id.relationen);

        //Wenn Android Version > 7, neue Methode verwenden
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            relation.setText(Html.fromHtml(relations, Html.FROM_HTML_MODE_LEGACY));
        } else {
            relation.setText(Html.fromHtml(relations));
        }
        TextView tasktext = (TextView) findViewById(R.id.task);
        tasktext.setText(task);

        setDifficulty(difficulty);
    }

    @SuppressLint("NewApi")
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
        if(result.getText().toString().equals(loesung)) {
            result.setText(R.string.correct_answer);
            result.setBackgroundColor(Color.parseColor("#BCED91"));
            db.query("UPDATE Aufgabenzustand SET Status = 'Richtig', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1" );
        }
        else {
            result.setBackgroundColor(Color.parseColor("#FF4040"));
            result.setText(R.string.wrong_answer);
            db.query("UPDATE Aufgabenzustand SET Status = 'Falsch', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1" );
        }
    }

    //Hilfsmethode um Tastatur auszublenden
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}