package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tool.gym.lua_trainingsapp.Activities.RandomTasks;


public class ChooseTask extends Activity {

    private Context context;

    public ChooseTask(Context applicationContext) {
        this.context = applicationContext;
    }

    public void nextBoolTask(Activity activity) {
        activity.finish();
        Double x = Math.random() * 2;
        Integer zufall = (int) Math.round(x);
        Intent i = null;
        switch (zufall) {
            case 0:
                i = new Intent(context, WahrheitstabellenTask.class);
                break;
            case 1:
                i = new Intent(context, TermVereinfachenTask.class);
                break;
            case 2:
                i = new Intent(context, NormalformenTask.class);
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("startactivity", ChooseTask.class.getSimpleName());
        context.startActivity(i);
    }

    public void nextRelationalTask(Activity activity){
        activity.finish();
        Intent i = new Intent(context, RelationalTask.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("startactivity", RandomTasks.class.getSimpleName());
        context.startActivity(i);
    }
}