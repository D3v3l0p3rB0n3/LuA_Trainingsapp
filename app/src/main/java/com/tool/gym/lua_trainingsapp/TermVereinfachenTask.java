package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tool.gym.lua_trainingsapp.Activities.RandomTasks;
import com.tool.gym.lua_trainingsapp.Activities.TaskList;

import Database.SQLiteDatabase;
import ResultCheck.CheckFormula;


/**
 * Created by Marcel on 20.05.2017.
 */


public class TermVereinfachenTask extends AppCompatActivity implements OnClickListener {
    String[] taskinformation;
    String lastinput = "";

    private Button checkbutton;
    private Button commitbutton;
    private Button help_button;

    private EditText result;
    SQLiteDatabase db;
    Bundle extras;
    String sql, taskhelp;
    Cursor c;
    private TextWatcher watcher;

    String LOG_TAG = TermVereinfachenTask.class.getSimpleName();
    private CustomKeyboard mCustomKeyboard;

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

        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.xml.boolkeyboard ,R.xml.qwertz, R.id.TitleArea, R.id.BottomArea);
        mCustomKeyboard.registerEditText(R.id.bool_term_result);

        watcher = new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable editable) {}

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                result.setSelection(result.length());}
        };
        result.addTextChangedListener(watcher);


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
                help.putExtra("text", taskhelp);
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
            else if (choser.equals(ChooseTask.class.getSimpleName())) //Zufällige Aufgabenauswahl => zuerst noch geringste Bearbeitungszahl ermitteln
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
        taskinformation = new String[6];
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

        //Log.d(TermVereinfachenTask.class.getSimpleName(), "Anzahl an DS mit kleinster Zahl: " + anzahl.toString());
        //Log.d(TermVereinfachenTask.class.getSimpleName(), "gewählte Aufgabe aus der Auswahl: " + zufall.toString());
        c.moveToPosition(zufall);

        // Spaltennummer herausfinden
        int id = c.getColumnIndex("ID");
        int hilfe = c.getColumnIndex("Hilfe");
        int term = c.getColumnIndex("Term");
        int schwierigkeitsgrad = c.getColumnIndex("Schwierigkeitsgrad");
        int aufgabenstellung = c.getColumnIndex("Aufgabenstellung");
        int anz_argumente = c.getColumnIndex("Anzahl_Argumente_der_Loesung");
        int loesung = c.getColumnIndex("Loesung");

        //Testweise Ausgabe der Infos im Debugger
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Aufgabenstellung " + c.getString(aufgabenstellung));
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Term: " + c.getString(term));
        Log.d(TermVereinfachenTask.class.getSimpleName(), "Schwierigkeitsgrad: " + c.getString(schwierigkeitsgrad));

        //Zuweisung der Infos aus der DB
        taskinformation[0] = c.getString(aufgabenstellung);
        taskinformation[1] = c.getString(term);
        taskinformation[2] = c.getString(schwierigkeitsgrad);
        taskinformation[3] = c.getString(loesung);
        taskinformation[4] = c.getString(anz_argumente);
        taskinformation[5] = c.getString(id);
        taskhelp = c.getString(hilfe);

        //Cursor schließen
        c.close();

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

    //Lösung prüfen
    private void checkSolution() {
        /*Das Endergebnis wird geprüft.
        Die Korrektheit der Umformung ist bereits gewährleistet.
        Es wird noch die Anzahl der Argumente des Endergebnises mit der Lösung verglichen.
         */
        if (lastinput.contains("→") | lastinput.contains("⟷")){
            Toast.makeText(getApplication(), "Solang das Endergebnis eine Implikation oder Äquivalenzen enthält, kann es nicht bestätigt werden. Bitte weiter Umformen!", Toast.LENGTH_LONG).show();
        }
        else{
            if(!lastinput.isEmpty())
            {
                lastinput = lastinput.replaceAll("\\W", "");
                int anzahlArg = lastinput.length();
                if (anzahlArg == Integer.parseInt(taskinformation[4])) {
                    Toast.makeText(getApplication(), "Ergebnis ist korrekt!", Toast.LENGTH_LONG).show();

                    Cursor cursor = db.query(db.getWritableDatabase(),"UPDATE Aufgabenzustand SET Status = 'Richtig', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1 WHERE ID = " + taskinformation[5] );
                    cursor.moveToFirst();
                    cursor.close();

                    ChooseTask task = new ChooseTask(getApplicationContext());
                    task.nextBoolTask(this);
                }
                else {
                    Toast.makeText(getApplication(), "Das Endergebnis ist nicht vollständig vereinfacht!", Toast.LENGTH_LONG).show();

                    Cursor cursor = db.query(db.getWritableDatabase(),"UPDATE Aufgabenzustand SET Status = 'Falsch', Anzahl_der_Bearbeitungen = Anzahl_der_Bearbeitungen + 1 WHERE ID = " + taskinformation[5] );
                    cursor.moveToFirst();
                    cursor.close();
                }
            }
        }
    }

    //Korrektheit der Umformung prüfen
    private void checkInput() {
        if (!result.getText().toString().isEmpty() & !result.getText().toString().contains("→") & !result.getText().toString().contains("⟷")) {
            //Textfeld nicht leer und keine Implikation => Prüfung geht weiter
            Log.d(TermVereinfachenTask.class.getSimpleName(), "Prüfung der Umformung gestartet");
            Toast.makeText(getApplication(), "Umformung wird geprüft...", Toast.LENGTH_SHORT).show();

            CheckFormula checkresult = new CheckFormula(taskinformation[3], result.getText().toString(), "4");

            //Bei richtigem Ergebnis wird die Lösung angezeigt.
            if (checkresult.getformelvergleichergebnis().equals("true")) {

                Toast.makeText(getApplication(), "Die eigegebene Umformung ist korrekt!", Toast.LENGTH_SHORT).show();

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
                lastinput = result.getText().toString();
                Log.d(LOG_TAG, anzahl.toString());
            }

            //bei falschem Ergebnis wird Fehlermeldung angezeigt.
            if (checkresult.getformelvergleichergebnis().equals("false")) {
                Toast.makeText(getApplication(), "Die eigegebene Umformung ist falsch!", Toast.LENGTH_SHORT).show();
            }
            //wenn der Algorithmus null zurückliefert ist in dem Algorithmus eine Exception entstanden.
            if (checkresult.getformelvergleichergebnis().equals("fehler")) {
                Toast.makeText(getApplication(), "Die eigegebene Umformung konnte nicht geprüft werden. Bitte Syntax beachten!", Toast.LENGTH_SHORT).show();
            }

        } else {
            if (result.getText().toString().isEmpty()) {
                Toast.makeText(getApplication(), "Textfeld leer - bitte befüllen!", Toast.LENGTH_SHORT).show();
            }
            if(result.getText().toString().contains("→") | result.getText().toString().contains("⟷")){

                //Wenn eine Eingabe eine Implikation oder eine Äquivalenz enthält kann sie zwar nicht geprüft werden
                // aber die Möglichkeit des Bestätigens der Eingabe muss trotzdem gegeben sein.
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
                lastinput = result.getText().toString();
                Log.d(LOG_TAG, anzahl.toString());
                Toast.makeText(getApplication(), "Eine Umformung kann erst geprüft werden sobald sie weder Implikationen noch Äquivalenzen enthält. Bitte weiter Umformen um den Term zu überprüfen!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public int getPixel(int sp) {
        float textsize = sp * getResources().getDisplayMetrics().scaledDensity;
        return (int) textsize;
    }
    @Override public void onBackPressed() {
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else this.finish();
    }

}

