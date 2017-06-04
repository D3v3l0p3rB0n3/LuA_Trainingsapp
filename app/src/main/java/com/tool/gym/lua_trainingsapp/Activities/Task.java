package com.tool.gym.lua_trainingsapp.Activities;

/**
 * Created by mabr on 25.01.2017.
 */

public class Task {
    public String difficulty;
    public String title;
    public String status;
    public String number;
    public Task(){
        super();
    }

    public Task(String number, String difficultyNew, String titleNew, String statusNew){
        super();
        this.difficulty = difficultyNew;
        this.title = titleNew;
        this.status = statusNew;
        this.number = number;
    }
}
