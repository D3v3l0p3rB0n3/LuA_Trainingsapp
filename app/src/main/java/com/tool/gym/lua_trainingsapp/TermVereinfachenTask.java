package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tool.gym.lua_trainingsapp.Activities.TaskList;

import Database.SQLiteDatabase;


/**
 * Created by Marcel on 20.05.2017.
 */


public class TermVereinfachenTask extends AppCompatActivity implements OnClickListener {

    private Button checkbutton;
    private Button commitbutton;
    private Button help_button;
    private EditText result;
    SQLiteDatabase db;
    Bundle extras;
    String sql;
    Cursor c;

    String LOG_TAG = TermVereinfachenTask.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database.SQLiteDatabase(this);
        extras = getIntent().getExtras();


        //Layout laden
        setContentView(R.layout.task_bool_termv);

        //Buttons => Listener anmelden
        checkbutton = (Button) findViewById(R.id.checkbutton);
        checkbutton.setOnClickListener(this);
        commitbutton = (Button) findViewById(R.id.commitbutton);
        commitbutton.setOnClickListener(this);
        help_button = (Button) findViewById(R.id.helpbutton);
        help_button.setOnClickListener(this);

        result = (EditText) findViewById(R.id.bool_term_result);

        //Tastatur ausblenden
        result.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        //InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        //imeManager.showInputMethodPicker();

        //Aufgabe laden
        setTask();

    }

    @Override //Reaktion auf Klicks auf die Buttons
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.checkbutton: // Umforumung prüfen
                checkInput();
                break;
            case R.id.commitbutton: // Lösung prüfen
                checkSolution();
                break;


            case R.id.helpbutton:
                Intent help = new Intent(TermVereinfachenTask.this, HelpPopUp.class);
                startActivity(help);
                break;
        }
    }

    //eigene Methoden
    //Ermittelt die zu bearbeitende Aufgabe und zeigt diese an
    private void setTask() {
        String[] taskinformation;
        taskinformation = getTask(); //Aufgabe aus DB
        showTaskdetails(taskinformation); //Anzeigen
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
                        "INNER JOIN Termvereinfachung t ON a.id = t.id " +
                        "WHERE a.id =  " + aufgabe + ";";

            }
            else if (choser.equals(BoolscheAlgebraTasks.class.getSimpleName())) //Zufällige Aufgabenauswahl => zuerst noch geringste Bearbeitungszahl ermitteln
            {

                // Kleinste Bearbeitungszahl ermitteln
                sql = "SELECT MIN(Aufgabenzustand.Anzahl_der_Bearbeitungen) " +
                        "FROM Aufgabenzustand INNER JOIN Aufgabe ON Aufgabenzustand.ID=Aufgabe.ID " +
                        "INNER JOIN Termvereinfachung ON Aufgabe.ID=Termvereinfachung.ID ";

                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
                c = db.query(db.getWritableDatabase(), sql);

                c.moveToFirst();
                Integer anzahl = c.getInt(0);
                Log.d(TermVereinfachenTask.class.getSimpleName(), "Anzahl an Bearbeitungen: " + anzahl.toString());

                // Aufgaben mit kleinster Bearbeitungszahl aus DB holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Termvereinfachung t ON a.id = t.id " +
                        "WHERE az.Anzahl_der_Bearbeitungen = " + anzahl.toString() + ";";
                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
            }

        }

        //gewählte Aufgabe verarbeiten
        String[] taskinformation = new String[5];
        c = db.query(db.getWritableDatabase(), sql);
        c.moveToFirst();

        // Spaltennummer herausfinden
        int id = c.getColumnIndex("ID");
        int hilfe = c.getColumnIndex("Hilfe");
        int term = c.getColumnIndex("Term");
        int schwierigkeitsgrad = c.getColumnIndex("Schwierigkeitsgrad");
        int aufgabenstellung = c.getColumnIndex("Aufgabenstellung");
        int loesung = c.getColumnIndex("Lösung");

        //Testweise Ausgabe der Infos im Debugger
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Aufgabenstellung " + c.getString(aufgabenstellung));
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Term: " + c.getString(term));
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Schwierigkeitsgrad: " + c.getString(schwierigkeitsgrad));

        //Zuweisung der Infos aus der DB
        taskinformation[0] = c.getString(aufgabenstellung);
        taskinformation[1] = c.getString(term);
        taskinformation[2] = c.getString(schwierigkeitsgrad);

        return taskinformation;
    }

    //Zeigt die Aufgabenstellung auf der Oberfläche an
    private void showTaskdetails(String[] taskdetails) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        taskheader.setText(taskdetails[0]);
        TextView task = (TextView) findViewById(R.id.task);
        task.setText(taskdetails[1]);
        setDifficulty(taskdetails[2]);
    }

    //Bild für den Schwierigkeitsgrad festlegen
    private void setDifficulty(String difficulty) {
        LinearLayout diff_png = (LinearLayout) findViewById(R.id.difficulty);
        if (difficulty.equals("1")) {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit1);
        } else if (difficulty.equals("2")) {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit2);
        } else {
            diff_png.setBackgroundResource(R.drawable.schwierigkeit3);
        }

    }

    //Hilfsmethode um Tastatur auszublenden
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Lösung
    private void checkSolution() {
        Toast.makeText(getApplication(), "Lösung wird geprüft...", Toast.LENGTH_SHORT).show();
        BoolscheAlgebraTasks task = new BoolscheAlgebraTasks(getApplicationContext());
        task.nextTask();
    }

    //Korrektheit der Umformung prüfen
    private void checkInput() {
        if (!result.getText().toString().isEmpty()) {
            //Textfeld nicht leer => Prüfung geht weiter

            // Container suchen + neues Textfeld anpassen
            LinearLayout container = (LinearLayout) findViewById(R.id.eingabe_bool_term_vereinfachen);
            TextView neuesfeld = new TextView(this);

            //neues Textfeld anpassen
            neuesfeld.setHeight(getPixel(35));
            neuesfeld.setWidth(getPixel(500));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, getPixel(20));
            neuesfeld.setLayoutParams(params);
            neuesfeld.setTextColor(ResourcesCompat.getColor(getResources(), R.color.Text, null));
            neuesfeld.setGravity(Gravity.CENTER);
            neuesfeld.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            neuesfeld.setText(result.getText());

            //neues Textfeld hinzufügen
            container.addView(neuesfeld);
            container.setGravity(Gravity.CENTER_HORIZONTAL);

            // Scroll-View immer ganz nach unten schieben
            ScrollView scroll = (ScrollView) findViewById(R.id.eingabe_term);
            scroll.fullScroll(ScrollView.FOCUS_DOWN);


            Integer anzahl = container.getChildCount();
            Log.d(LOG_TAG, anzahl.toString());

        } else {
            Toast.makeText(getApplication(), "Textfeld leer - bitte befüllen!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getPixel(int sp) {
        float textsize = sp * getResources().getDisplayMetrics().scaledDensity;
        return (int) textsize;
    }

}

