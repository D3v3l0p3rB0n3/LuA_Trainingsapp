package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class BoolscheAlgebraTasks extends Activity {

    private Context context;

    public BoolscheAlgebraTasks(Context applicationContext) {
        this.context = applicationContext;
    }

    public void nextTask() {
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
        context.startActivity(i);
    }
}