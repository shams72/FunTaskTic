package com.example.funtasktic_app_.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.funtasktic_app_.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int VERSION= 2;
    private static final String Name="toDoListDatabase";
    private static final String TODO_TABLE="toDo";
    private static final String ID="id";
    private static final String TASK="task";
    private static final String STATUS="status";
    private static final String PRIORITY="PRIORITY";
    private static final String DATE="DATE";
    private static final String CREATE_TO_DO_TABLE = "CREATE TABLE " + TODO_TABLE + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK + " TEXT, " +
            STATUS + " INTEGER, " +
            PRIORITY + " TEXT, " +
            DATE + " TEXT) ";


    private SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context,Name,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TO_DO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE);
        onCreate(db);
    }

    public void openDataBase(){
        db=this.getWritableDatabase();
    }

    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(TASK,task.getTask());
        cv.put(STATUS,0);
        cv.put(PRIORITY,task.getPriority());
        cv.put(DATE,task.getDate());
        db.insert(TODO_TABLE,null,cv);
    }


    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(TODO_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        int column_ID_Index = cur.getColumnIndex(ID);
                        int column_TASK_Index = cur.getColumnIndex(TASK);
                        int column_STATUS_Index = cur.getColumnIndex(STATUS);
                        int coloum_Priority_Index= cur.getColumnIndex(PRIORITY);
                        int coloum_Date_Index= cur.getColumnIndex(DATE);

                        if (column_ID_Index != -1) {
                            task.setId(cur.getInt(column_ID_Index));
                        }

                        if (column_TASK_Index != -1) {
                            task.setTask(cur.getString(column_TASK_Index));
                        }

                        if (column_STATUS_Index != -1) {
                            task.setStatus(cur.getInt(column_STATUS_Index));
                        }

                        if (coloum_Priority_Index != -1) {
                            task.setPriority(cur.getString(coloum_Priority_Index));
                        }

                        if (column_ID_Index != -1) {
                            task.setDate(cur.getString(coloum_Date_Index));
                        }

                        taskList.add(task); // Add the task to the list

                    } while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            if (cur != null) {
                cur.close();
            }
        }
        return taskList;
    }

    public List<ToDoModel> getTasksByPriority(String priority) {
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;

        try {
            Log.d("DatabaseHandler", "Querying tasks for priority: " + priority);

            String selection = PRIORITY + "=?";
            String[] selectionArgs = {priority};

            cur = db.query(TODO_TABLE, null, selection, selectionArgs, null, null, null);

            if(cur != null){

             if (cur.moveToFirst()) {
                do {
                    ToDoModel task = new ToDoModel();
                    int column_ID_Index = cur.getColumnIndex(ID);
                    int column_TASK_Index = cur.getColumnIndex(TASK);
                    int column_STATUS_Index = cur.getColumnIndex(STATUS);
                    int coloum_Priority_Index = cur.getColumnIndex(PRIORITY);
                    int coloum_Date_Index= cur.getColumnIndex(DATE);

                    if (column_ID_Index != -1) {
                        task.setId(cur.getInt(column_ID_Index));
                    }

                    if (column_TASK_Index != -1) {
                        task.setTask(cur.getString(column_TASK_Index));
                    }

                    if (column_STATUS_Index != -1) {
                        task.setStatus(cur.getInt(column_STATUS_Index));
                    }
                    if (coloum_Priority_Index != -1) {
                        task.setPriority(cur.getString(coloum_Priority_Index));
                    } else {
                        task.setPriority("DefaultPriority");
                    }
                    if (column_ID_Index != -1) {
                        task.setDate(cur.getString(coloum_Date_Index));
                    }
                    taskList.add(task);

                } while (cur.moveToNext());
            }}
        } finally {
            if (cur != null) {
                cur.close();
            }
        }
        return taskList;
    }


    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateDate(int id, String Date){
        ContentValues cv = new ContentValues();
        cv.put(DATE, Date);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updatePriority(int id, String Priority){
        ContentValues cv = new ContentValues();
        cv.put(PRIORITY, Priority);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(TASK, task);
        db.update(TODO_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(TODO_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }



}
