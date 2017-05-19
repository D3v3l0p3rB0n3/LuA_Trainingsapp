package com.tool.gym.lua_trainingsapp;

import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by matthiasbrandel on 19.05.17.
 */

public class Wahrheitstabelle {
    public Wahrheitstabelle() {

    }

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

    private static TableLayout getTable2Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);

        TableRow row1 = new TableRow(task); //create a new tablerow
        TextView column1 = new TextView(task); //create a new textview for left column
        TextView column2 = new TextView(task); //create a new textview for right column
        TextView column3 = new TextView(task); //create a new textview for left column
        column1.setText("A"); //insert text in the first textview
        column2.setText("B"); //insert text in the first textview
        column3.setText("Result"); //insert text in the first textview
        row1.addView(column1); //add the first textview to your tablerow (left column)
        row1.addView(column2); //add the second textview to your tablerow (right column)
        row1.addView(column3); //add the second textview to your tablerow (right column)
        table.addView(row1);

        TableRow row2 = new TableRow(task); //create a new tablerow
        TextView column4 = new TextView(task); //create a new textview for left column
        TextView column5 = new TextView(task); //create a new textview for right column
        TextView column6 = new TextView(task); //create a new textview for left column
        column1.setText("1"); //insert text in the first textview
        column2.setText("1"); //insert text in the first textview
        column3.setText(""); //insert text in the first textview
        row2.addView(column4); //add the first textview to your tablerow (left column)
        row2.addView(column5); //add the second textview to your tablerow (right column)
        row2.addView(column6); //add the second textview to your tablerow (right column)
        table.addView(row2);

        TableRow row3 = new TableRow(task); //create a new tablerow
        TextView column7 = new TextView(task); //create a new textview for left column
        TextView column8 = new TextView(task); //create a new textview for right column
        TextView column9 = new TextView(task); //create a new textview for left column
        column7.setText("1"); //insert text in the first textview
        column8.setText("0"); //insert text in the first textview
        column9.setText(""); //insert text in the first textview
        row3.addView(column7); //add the first textview to your tablerow (left column)
        row3.addView(column8); //add the second textview to your tablerow (right column)
        row3.addView(column9); //add the second textview to your tablerow (right column)
        table.addView(row3);

        return table;
    }

    private static TableLayout getTable3Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);

        TableRow row1 = new TableRow(task); //create a new tablerow
        TextView column1 = new TextView(task); //create a new textview for left column
        TextView column2 = new TextView(task); //create a new textview for right column
        TextView column3 = new TextView(task); //create a new textview for left column
        column1.setText("A"); //insert text in the first textview
        column2.setText("B"); //insert text in the first textview
        column3.setText("Result"); //insert text in the first textview
        row1.addView(column1); //add the first textview to your tablerow (left column)
        row1.addView(column2); //add the second textview to your tablerow (right column)
        row1.addView(column3); //add the second textview to your tablerow (right column)
        table.addView(row1);

        TableRow row2 = new TableRow(task); //create a new tablerow
        TextView column4 = new TextView(task); //create a new textview for left column
        TextView column5 = new TextView(task); //create a new textview for right column
        TextView column6 = new TextView(task); //create a new textview for left column
        column1.setText("1"); //insert text in the first textview
        column2.setText("1"); //insert text in the first textview
        column3.setText(""); //insert text in the first textview
        row2.addView(column4); //add the first textview to your tablerow (left column)
        row2.addView(column5); //add the second textview to your tablerow (right column)
        row2.addView(column6); //add the second textview to your tablerow (right column)
        table.addView(row2);

        TableRow row3 = new TableRow(task); //create a new tablerow
        TextView column7 = new TextView(task); //create a new textview for left column
        TextView column8 = new TextView(task); //create a new textview for right column
        TextView column9 = new TextView(task); //create a new textview for left column
        column7.setText("1"); //insert text in the first textview
        column8.setText("0"); //insert text in the first textview
        column9.setText(""); //insert text in the first textview
        row3.addView(column7); //add the first textview to your tablerow (left column)
        row3.addView(column8); //add the second textview to your tablerow (right column)
        row3.addView(column9); //add the second textview to your tablerow (right column)
        table.addView(row3);

        return table;
    }

    private static TableLayout getTable4Arguments(WahrheitstabellenTask task) {
        TableLayout table = new TableLayout(task);

        TableRow row1 = new TableRow(task); //create a new tablerow
        TextView column1 = new TextView(task); //create a new textview for left column
        TextView column2 = new TextView(task); //create a new textview for right column
        TextView column3 = new TextView(task); //create a new textview for left column
        column1.setText("A"); //insert text in the first textview
        column2.setText("B"); //insert text in the first textview
        column3.setText("Result"); //insert text in the first textview
        row1.addView(column1); //add the first textview to your tablerow (left column)
        row1.addView(column2); //add the second textview to your tablerow (right column)
        row1.addView(column3); //add the second textview to your tablerow (right column)
        table.addView(row1);

        TableRow row2 = new TableRow(task); //create a new tablerow
        TextView column4 = new TextView(task); //create a new textview for left column
        TextView column5 = new TextView(task); //create a new textview for right column
        TextView column6 = new TextView(task); //create a new textview for left column
        column1.setText("1"); //insert text in the first textview
        column2.setText("1"); //insert text in the first textview
        column3.setText(""); //insert text in the first textview
        row2.addView(column4); //add the first textview to your tablerow (left column)
        row2.addView(column5); //add the second textview to your tablerow (right column)
        row2.addView(column6); //add the second textview to your tablerow (right column)
        table.addView(row2);

        TableRow row3 = new TableRow(task); //create a new tablerow
        TextView column7 = new TextView(task); //create a new textview for left column
        TextView column8 = new TextView(task); //create a new textview for right column
        TextView column9 = new TextView(task); //create a new textview for left column
        column7.setText("1"); //insert text in the first textview
        column8.setText("0"); //insert text in the first textview
        column9.setText(""); //insert text in the first textview
        row3.addView(column7); //add the first textview to your tablerow (left column)
        row3.addView(column8); //add the second textview to your tablerow (right column)
        row3.addView(column9); //add the second textview to your tablerow (right column)
        table.addView(row3);

        return table;
    }

}
