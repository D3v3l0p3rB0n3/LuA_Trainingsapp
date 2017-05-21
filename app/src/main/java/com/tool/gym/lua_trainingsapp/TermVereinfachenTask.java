package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Marcel on 20.05.2017.
 */


public class TermVereinfachenTask extends AppCompatActivity implements OnClickListener {

    private Button commitbutton;
    private Button next_tast_button;
    private Button help_button;
    private EditText result;

       String log_tag = TermVereinfachenTask.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Layout laden
        setContentView(R.layout.task_bool_termv);

        //Buttons => Listener anmelden
        commitbutton = (Button) findViewById(R.id.commitbutton);
        commitbutton.setOnClickListener(this);
        next_tast_button = (Button) findViewById(R.id.next_task);
        next_tast_button.setOnClickListener(this);
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
        InputMethodManager imeManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();

        //Aufgabe laden
        setTask();

    }

    @Override //Reaktion auf Klicks auf die Buttons
    public void onClick(View v) {
        int id = v.getId();
        //Toast.makeText(getApplication(),"Test", Toast.LENGTH_SHORT).show();

        if (id == R.id.commitbutton)
        {
            Toast.makeText(getApplication(), "Bestätigen gedrückt", Toast.LENGTH_SHORT).show();
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
        String[] taskinformation = new String[5];
        taskinformation[0] = "Vereinfache den folgenden Term soweit wie möglich";
        Log.d(log_tag, taskinformation[0]);
        taskinformation[1] = "( a + ¬(b*a)) * (c+(d+c))";
        taskinformation[2] = "2"; // Schwierigkeit
        return taskinformation;
    }

    //Zeigt die Aufgabenstellung auf der Oberfläche an
    private void showTaskdetails(String[] taskdetails) {
        TextView taskheader = (TextView) findViewById(R.id.taskheader);
        Log.d(log_tag, taskdetails[0]);
        taskheader.setText(taskdetails[0]);
        TextView task = (TextView) findViewById(R.id.task);
        task.setText(taskdetails[1]);
        setDifficulty(taskdetails[2]);
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

    //Hilfsmethode um Tastatur auszublenden
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
