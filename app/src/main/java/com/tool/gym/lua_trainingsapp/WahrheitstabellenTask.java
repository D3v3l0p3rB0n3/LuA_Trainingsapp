package com.tool.gym.lua_trainingsapp;

import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import Database.SQLiteDatabase;
import ResultCheck.CheckFormula;
import ResultCheck.PotenzmengeVariablen;
import java.lang.Object;

import com.tool.gym.lua_trainingsapp.Activities.RandomTasks;
import com.tool.gym.lua_trainingsapp.Activities.TaskList;

public class WahrheitstabellenTask extends AppCompatActivity implements OnClickListener {
    String[] taskinformation;
    String[] termSolution;

    private Button commitbutton;
    private Button next_tast_button;
    private Button help_button;

    //Alle Felder der Tabelle
    TextView field1;
    TextView field2;
    TextView field3;
    TextView field4;
    TextView field5;
    TextView field6;
    TextView field7;
    TextView field8;
    TextView field9;
    TextView field10;
    TextView field11;
    TextView field12;
    TextView field13;
    TextView field14;
    TextView field15;
    TextView field16;


    String sql, taskhelp;
    Cursor c;
    SQLiteDatabase db;
    Bundle extras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database.SQLiteDatabase(this);
        extras = getIntent().getExtras();

         field1 = (TextView) findViewById(R.id.row1);
         field2 = (TextView) findViewById(R.id.row2);
         field3 = (TextView) findViewById(R.id.row3);
         field4 = (TextView) findViewById(R.id.row4);
         field5 = (TextView) findViewById(R.id.row5);
         field6 = (TextView) findViewById(R.id.row6);
         field7 = (TextView) findViewById(R.id.row7);
         field8 = (TextView) findViewById(R.id.row8);
         field9 = (TextView) findViewById(R.id.row9);
         field10 = (TextView) findViewById(R.id.row10);
         field11 = (TextView) findViewById(R.id.row11);
         field12 = (TextView) findViewById(R.id.row12);
         field13 = (TextView) findViewById(R.id.row13);
         field14 = (TextView) findViewById(R.id.row14);
         field15 = (TextView) findViewById(R.id.row15);
         field16 = (TextView) findViewById(R.id.row16);

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
        taskinformation = new String[5];
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

        field1 = (TextView) findViewById(R.id.row1);
        field2 = (TextView) findViewById(R.id.row2);
        field3 = (TextView) findViewById(R.id.row3);
        field4 = (TextView) findViewById(R.id.row4);
        field5 = (TextView) findViewById(R.id.row5);
        field6 = (TextView) findViewById(R.id.row6);
        field7 = (TextView) findViewById(R.id.row7);
        field8 = (TextView) findViewById(R.id.row8);

        //Wenn einmal Aufgaben mit 4 Variablen dazukommen sollten
        if(taskinformation[3].equals("4")) {
            field9 = (TextView) findViewById(R.id.row9);
            field10 = (TextView) findViewById(R.id.row10);
            field11 = (TextView) findViewById(R.id.row11);
            field12 = (TextView) findViewById(R.id.row12);
            field13 = (TextView) findViewById(R.id.row13);
            field14 = (TextView) findViewById(R.id.row14);
            field15 = (TextView) findViewById(R.id.row15);
            field16 = (TextView) findViewById(R.id.row16);
        }

        Boolean everythingright = true;
        //Ergebnisse für die Tabellen holen
        CheckFormula checkterm = new CheckFormula(taskinformation[1]);
        termSolution = checkterm.getwahrheitstabellenErgebnis();

        //Ergebnisse mit den eingebenen Werten abgleichen
        if (field1.getText().toString().equals(termSolution[0])) {
            field1.setBackgroundColor(Color.GREEN);
        }
        if (!field1.getText().toString().equals(termSolution[0])) {
            field1.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field2.getText().toString().equals(termSolution[1])) {
            field2.setBackgroundColor(Color.GREEN);
        }
        if (!field2.getText().toString().equals(termSolution[1])) {
            field2.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field3.getText().toString().equals(termSolution[2])) {
            field3.setBackgroundColor(Color.GREEN);
        }
        if (!field3.getText().toString().equals(termSolution[2])) {
            field3.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field4.getText().toString().equals(termSolution[3])) {
            field4.setBackgroundColor(Color.GREEN);
        }
        if (!field4.getText().toString().equals(termSolution[3])) {
            field4.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field5.getText().toString().equals(termSolution[4])) {
            field5.setBackgroundColor(Color.GREEN);
        }
        if (!field5.getText().toString().equals(termSolution[4])) {
            field5.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field6.getText().toString().equals(termSolution[5])) {
            field6.setBackgroundColor(Color.GREEN);
        }
        if (!field6.getText().toString().equals(termSolution[5])) {
            field6.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field7.getText().toString().equals(termSolution[6])) {
            field7.setBackgroundColor(Color.GREEN);
        }
        if (!field7.getText().toString().equals(termSolution[6])) {
            field7.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        if (field8.getText().toString().equals(termSolution[7])) {
            field8.setBackgroundColor(Color.GREEN);
        }
        if (!field8.getText().toString().equals(termSolution[7])) {
            field8.setBackgroundColor(Color.RED);
            everythingright = false;
        }

        //Wenn einmal Aufgaben mit 4 Variablen dazukommen sollten
        if (taskinformation[3].equals("4")) {
            if (field9.getText().toString().equals(termSolution[8])) {
                field9.setBackgroundColor(Color.GREEN);
            }
            if (!field9.getText().toString().equals(termSolution[8])) {
                field9.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field10.getText().toString().equals(termSolution[9])) {
                field10.setBackgroundColor(Color.GREEN);
            }
            if (!field10.getText().toString().equals(termSolution[9])) {
                field10.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field11.getText().toString().equals(termSolution[10])) {
                field11.setBackgroundColor(Color.GREEN);
            }
            if (!field11.getText().toString().equals(termSolution[10])) {
                field11.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field12.getText().toString().equals(termSolution[11])) {
                field12.setBackgroundColor(Color.GREEN);
            }
            if (!field12.getText().toString().equals(termSolution[11])) {
                field12.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field13.getText().toString().equals(termSolution[12])) {
                field13.setBackgroundColor(Color.GREEN);
            }
            if (!field13.getText().toString().equals(termSolution[12])) {
                field13.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field14.getText().toString().equals(termSolution[13])) {
                field14.setBackgroundColor(Color.GREEN);
            }
            if (!field14.getText().toString().equals(termSolution[13])) {
                field14.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field15.getText().toString().equals(termSolution[14])) {
                field15.setBackgroundColor(Color.GREEN);
            }
            if (!field15.getText().toString().equals(termSolution[14])) {
                field15.setBackgroundColor(Color.RED);
                everythingright = false;
            }

            if (field16.getText().toString().equals(termSolution[15])) {
                field16.setBackgroundColor(Color.GREEN);
            }
            if (!field16.getText().toString().equals(termSolution[15])) {
                field16.setBackgroundColor(Color.RED);
                everythingright = false;
            }
        }

        //Prüfen ob alle Zeilen korrekt eingegeben wurden
        if(everythingright){
            Toast.makeText(getApplication(), "Ergebnis ist korrekt!", Toast.LENGTH_LONG).show();

            /*
            Updates einfügen!!
             */
        }
        else{
            Toast.makeText(getApplication(), "Ergebnis ist nicht korrekt!", Toast.LENGTH_LONG).show();
        }
    }

