package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class HelpPopUp extends Activity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helppopup);
        tv = (TextView) findViewById(R.id.helptext);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));
        Bundle extras = getIntent().getExtras();
        String help;
        if(extras.getString("loesung") != null){
             help = extras.getString("text") + "\n \n" + "Lösung: " + extras.getString("loesung") + "\n";
        }
        else
        {
             help = extras.getString("text");
        }
        tv.setText(help);
    }
}
