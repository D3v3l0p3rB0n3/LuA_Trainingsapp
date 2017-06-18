package com.tool.gym.lua_trainingsapp;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tool.gym.lua_trainingsapp.Activities.TaskList;

import Database.SQLiteDatabase;

public class RelationalTask extends AppCompatActivity implements OnClickListener {

    private Button commitbutton;
    private EditText result;
    private TextWatcher watcher;
    private String loesung;
    private Button next_tast_button;
    private Button help_button;
    private String taskid, taskhelp, sql;
    Cursor c;
    SQLiteDatabase db;
    Bundle extras;
    private CustomKeyboard mCustomKeyboard;

    private Runnable colourDefault = new Runnable() {
        public void run() {
            result.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new SQLiteDatabase(this);
        extras = getIntent().getExtras();

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
                    result.setSelection(result.length());
                }
                else if(loesung.startsWith(charSequence.toString())){
                    result.setBackgroundColor(Color.parseColor("#BCED91"));
                    result.postDelayed(colourDefault, 150);
                    result.setSelection(result.length());
                }
                else {
                    result.setBackgroundColor(Color.parseColor("#FF4040"));
                    result.postDelayed(colourDefault, 150);
                    result.setSelection(result.length());

                }
            }
        };

        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.qwertz, R.id.TitleArea, R.id.BottomArea);
        mCustomKeyboard.registerEditText(R.id.relationalresult);

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
                ChooseTask task = new ChooseTask(getApplicationContext());
                task.nextRelationalTask(this);
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

        if (extras!=null)
        {
            String choser = extras.getString("startactivity");

            if (choser.equals(TaskList.class.getSimpleName())) // Aufgabe aus der gezielten Aufgabenauswahl gewählt
            {
                String aufgabe = extras.getString("choice");

                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Relationenschema r ON a.id = r.id " +
                        "WHERE a.id =  " + aufgabe + ";";

            }
            else if (choser.equals(ChooseTask.class.getSimpleName())) //Zufällige Aufgabenauswahl => zuerst noch geringste Bearbeitungszahl ermitteln
            {

                // Kleinste Bearbeitungszahl ermitteln
                sql = "SELECT MIN(az.Anzahl_der_Bearbeitungen) " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a ON az.ID=a.ID " +
                        "INNER JOIN Relationenschema r ON a.ID = r.ID ";

                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
                c = db.query(db.getReadableDatabase(), sql);

                c.moveToFirst();
                Integer anzahl = c.getInt(0);
                Log.d(TermVereinfachenTask.class.getSimpleName(), "Anzahl an Bearbeitungen: " + anzahl.toString());

                // Aufgaben mit kleinster Bearbeitungszahl aus DB holen
                sql = "SELECT * " +
                        "FROM Aufgabenzustand az INNER JOIN Aufgabe a on az.ID = a.ID " +
                        "INNER JOIN Relationenschema r ON a.id = r.id " +
                        "WHERE az.Anzahl_der_Bearbeitungen = " + anzahl.toString() + ";";
                Log.d(TermVereinfachenTask.class.getSimpleName(), sql);
            }

        }

        //gewählte Aufgabe verarbeiten
        String[] taskinformation = new String[6];
        c = db.query(db.getWritableDatabase(), sql);

        //Ermittlung der konrekten Aufgabe => z.T. per Zufallszahl
        Integer anzahl = c.getCount();
        Integer zufall,x1;
        if (anzahl > 1) // wenn mehrs als 1 Aufgabe aus der DB geholt wurde => Zufallszahl für die Aufgabe, sonst nur die 1 Aufgabe
        {
            x1= (int) ((Math.random())*anzahl+1); //Zufallszahl zwischen 0 - Anzahl der Aufgaben - 1
            zufall = x1 - 1;


        }
        else
        {
            zufall = 0;
        }

        c.moveToPosition(zufall);

        // Spaltennummer herausfinden
        int id = c.getColumnIndex("ID");
        int hilfe = c.getColumnIndex("Hilfe");
        int aufgabenstellung = c.getColumnIndex("Aufgabenstellung");
        int schwierigkeitsgrad = c.getColumnIndex("Schwierigkeitsgrad");
        int aufgabenbeschreibung = c.getColumnIndex("Aufgabenbeschreibung");
        int loesung = c.getColumnIndex("Loesung");
        int relationennummer = c.getColumnIndex("Relationennummer");

        // Zuweisung der Infos aus der DB
        taskid = c.getString(id);
        taskhelp = c.getString(hilfe);
        String relationen = getRelationen(c.getString(relationennummer));
        taskinformation[0] = c.getString(aufgabenstellung);
        taskinformation[1] = relationen;
        taskinformation[2] = c.getString(aufgabenbeschreibung);
        taskinformation[3] = c.getString(loesung);
        taskinformation[4] = c.getString(schwierigkeitsgrad);

        c.close();
        return taskinformation;
    }

    private String getRelationen(String relationennummer) {
        String relationen = "";
        Cursor cursor = db.query(db.getWritableDatabase(),"SELECT Relation FROM Relation WHERE Relationennummer=" + relationennummer);
        cursor.moveToFirst();
        int colId;

        while(!cursor.isAfterLast()) {
            colId = cursor.getColumnIndex("Relation");
            if(cursor.isLast()){
                relationen = relationen + " " + cursor.getString(colId);
            }
            else{
                relationen = relationen + " " + cursor.getString(colId) + "<br>";
            }
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
            Cursor cursor = db.query(db.getWritableDatabase(),"UPDATE Aufgabenzustand SET Status = 'Richtig', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1 WHERE ID = " + taskid );
            cursor.moveToFirst();
            cursor.close();

        }
        else {
            result.setBackgroundColor(Color.parseColor("#FF4040"));
            result.setText(R.string.wrong_answer);
            Cursor cursor = db.query(db.getWritableDatabase(),"UPDATE Aufgabenzustand SET Status = 'Falsch', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1 WHERE ID = " + taskid  );
            cursor.moveToFirst();
            cursor.close();
        }
    }

    @Override public void onBackPressed() {
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else this.finish();
    }
}