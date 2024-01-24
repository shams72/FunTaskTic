package com.example.funtasktic_app_.Model;

public class ToDoModel {
    private int id,status;
    private String task;
    private String Username;
    private String Priority;
    private String Date;
    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public int getId() {
        return id;
    }

    public void setPriority(String Priority) {
        this.Priority = Priority;
    }
    public String getPriority() {
        return Priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUserName(String username) {
        this.Username = username;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
