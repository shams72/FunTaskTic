package com.example.funtasktic_app_;

import androidx.activity.OnBackPressedCallback;
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

public class SecondActivity extends AppCompatActivity implements DialogCloseListener, PopupMenu.OnMenuItemClickListener {

    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private FloatingActionButton fab;
    private List<ToDoModel>taskList;
    private DatabaseHandler db;
    private Button buttonToMain;
    private Button buttonToThird;

    private String Screen="Middle";
    private String username;
    private String videotype;

    String current_screen="Second";

    String Help="Help";

    String Screen_Type="Second";


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

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME_EXTRA");

        fab = findViewById(R.id.fabSecond);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(taskAdapter));
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);

        taskList = db.getTasksByPriority("Medium",username);
        Collections.reverse(taskList);

        taskAdapter.setTasks(taskList);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance(username).show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        buttonToMain = findViewById(R.id.buttonToMain);
        buttonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                intent.putExtra("current_screen",current_screen);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }

        });

        buttonToThird = findViewById(R.id.buttonToThird);
        buttonToThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this ,ThirdActivity.class);
                intent.putExtra("USERNAME_EXTRA", username);
                intent.putExtra("current_screen",current_screen);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if("Help".equals(Help)){

                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getTasksByPriority("Medium",username);
        Collections.reverse(taskList);
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();

    }

    public void showPopupSecond(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.subitem1) {
            videotype ="Insert_Edit";

            Intent intent = new Intent(SecondActivity.this, helppage.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("VideoTask",videotype);
            intent.putExtra("Screen",Screen_Type);
            intent.putExtra("current_screen",current_screen);
            startActivity(intent);

            return true;
        }
        if (itemId == R.id.subitem2) {

            videotype ="Delete";
            Intent intent = new Intent(SecondActivity.this, helppage.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("VideoTask",videotype);
            intent.putExtra("Screen",Screen_Type);
            intent.putExtra("current_screen",current_screen);
            startActivity(intent);


            return true;
        }
        if (itemId == R.id.subitem3) {

            videotype ="Edit";
            Intent intent = new Intent(SecondActivity.this, helppage.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("VideoTask",videotype);
            intent.putExtra("Screen",Screen_Type);
            intent.putExtra("current_screen",current_screen);
            startActivity(intent);

            return true;
        }

        if (itemId == R.id.subitem4) {

            videotype ="Alldelete";
            Intent intent = new Intent(SecondActivity.this, helppage.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("VideoTask",videotype);
            intent.putExtra("Screen",Screen_Type);
            intent.putExtra("current_screen",current_screen);
            startActivity(intent);
            return true;
        }  else if (itemId == R.id.abmelden) {

            Toast.makeText(SecondActivity.this, "Abmeldung erfolgreich! ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SecondActivity.this, LoginPage.class);
            startActivity(intent);

            return true;
        }

        else if (itemId == R.id.TaskErledigte) {
            Intent intent = new Intent(SecondActivity.this, DoneActivity.class);
            intent.putExtra("USERNAME_EXTRA", username);
            intent.putExtra("Screen",Screen);
            startActivity(intent);
            return true;
        }
        else {
            return false;
        }
    }
}