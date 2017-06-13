package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tool.gym.lua_trainingsapp.Activities.RandomTasks;

import Database.SQLiteDatabase;
import android.database.Cursor;


public class ChooseTask extends Activity {

    private Context context;
    Intent i;
    SQLiteDatabase db;
    String sql;
    Cursor c;

    public ChooseTask(Context applicationContext) {
        this.context = applicationContext;

    }

    public void nextBoolTask(Activity activity) {
        activity.finish();

        //gannzahlige Zufallszahl zwischen 1-3
        Double x1 = Math.random();
        Integer zufall = (int)(x1 * 3 + 1);

        i = null;
        switch (zufall) {
            case 1:
                i = new Intent(context, WahrheitstabellenTask.class);
                break;
            case 2:
                i = new Intent(context, TermVereinfachenTask.class);
                break;
            case 3:
                i = new Intent(context, NormalformenTask.class);
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("startactivity", ChooseTask.class.getSimpleName());
        context.startActivity(i);
    }

    public void nextRelationalTask(Activity activity){
        activity.finish();
        i = new Intent(context, RelationalTask.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("startactivity", ChooseTask.class.getSimpleName());
        context.startActivity(i);
    }

    public void nextRandomTask(Activity activity){
        activity.finish();

        //gannzahlige Zufallszahl zwischen 1-4
        Double x1 = Math.random();
        Integer zufall = (int)(x1 * 4 + 1);

        Log.d(ChooseTask.class.getSimpleName(), "Zufallszahl: " +x1.toString());
        Log.d(ChooseTask.class.getSimpleName(), "multiplizierte Zufallszahl: " + zufall.toString());


        i = null;

        switch (zufall) {
            case 1:
                i = new Intent(context, WahrheitstabellenTask.class);
                Log.d(ChooseTask.class.getSimpleName(), "Wahrheitstabelle gewählt");
                break;
            case 2:
                i = new Intent(context, TermVereinfachenTask.class);
                Log.d(ChooseTask.class.getSimpleName(), "Term gewählt");
                break;
            case 3:
                i = new Intent(context, NormalformenTask.class);
                Log.d(ChooseTask.class.getSimpleName(), "Normalformen gewählt");
                break;
            case 4:
                i = new Intent(context, RelationalTask.class);
                Log.d(ChooseTask.class.getSimpleName(), "Relationenalgebra gewählt");
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("startactivity", ChooseTask.class.getSimpleName());
        context.startActivity(i);
    }



}