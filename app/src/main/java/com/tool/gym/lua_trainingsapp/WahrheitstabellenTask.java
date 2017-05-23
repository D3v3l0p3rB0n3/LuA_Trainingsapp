package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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
        taskinformation[3] = "4";
        taskinformation[4] = "2";
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
        BoolscheAlgebraTasks task = new BoolscheAlgebraTasks(getApplicationContext());
        task.nextTask();
    }

    //Button Click verarbeiten
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.commitbutton:
                checkInput();
                break;
            case R.id.next_task:
                BoolscheAlgebraTasks task = new BoolscheAlgebraTasks(getApplicationContext());
                task.nextTask();
                break;
            case R.id.helpbutton:
                Intent help = new Intent(WahrheitstabellenTask.this, HelpPopUp.class);
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