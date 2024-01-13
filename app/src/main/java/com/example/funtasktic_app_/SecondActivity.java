package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.funtasktic_app_.Adapter.ToDoAdapter;
import com.example.funtasktic_app_.Model.ToDoModel;
import com.example.funtasktic_app_.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.collections.ArrayDeque;
public class SecondActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel>taskList;
    private DatabaseHandler db;
    private Button buttonToMain;
    private Button buttonToThird;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DatabaseHandler(this);
        db.openDataBase();

        taskList=new ArrayList<>();

        taskRecyclerView=findViewById(R.id.tasksRecyclerViewSecond);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter =new ToDoAdapter(db,this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fabSecond);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        taskList = db.getTasksByPriority("Medium");
        Collections.reverse(taskList);

        taskAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        buttonToMain = findViewById(R.id.buttonToMain);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this ,MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        buttonToThird = findViewById(R.id.buttonToThird);
        buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this ,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getTasksByPriority("Medium");
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();

    }
}