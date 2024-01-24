package com.example.funtasktic_app_.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funtasktic_app_.AddNewTask;
import com.example.funtasktic_app_.MainActivity;
import com.example.funtasktic_app_.Model.ToDoModel;
import com.example.funtasktic_app_.R;
import com.example.funtasktic_app_.Utils.DatabaseHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> toDoList;
    private Activity activity;
    private DatabaseHandler db;

    int touchactivity=0;
    public ToDoAdapter(DatabaseHandler db, Activity activity) {
        this.db = db;
        this.activity = activity;
        //this.toDoList = new ArrayList<>();
    }

    public ToDoAdapter(DatabaseHandler db, Activity activity,int touchactivity) {
        this.db = db;
        this.activity = activity;
        this.touchactivity = touchactivity;
        //this.toDoList = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        db.openDataBase();
        final ToDoModel item = toDoList.get(position);
        holder.task.setText(item.getTask());
        holder.date.setText(item.getDate());
        holder.task.setChecked(toBoolean(item.getStatus()));

        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(touchactivity<=0){
                if (isChecked) {

                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(), 0);
                }
                }else{
                    db.updateStatus(item.getId(), 1);
                    holder.task.setChecked(true);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n!=0;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<ToDoModel>toDoList){
        this.toDoList=toDoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        ToDoModel item = toDoList.get(position);
        db.deleteTask(item.getId());
        toDoList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        ToDoModel item = toDoList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        bundle.putString("PRIORITY",item.getPriority());
        bundle.putString("DATE", item.getDate());
        bundle.putString("USERNAME", item.getUsername());

        FragmentActivity fragmentActivity = (FragmentActivity) activity;
        AddNewTask fragment = new AddNewTask(item.getUsername());
        fragment.setArguments(bundle);
        fragment.show(fragmentActivity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;
        TextView date;
        public ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
            date=view.findViewById(R.id.todoDate);
        }
    }
}
