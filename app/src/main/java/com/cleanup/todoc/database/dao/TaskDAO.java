package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM task Where projectId = :projectId")
    LiveData<List<Task>> getTask(final long projectId);

    @Insert
    long insertTask(Task task);

    @Delete
    int deleteItem(Task task);
}
