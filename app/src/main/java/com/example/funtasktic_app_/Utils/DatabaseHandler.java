package com.example.funtasktic_app_.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.funtasktic_app_.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int VERSION= 1;
    private static final String Name="toDoListDatabase";
    private static final String TODO_TABLE="toDo";
    private static final String ID="id";
    private static final String TASK="task";
    private static final String STATUS="status";
    private static final String CREATE_TO_DO_TABLE="CREATE TABLE " + TODO_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASK + " TEXT, "
            + STATUS + " INTEGER)";

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
        db.insert(TODO_TABLE,null,cv);

    }

    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList= new ArrayList<>();
        Cursor cur=null;
        db.beginTransaction();
        try{
            cur=db.query(TODO_TABLE,null,null,null,null,null,null,null);
            if(cur!=null){
                if(cur.moveToFirst()){
                    do{

                        ToDoModel task =new ToDoModel();

                        int column_ID_Index = cur.getColumnIndex(ID);
                        int column_START_Index = cur.getColumnIndex(TASK);
                        int column_STATUS_Index = cur.getColumnIndex(STATUS);

                        if (column_ID_Index != -1) {
                            task.setId(cur.getInt(column_ID_Index));
                        }

                        if (column_START_Index != -1) {
                            task.setTask(cur.getString(column_START_Index));
                        }

                        if (column_STATUS_Index != -1) {
                            task.setStatus(cur.getInt(column_STATUS_Index));
                        }

                    }while(cur.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
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
