package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM task Where projectId = :project_id")
    LiveData<List<Task>> getTask(final long project_id);

    @Insert
    long insertTask(Task task);

    @Delete
    int deleteItem(Task task);
}
