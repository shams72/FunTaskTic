package com.example.funtasktic_app_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
public class ThirdActivity extends AppCompatActivity implements DialogCloseListener, PopupMenu.OnMenuItemClickListener {

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel>taskList;
    private DatabaseHandler db;
    private Button buttonToSecond;
    private String username;

    private Button abmelden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DatabaseHandler(this);
        db.openDataBase();

        taskList=new ArrayList<>();

        taskRecyclerView=findViewById(R.id.tasksRecyclerViewThird);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter =new ToDoAdapter(db,this);
        taskRecyclerView.setAdapter(taskAdapter);

        fab = findViewById(R.id.fabThird);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        taskList = db.getTasksByPriority("Low",username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);




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
                Intent intent = new Intent(ThirdActivity.this ,SecondActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getTasksByPriority("Low",username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();

    }

    public void showPopupThird(View v) {
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
        }
        else if (itemId == R.id.abmelden) {

            Toast.makeText(ThirdActivity.this, "Abmeldung erfolgreich! ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ThirdActivity.this, LoginPage.class);
            startActivity(intent);

            return true;

        }  else if (itemId == R.id.TaskErledigte) {
            Intent intent = new Intent(ThirdActivity.this, DoneActivity.class);
            intent.putExtra("USERNAME_EXTRA", username);
            startActivity(intent);
            return true;
        }

        else {
            return false;
        }
    }
}