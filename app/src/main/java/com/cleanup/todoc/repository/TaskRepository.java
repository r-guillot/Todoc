package com.cleanup.todoc.repository;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.ClipData;

import com.cleanup.todoc.database.TaskDataBase;
import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDAO mTaskDAO;
    private LiveData<List<Task>> mAllTask;

    public TaskRepository(Application application) {
        TaskDataBase db = TaskDataBase.getDatabase(application);
        mTaskDAO = db.taskDao();
        mAllTask= mTaskDAO.getAllTask();
    }

    //Get Tasks
    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

   public LiveData<List<Task>> getTask(long projectId) {
        return mTaskDAO.getTask(projectId);
    }

    //Create
    public void createTask(Task task){
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mTaskDAO.insertTask(task);
        });}

    //Delete
    public void deleteTask(Task task){
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mTaskDAO.deleteItem(task);
        });}
}
