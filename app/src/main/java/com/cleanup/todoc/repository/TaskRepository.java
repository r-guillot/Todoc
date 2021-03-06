package com.cleanup.todoc.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;

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
        mAllTask = mTaskDAO.getAllTask();
    }

    //Get Tasks
    public LiveData<List<Task>> getAllTask() {
        return mAllTask;
    }

    //Create
    public void createTask(Task task) {
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mTaskDAO.insertTask(task);
        });
    }

    //Delete
    public void deleteTask(Task task) {
        TaskDataBase.databaseWriteExecutor.execute(() -> {
            mTaskDAO.deleteItem(task);
        });
    }
}
