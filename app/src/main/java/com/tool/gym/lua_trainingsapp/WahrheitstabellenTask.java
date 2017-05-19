package com.tool.gym.lua_trainingsapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class WahrheitstabellenTask extends AppCompatActivity implements OnClickListener {
    private Button commitbutton;
    private Button next_tast_button;
    private Button help_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

    //Button Click verarbeiten
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.commitbutton:
                checkInput();
                break;
            case R.id.next_task:
                Intent i = new Intent(WahrheitstabellenTask.this, WahrheitstabellenTask.class);
                startActivity(i);
                break;
            case R.id.helpbutton:
                Intent help = new Intent(WahrheitstabellenTask.this, HelpPopUp.class);
                startActivity(help);
                break;
        }
    }

    //Ermittelt die zu bearbeitende Aufgabe und zeigt diese an
    private void setTask() {
        String[] taskinformation;
        taskinformation = getTask();
        setTaskdetails(taskinformation);
    }


    //Führt den Select auf die Datenbank aus, um die Aufgabe zu ermitteln
    private String[] getTask() {
        String[] taskinformation = new String[5];
        taskinformation[0] = "Fülle die Wahrheitstabelle mit den Werten 1 und 0 aus";
        taskinformation[1] = "a * b + c";
        taskinformation[2] = "Tabelle";
        taskinformation[3] = "1";
        taskinformation[4] = "1";
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

    private void createTable(String arguments) {
        LinearLayout tablelayout = (LinearLayout) findViewById(R.id.tabelle);
        tablelayout.addView(Wahrheitstabelle.createTable(this, arguments));

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

    }
}