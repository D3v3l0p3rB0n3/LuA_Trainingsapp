package com.tool.gym.lua_trainingsapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tool.gym.lua_trainingsapp.R;

import org.w3c.dom.Text;

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
            holder.difficulty = (TextView) row.findViewById(R.id.difficulty);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.status = (TextView)row.findViewById(R.id.status);
            holder.number = (TextView)row.findViewById(R.id.idaufgabe);

            row.setTag(holder);
        }
        else
        {
            holder = (TaskHolder)row.getTag();
        }

        Task task = data[position];
        holder.txtTitle.setText(task.title);
        //holder.difficulty.setText(task.difficulty);
        holder.status.setText(task.status);
        holder.number.setText(task.number);
        if (task.number.equals("1"))
        {
            //holder.difficulty.setBackgroundResource(R.drawable.schwierigkeit1);
        }
        else if (task.number.equals("2"))
        {
            //holder.difficulty.setBackgroundResource(R.drawable.schwierigkeit2);
        }
        else
        {
            //holder.difficulty.setBackgroundResource(R.drawable.schwierigkeit3);
        }


        return row;
    }



    static class TaskHolder
    {
        TextView difficulty;
        TextView txtTitle;
        TextView status;
        TextView number;
    }
}

