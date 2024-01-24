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

public class DoneActivity extends AppCompatActivity implements DialogCloseListener, PopupMenu.OnMenuItemClickListener {

    private List<ToDoModel> taskList;
    private ToDoAdapter taskAdapter;
    private DatabaseHandler db;
    private RecyclerView taskRecyclerView;
    private Button buttonToMain;
    private FloatingActionButton deleteChecked;
    String username;
    String Username2;

    int attempts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        db = new DatabaseHandler(this);
        db.openDataBase();

        taskList=new ArrayList<>();

        taskRecyclerView=findViewById(R.id.tasksRecyclerViewDone);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter =new ToDoAdapter(db,this,1);
        taskRecyclerView.setAdapter(taskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter,1));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");

        taskList = db.getAllDoneTasks(username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);

        Intent user = getIntent();
        Username2 = user.getStringExtra("USERNAME");



        deleteChecked = findViewById(R.id.deleteChecked);
        deleteChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* db.deleteDoneTask(username);
                taskAdapter.notifyDataSetChanged();*/

                if(db.deleteDoneTask(username)>0){
                taskAdapter.notifyDataSetChanged();
                Intent intent = new Intent(DoneActivity.this, DeleteAnimation.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }else{
                    Toast.makeText(DoneActivity.this, "Es wurden keine Aufgaben gelöscht. ", Toast.LENGTH_SHORT).show();


                }
            }

        });

        buttonToMain = findViewById(R.id.buttonToMainFromDone);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoneActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }

        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllDoneTasks(username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();

    }

    public void showPopupDone(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu2);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.hilfe) {

            Intent intent = new Intent(DoneActivity.this, helppage.class);
            startActivity(intent);

            return true;
        } else if (itemId == R.id.abmelden) {

            Toast.makeText(DoneActivity.this, "Abmeldung erfolgreich! ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DoneActivity.this, LoginPage.class);
            startActivity(intent);

            return true;
        }
        else if (itemId == R.id.Home) {

            Intent intent = new Intent(DoneActivity.this, MainActivity.class);
            intent.putExtra("USERNAME_EXTRA", username);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            return true;
        }

        else {
            return false;
        }
    }
}