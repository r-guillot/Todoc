package com.cleanup.todoc.repository;


import android.arch.lifecycle.LiveData;
import android.content.ClipData;

import com.cleanup.todoc.database.dao.TaskDAO;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDAO mTaskDAO;

    public TaskDataRepository(TaskDAO mTaskDAO) {
        this.mTaskDAO = mTaskDAO;
    }

    //Get Tasks
    public LiveData<List<Task>> getTask(long projectId) {
        return this.mTaskDAO.getItems(projectId);
    }

    //Create
    public void createTask(Task task){ mTaskDAO.insertTask(task); }

    //Delete
    public void deleteTask(long taskId){ mTaskDAO.deleteItem(taskId); }

    //Update
    public void updateTask(Task task){ mTaskDAO.updateItem(task); }
}
