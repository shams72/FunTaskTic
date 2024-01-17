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
import android.widget.Switch;

import com.example.funtasktic_app_.Adapter.ToDoAdapter;
import com.example.funtasktic_app_.Model.ToDoModel;
import com.example.funtasktic_app_.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import kotlin.collections.ArrayDeque;

public class MainActivity extends AppCompatActivity implements DialogCloseListener{

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel>taskList;
    private DatabaseHandler db;
    private Button buttonToSecond;


    String username;

    String Shams;

    private  Button  buttonHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DatabaseHandler(this);
        db.openDataBase();

        taskList=new ArrayList<>();

        taskRecyclerView=findViewById(R.id.tasksRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter =new ToDoAdapter(db,this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");
        taskList = db.getTasksByPriority("High",username);

        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);

        Intent user = getIntent();
        Shams = user.getStringExtra("USERNAME");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance(username).show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });


        buttonToSecond = findViewById(R.id.buttonToSecond);
        buttonHelp = findViewById(R.id.help_b);
        buttonToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
            }
        });


        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, helppage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getTasksByPriority("High",username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();

    }
}