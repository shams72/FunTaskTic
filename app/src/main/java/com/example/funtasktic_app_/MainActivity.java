package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.funtasktic_app_.Adapter.ToDoAdapter;
import com.example.funtasktic_app_.Model.ToDoModel;
import com.example.funtasktic_app_.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener, PopupMenu.OnMenuItemClickListener {

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel>taskList;
    private DatabaseHandler db;
    private Button buttonToSecond;
    private Button buttonToDone;

    String username;

    String Screen="Home";
    String Username2;

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
        Username2 = user.getStringExtra("USERNAME");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance(username).show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });


        buttonToSecond = findViewById(R.id.buttonToSecond);
        buttonToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });

        buttonToDone = findViewById(R.id.buttonToDone);
        buttonToDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoneActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                intent.putExtra("Screen",Screen);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

    public void showPopupMain(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }





    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.subitem1) {

            //Code fur Tasks Einfugen Video

            return true;
        }
        if (itemId == R.id.subitem2) {

            //Code fur Tasks Loeschen Video

            return true;
        }
        if (itemId == R.id.subitem3) {

            //Code fur Tasks Editieren Video

            return true;
        }

        if (itemId == R.id.subitem4) {

            //Code fur Tasks Alle tasks Loeschen Video

            return true;
        } else if (itemId == R.id.abmelden) {

            Toast.makeText(MainActivity.this, "Abmeldung erfolgreich! ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);

            return true;
        } else if (itemId == R.id.TaskErledigte) {
            Intent intent = new Intent(MainActivity.this, DoneActivity.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("Screen",Screen);
            startActivity(intent);
            return true;

        } else {
            return false;
        }
    }
}