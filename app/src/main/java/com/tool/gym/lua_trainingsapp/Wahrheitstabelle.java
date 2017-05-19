package com.tool.gym.lua_trainingsapp;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Wahrheitstabelle {

    public static TableLayout createTable(WahrheitstabellenTask task, String arguments) {
        TableLayout table = null;
        switch (arguments) {
            case "2":
                table = getTable2Arguments(task);
                break;
            case "3":
                table = getTable3Arguments(task);
                break;
            case "4":
                table = getTable4Arguments(task);
                break;
        }

        return table;
    }

    //Wahrheitstabelle für 2 Variablen erstellen
    private static TableLayout getTable2Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);
        TableRow[] row = new TableRow[5];
        TextView[][] columns = new TextView[5][3];

        String[][] array = new String[][] {
                { "A", "B", "Result" },
                { "1", "1", "" },
                { "1", "0", "" },
                { "0", "1", "" },
                { "0", "0", "" }};

        for (int i = 0; i <= 4; i++) {
            row[i] = new TableRow(task); //create a new tablerow
            for (int j = 0; j <= 2; j++){
                columns[i][j] = new TextView(task);
                columns[i][j].setText(array[i][j]);
                row[i].addView(columns[i][j]);
            }
            table.addView(row[i]);
        }
        return table;
    }

    //Wahrheitstabelle für 3 Variablen erstellen
    private static TableLayout getTable3Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);
        TableRow[] row = new TableRow[9];
        TextView[][] columns = new TextView[9][4];

        String[][] array = new String[][] {
                { "A", "B", "C" ,"Result" },
                { "1", "1", "1","" },
                { "1", "1", "0","" },
                { "1", "0", "1","" },
                { "1", "0", "0","" },
                { "0", "1", "1","" },
                { "0", "1", "0","" },
                { "0", "0", "1","" },
                { "0", "0", "0","" }};

        for (int i = 0; i <= 8; i++) {
            row[i] = new TableRow(task); //create a new tablerow
            for (int j = 0; j <= 3; j++){
                columns[i][j] = new TextView(task);
                columns[i][j].setText(array[i][j]);
                row[i].addView(columns[i][j]);
            }
            table.addView(row[i]);
        }
        return table;
    }

    //Wahrheitstabelle für 4 Variablen erstellen
    private static TableLayout getTable4Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);
        TableRow[] row = new TableRow[17];
        TextView[][] columns = new TextView[17][5];

        String[][] array = new String[][] {
                { "A", "B", "C", "D" ,"Result" },
                { "0", "0", "0", "0", "" },
                { "0", "0", "0", "1", "" },
                { "0", "0", "1", "0", "" },
                { "0", "0", "1", "1", "" },
                { "0", "1", "0", "0", "" },
                { "0", "1", "0", "1", "" },
                { "0", "1", "1", "0", "" },
                { "0", "1", "1", "1", "" },
                { "0", "0", "1", "0", "" },
                { "1", "0", "0", "0", "" },
                { "1", "0", "0", "1", "" },
                { "1", "0", "1", "0", "" },
                { "1", "0", "1", "1", "" },
                { "1", "1", "0", "0", "" },
                { "1", "1", "0", "1", "" },
                { "1", "1", "1", "0", "" },
                { "1", "1", "1", "1", "" }};

        for (int i = 0; i <= 16; i++) {
            row[i] = new TableRow(task); //create a new tablerow
            for (int j = 0; j <= 4; j++){
                columns[i][j] = new TextView(task);
                columns[i][j].setText(array[i][j]);
                row[i].addView(columns[i][j]);
            }
            table.addView(row[i]);
        }
        return table;
    }

}
