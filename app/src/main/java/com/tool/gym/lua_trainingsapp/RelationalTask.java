package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class RelationalTask extends AppCompatActivity implements OnClickListener{
    Button commitbutton;
    EditText result;
    TextWatcher watcher;
    String loesung;
    private Runnable colourDefault = new Runnable() {
        public void run() {
            result.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    };


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relationtask_layout);
        commitbutton = (Button) findViewById(R.id.commitbutton);
        commitbutton.setOnClickListener(this);
        result = (EditText) findViewById(R.id.relationalresult);
        loesung = "π(Buch,(Seiten,Autor))";

        watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable editable) {}

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    result.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else if(loesung.startsWith(charSequence.toString())){
                    result.setBackgroundColor(Color.parseColor("#00FF00"));
                    result.postDelayed(colourDefault, 100);
                }
                else
                {
                    result.setBackgroundColor(Color.parseColor("#FF0000"));
                    result.postDelayed(colourDefault, 100);
                }
            }
        };
        result.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        result.addTextChangedListener(watcher);
        setTask();
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
    }

    private void setTask() {
        String[] taskinformation = new String[5];
        taskinformation = getTask();
        loesung = taskinformation[3];
        setTaskdetails(taskinformation[0], taskinformation[1], taskinformation[2]);
    }

    private String[] getTask() {
        String[] taskinformation = new String[5];
        taskinformation[0] = "Verfasse die Anfrage als Ausdruck der Relationenalgebra";
        taskinformation[1] = "Buch(Signatur, ISBN, Titel, Autor, Jahr, VerlagID, AnzahlSeiten)\n" +
                "Verlag(VerlagID, VerlagName, Verlagort)\n" +
                "Ausleiher(ID, Name, Geburtsdatum, Ort)\n" +
                "Ausgeliehen(Signatur, ID, VonDatum, BisDatum)";
        taskinformation[2] = "Geben Sie die Titel der Bücher an, die im Jahr 2010 erschienen sind und mehr als 300 Seiten haben.";
        taskinformation[3] = "π(Buch,(Seiten,Autor))";
        return taskinformation;
    }

    private void setTaskdetails(String title, String relations, String task) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        taskheader.setText(title);
        TextView relation = (TextView) findViewById(R.id.relationen);
        relation.setText(relations);
        TextView tasktext = (TextView) findViewById(R.id.task);
        tasktext.setText(task);
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.commitbutton:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        if(result.getText().toString().equals(loesung)){
            result.setText("Die Antwort ist korrekt");
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}