    //Button Click verarbeiten
    public void onClick(View v) {

        field1 = (TextView) findViewById(R.id.row1);
        field2 = (TextView) findViewById(R.id.row2);
        field3 = (TextView) findViewById(R.id.row3);
        field4 = (TextView) findViewById(R.id.row4);
        field5 = (TextView) findViewById(R.id.row5);
        field6 = (TextView) findViewById(R.id.row6);
        field7 = (TextView) findViewById(R.id.row7);
        field8 = (TextView) findViewById(R.id.row8);
        field9 = (TextView) findViewById(R.id.row9);
        field10 = (TextView) findViewById(R.id.row10);
        field11 = (TextView) findViewById(R.id.row11);
        field12 = (TextView) findViewById(R.id.row12);
        field13 = (TextView) findViewById(R.id.row13);
        field14 = (TextView) findViewById(R.id.row14);
        field15 = (TextView) findViewById(R.id.row15);
        field16 = (TextView) findViewById(R.id.row16);

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
                if (field1.getText().toString().equals("1")) {
                    field1.setText("0");
                }
                else
                {
                    field1.setText("1");
                }
                break;
            case R.id.row2:
                if (field2.getText().toString().equals("1")) {
                    field2.setText("0");
                }
                else
                {
                    field2.setText("1");
                }
                break;
            case R.id.row3:
                if (field3.getText().toString().equals("1")) {
                    field3.setText("0");
                }
                else
                {
                    field3.setText("1");
                }
                break;
            case R.id.row4:
                if (field4.getText().toString().equals("1")) {
                    field4.setText("0");
                }
                else
                {
                    field4.setText("1");
                }
                break;
            case R.id.row5:
                if (field5.getText().toString().equals("1")) {
                    field5.setText("0");
                }
                else
                {
                    field5.setText("1");
                }
                break;
            case R.id.row6:
                if (field6.getText().toString().equals("1")) {
                    field6.setText("0");
                }
                else
                {
                    field6.setText("1");
                }
                break;
            case R.id.row7:
                if (field7.getText().toString().equals("1")) {
                    field7.setText("0");
                }
                else
                {
                    field7.setText("1");
                }
                break;
            case R.id.row8:
                if (field8.getText().toString().equals("1")) {
                    field8.setText("0");
                }
                else
                {
                    field8.setText("1");
                }
                break;
            case R.id.row9:
                if (field9.getText().toString().equals("1")) {
                    field9.setText("0");
                }
                else
                {
                    field9.setText("1");
                }
                break;case R.id.row10:
                if (field10.getText().toString().equals("1")) {
                    field10.setText("0");
                }
                else
                {
                    field10.setText("1");
                }
                break;
            case R.id.row11:
                if (field11.getText().toString().equals("1")) {
                    field11.setText("0");
                }
                else
                {
                    field11.setText("1");
                }
                break;
            case R.id.row12:
                if (field12.getText().toString().equals("1")) {
                    field12.setText("0");
                }
                else
                {
                    field12.setText("1");
                }
                break;
            case R.id.row13:
                if (field13.getText().toString().equals("1")) {
                    field13.setText("0");
                }
                else
                {
                    field13.setText("1");
                }
                break;
            case R.id.row14:
                if (field14.getText().toString().equals("1")) {
                    field14.setText("0");
                }
                else
                {
                    field14.setText("1");
                }
                break;
            case R.id.row15:
                if (field15.getText().toString().equals("1")) {
                    field15.setText("0");
                }
                else
                {
                    field15.setText("1");
                }
                break;
            case R.id.row16:
                if (field16.getText().toString().equals("1")) {
                    field16.setText("0");
                }
                else
                {
                    field16.setText("1");
                }
                break;

        }
    }
}