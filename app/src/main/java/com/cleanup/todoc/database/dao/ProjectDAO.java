package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Query("SELECT * FROM project_table")
    LiveData<List<Project>> getAllProject();

    @Query("SELECT * FROM project_table WHERE project_id= :id;")
    LiveData<Project> getProject(long id);
}
