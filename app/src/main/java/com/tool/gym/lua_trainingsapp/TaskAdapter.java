package com.tool.gym.lua_trainingsapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mabr on 25.01.2017.
 */

public class TaskAdapter extends ArrayAdapter<Task> {

    Context context;
    int layoutResourceId;
    Task data[] = null;

    public TaskAdapter(Context contextNew, int layoutResourceIdNew, Task[] dataNew) {
        super(contextNew, layoutResourceIdNew, dataNew);
        this.layoutResourceId = layoutResourceIdNew;
        this.context = contextNew;
        this.data = dataNew;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TaskHolder();
            holder.difficulty = (TextView)row.findViewById(R.id.difficulty);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.status = (TextView)row.findViewById(R.id.status);

            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }

        Task task = data[position];
        holder.txtTitle.setText(task.title);
        holder.difficulty.setText(task.difficulty);
        holder.status.setText(task.status);


        return row;
    }

    static class TaskHolder
    {
        TextView difficulty;
        TextView txtTitle;
        TextView status;
    }
}
